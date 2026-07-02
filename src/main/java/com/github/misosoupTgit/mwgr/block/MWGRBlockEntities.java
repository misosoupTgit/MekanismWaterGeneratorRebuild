package com.github.misosoupTgit.mwgr.block;

import com.github.misosoupTgit.mwgr.MWGRMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MWGRBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MWGRMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>> WATER_GENERATOR_BE =
            registerGeneratorBE("water_generator", () -> MWGRBlocks.WATER_GENERATOR, () -> Fluids.WATER);

    public static final RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>> LAVA_GENERATOR_BE =
            registerGeneratorBE("lava_generator", () -> MWGRBlocks.LAVA_GENERATOR, () -> Fluids.LAVA);

    public static final RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>> HEAVY_WATER_GENERATOR_BE =
            registerGeneratorBE("heavywater_generator", () -> MWGRBlocks.HEAVY_WATER_GENERATOR, com.github.misosoupTgit.mwgr.compat.MekanismCompat.getHeavyWaterSupplier());

    private static RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>> registerGeneratorBE(
            String name, Supplier<RegistryObject<Block>> blockReg, Supplier<Fluid> fluidSupplier) {
        return BLOCK_ENTITIES.register(name,
                () -> BlockEntityType.Builder.of(
                        (pos, state) -> {
                            BlockEntityType<?> type = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(
                                    ResourceLocation.fromNamespaceAndPath(MWGRMod.MOD_ID, name));
                            return new FluidGeneratorBlockEntity(type, pos, state, fluidSupplier);
                        },
                        blockReg.get().get()
                ).build(null));
    }

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}