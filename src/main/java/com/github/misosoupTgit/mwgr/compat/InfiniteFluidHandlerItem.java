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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Mekanism等のGUIスロットで無限に液体を提供するアイテムクラス
 */
public class InfiniteFluidHandlerItem extends MWGRColorBlockItems {
    private final Fluid fluid;

    public InfiniteFluidHandlerItem(Block block, Properties properties, String hexColorString, boolean isBold,
            Fluid fluid) {
        super(block, properties, hexColorString, isBold);
        this.fluid = fluid;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new InfiniteFluidCapability(stack, fluid);
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
        // 詳細表示
        FluidStack fluidStack = new FluidStack(this.fluid, Integer.MAX_VALUE);
        tooltip.add(MekanismLang.GENERIC_STORED_MB.translateColored(EnumColor.PINK, fluidStack, EnumColor.GRAY, TextUtils.format(fluidStack.getAmount())));
    }

    private static class InfiniteFluidCapability implements IFluidHandlerItem, ICapabilityProvider {
        private final ItemStack container;
        private final Fluid fluid;
        private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);

        public InfiniteFluidCapability(ItemStack container, Fluid fluid) {
            this.container = container;
            this.fluid = fluid;
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
            return new FluidStack(fluid, Integer.MAX_VALUE);
        }

        @Override
        public int getTankCapacity(int tank) {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return stack.getFluid().isSame(fluid);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @NotNull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return new FluidStack(fluid, maxDrain);
        }

        @NotNull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.isEmpty() || !resource.getFluid().isSame(fluid))
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