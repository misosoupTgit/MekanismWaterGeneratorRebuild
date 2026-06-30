package com.github.misosoupTgit.mwgr.block;

import com.github.misosoupTgit.mwgr.MWGRMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWGRBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MWGRMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>> WATER_GENERATOR_BE =
            BLOCK_ENTITIES.register("water_generator",
                    () -> BlockEntityType.Builder.of(
                            (pos, state) -> {
                                BlockEntityType<?> type = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(
                                        ResourceLocation.fromNamespaceAndPath(MWGRMod.MOD_ID, "water_generator"));
                                return new FluidGeneratorBlockEntity(type, pos, state, Fluids.WATER);
                            },
                            MWGRBlocks.WATER_GENERATOR.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>> LAVA_GENERATOR_BE =
            BLOCK_ENTITIES.register("lava_generator",
                    () -> BlockEntityType.Builder.of(
                            (pos, state) -> {
                                BlockEntityType<?> type = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(
                                        ResourceLocation.fromNamespaceAndPath(MWGRMod.MOD_ID, "lava_generator"));
                                return new FluidGeneratorBlockEntity(type, pos, state, Fluids.LAVA);
                            },
                            MWGRBlocks.LAVA_GENERATOR.get()
                    ).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}