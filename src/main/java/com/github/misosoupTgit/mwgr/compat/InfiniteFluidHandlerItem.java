package com.github.misosoupTgit.mwgr.compat;

import com.github.misosoupTgit.mwgr.item.MWGRColorBlockItems;
import mekanism.api.text.EnumColor;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.MekanismLang;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.util.text.TextUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfiniteFluidHandlerItem extends MWGRColorBlockItems {
    private final java.util.function.Supplier<Fluid> fluidSupplier;

    public InfiniteFluidHandlerItem(Block block, Properties properties, String hexColorString, boolean isBold,
            java.util.function.Supplier<Fluid> fluidSupplier) {
        super(block, properties, hexColorString, isBold);
        this.fluidSupplier = fluidSupplier;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new InfiniteFluidCapability(stack, fluidSupplier);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag) {
        if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.descriptionKey)) {
            if (getBlock() instanceof IHasDescription hasDescription) {
                tooltip.add(hasDescription.getDescription().translate());
            }
        } else if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.detailsKey)) {
            addDetails(stack, world, tooltip, flag);
        } else {
            tooltip.add(MekanismLang.HOLD_FOR_DETAILS.translateColored(EnumColor.GRAY, EnumColor.INDIGO,
                    MekanismKeyHandler.detailsKey.getTranslatedKeyMessage()));
            tooltip.add(MekanismLang.HOLD_FOR_DESCRIPTION.translateColored(EnumColor.GRAY, EnumColor.AQUA,
                    MekanismKeyHandler.descriptionKey.getTranslatedKeyMessage()));
        }
    }

    protected void addDetails(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag) {
        Fluid fluid = this.fluidSupplier.get();
        FluidStack fluidStack = new FluidStack(fluid == null ? Fluids.EMPTY : fluid, Integer.MAX_VALUE);
        tooltip.add(MekanismLang.GENERIC_STORED_MB.translateColored(EnumColor.PINK, fluidStack, EnumColor.GRAY,
                TextUtils.format(fluidStack.getAmount())));
    }

    private static class InfiniteFluidCapability implements IFluidHandlerItem, ICapabilityProvider {
        private final ItemStack container;
        private final java.util.function.Supplier<Fluid> fluidSupplier;
        private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);

        public InfiniteFluidCapability(ItemStack container, java.util.function.Supplier<Fluid> fluidSupplier) {
            this.container = container;
            this.fluidSupplier = fluidSupplier;
        }

        @Override
        public @NotNull ItemStack getContainer() {
            return container;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @NotNull
        @Override
        public FluidStack getFluidInTank(int tank) {
            Fluid fluid = fluidSupplier.get();
            return new FluidStack(fluid == null ? Fluids.EMPTY : fluid, Integer.MAX_VALUE);
        }

        @Override
        public int getTankCapacity(int tank) {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            Fluid fluid = fluidSupplier.get();
            return fluid != null && stack.getFluid().isSame(fluid);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @NotNull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            Fluid fluid = fluidSupplier.get();
            return new FluidStack(fluid == null ? Fluids.EMPTY : fluid, maxDrain);
        }

        @NotNull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            Fluid fluid = fluidSupplier.get();
            if (fluid == null || resource.isEmpty() || !resource.getFluid().isSame(fluid))
                return FluidStack.EMPTY;
            return drain(resource.getAmount(), action);
        }

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap,
                @Nullable net.minecraft.core.Direction side) {
            return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, holder);
        }
    }
}