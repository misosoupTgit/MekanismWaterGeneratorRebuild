package com.github.misosoupTgit.mwgr.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class MekanismCompat {
    public static final String MEKANISM_MODID = "mekanism";
    public static final ResourceLocation HEAVY_WATER_RL = ResourceLocation.fromNamespaceAndPath(MEKANISM_MODID,
            "heavy_water");

    public static boolean isMekanismLoaded() {
        return ModList.get().isLoaded(MEKANISM_MODID);
    }

    public static Supplier<Fluid> getHeavyWaterSupplier() {
        return () -> {
            if (isMekanismLoaded()) {
                return MekanismRealImpl.getHeavyWater();
            }
            return Fluids.EMPTY;
        };
    }

    public static Block createMekanismBlock(
            BlockBehaviour.Properties props,
            Supplier<BlockEntityType<com.github.misosoupTgit.mwgr.block.FluidGeneratorBlockEntity>> typeSupplier,
            Supplier<Fluid> fluidSupplier,
            String descriptionKey) {
        if (isMekanismLoaded()) {
            return MekanismRealImpl.createBlock(props, typeSupplier, fluidSupplier, descriptionKey);
        }
        return new com.github.misosoupTgit.mwgr.block.FluidGeneratorBlock(props, typeSupplier, fluidSupplier,
                descriptionKey);
    }

    public static Item createInfiniteFluidHandlerItem(
            Block block,
            Item.Properties properties,
            String hexColorString,
            boolean isBold,
            Supplier<Fluid> fluidSupplier) {
        if (isMekanismLoaded()) {
            return MekanismRealImpl.createItem(block, properties, hexColorString, isBold, fluidSupplier);
        }
        return new net.minecraft.world.item.BlockItem(block, properties);
    }

    public static net.minecraft.network.chat.Component getAutoEjectTooltip(boolean autoEject) {
        if (isMekanismLoaded()) {
            return MekanismRealImpl.getAutoEjectTooltip(autoEject);
        }
        return net.minecraft.network.chat.Component.empty();
    }

    private static class MekanismRealImpl {
        static Fluid getHeavyWater() {
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(HEAVY_WATER_RL);
            return fluid != null ? fluid : Fluids.EMPTY;
        }

        static Block createBlock(
                BlockBehaviour.Properties props,
                Supplier<BlockEntityType<com.github.misosoupTgit.mwgr.block.FluidGeneratorBlockEntity>> typeSupplier,
                Supplier<Fluid> fluidSupplier,
                String descriptionKey) {
            return new MekanismFluidGeneratorBlock(props, typeSupplier, fluidSupplier, descriptionKey);
        }

        static Item createItem(
                Block block,
                Item.Properties properties,
                String hexColorString,
                boolean isBold,
                Supplier<Fluid> fluidSupplier) {
            return new InfiniteFluidHandlerItem(block, properties, hexColorString, isBold, fluidSupplier);
        }

        static net.minecraft.network.chat.Component getAutoEjectTooltip(boolean autoEject) {
            return com.github.misosoupTgit.mwgr.MWGRLang.STATS_AUTO_EJECT.translate(
                    autoEject
                            ? mekanism.common.MekanismLang.ON.translateColored(mekanism.api.text.EnumColor.BRIGHT_GREEN)
                            : mekanism.common.MekanismLang.OFF.translateColored(mekanism.api.text.EnumColor.RED));
        }
    }
}