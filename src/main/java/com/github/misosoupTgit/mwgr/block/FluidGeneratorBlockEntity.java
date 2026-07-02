package com.github.misosoupTgit.mwgr.block;

import com.github.misosoupTgit.mwgr.config.MWGRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FluidGeneratorBlockEntity extends BlockEntity {
    private final LazyOptional<IFluidHandler> handler;
    private final Supplier<Fluid> fluidSupplier;
    private boolean autoEject;

    public FluidGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, Supplier<Fluid> fluidSupplier) {
        super(type, pos, state);
        this.fluidSupplier = fluidSupplier;
        this.handler = LazyOptional.of(() -> new StaticFluidHandler(fluidSupplier));
        this.autoEject = MWGRConfig.DEFAULT_AUTO_EJECT.get();
    }
    public boolean isAutoEject() {
        return autoEject;
    }

    public boolean toggleAutoEject() {
        this.autoEject = !this.autoEject;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
        return this.autoEject;
    }

    public void serverTick() {
        if (!autoEject || level == null || level.isClientSide) return;

        Fluid fluid = fluidSupplier.get();
        if (fluid == null || fluid == Fluids.EMPTY) return;

        for (Direction direction : Direction.values()) {
            BlockEntity neighbor = level.getBlockEntity(worldPosition.relative(direction));
            if (neighbor != null) {
                neighbor.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite()).ifPresent(neighborHandler -> {
                    neighborHandler.fill(new FluidStack(fluid, Integer.MAX_VALUE), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        }
    }

    private static class StaticFluidHandler implements IFluidHandler {
        private final Supplier<Fluid> fluidSupplier;
        public StaticFluidHandler(Supplier<Fluid> fluidSupplier) { this.fluidSupplier = fluidSupplier; }
        private FluidStack getStack() { return new FluidStack(fluidSupplier.get(), Integer.MAX_VALUE); }
        @Override public int getTanks() { return 1; }
        @NotNull @Override public FluidStack getFluidInTank(int tank) { return getStack(); }
        @Override public int getTankCapacity(int tank) { return Integer.MAX_VALUE; }
        @Override public boolean isFluidValid(int tank, @NotNull FluidStack stack) { return true; }
        @Override public int fill(FluidStack resource, FluidAction action) { return 0; }
        @NotNull @Override public FluidStack drain(int maxDrain, FluidAction action) { return new FluidStack(fluidSupplier.get(), maxDrain); }
        @NotNull @Override public FluidStack drain(FluidStack resource, FluidAction action) {
            FluidStack stack = getStack();
            if (resource.isEmpty() || !resource.isFluidEqual(stack)) return FluidStack.EMPTY;
            return drain(resource.getAmount(), action);
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("AutoEject")) {
            this.autoEject = tag.getBoolean("AutoEject");
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("AutoEject", autoEject);
    }

    @Override public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override public void invalidateCaps() { super.invalidateCaps(); handler.invalidate(); }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) return handler.cast();
        return super.getCapability(cap, side);
    }
}