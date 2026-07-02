package com.github.misosoupTgit.mwgr.block;

import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.compat.MekanismCompat;
import com.github.misosoupTgit.mwgr.item.MWGRItems;
import com.github.misosoupTgit.mwgr.item.MWGRColorBlockItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MWGRBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MWGRMod.MOD_ID);

    public static final RegistryObject<Block> WATER_GENERATOR = registerGenerator(
            "water_generator",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(Blocks.IRON_BLOCK.defaultMapColor())
                    .strength(0.8f, 128.0f)
                    .sound(SoundType.STONE),
            () -> MWGRBlockEntities.WATER_GENERATOR_BE,
            () -> Fluids.WATER,
            "description.mwgr.water_generator",
            "#00AAFF",
            true
    );

    public static final RegistryObject<Block> LAVA_GENERATOR = registerGenerator(
            "lava_generator",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(Blocks.STONE.defaultMapColor())
                    .strength(0.8f, 128.0f)
                    .sound(SoundType.STONE),
            () -> MWGRBlockEntities.LAVA_GENERATOR_BE,
            () -> Fluids.LAVA,
            "description.mwgr.lava_generator",
            "#FF4400",
            true
    );

    public static final RegistryObject<Block> HEAVY_WATER_GENERATOR = registerGenerator(
            "heavywater_generator",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(Blocks.IRON_BLOCK.defaultMapColor())
                    .strength(0.8f, 128.0f)
                    .sound(SoundType.STONE),
            () -> MWGRBlockEntities.HEAVY_WATER_GENERATOR_BE,
            MekanismCompat.getHeavyWaterSupplier(),
            "description.mwgr.heavywater_generator",
            "#5A8FCE",
            true
    );

    private static RegistryObject<Block> registerGenerator(
            String name,
            Supplier<BlockBehaviour.Properties> propertiesSupplier,
            Supplier<RegistryObject<BlockEntityType<FluidGeneratorBlockEntity>>> typeSupplier,
            Supplier<Fluid> fluidSupplier,
            String descriptionKey,
            String hexColor,
            boolean isBold) {

        RegistryObject<Block> block = BLOCKS.register(name, () -> {
            BlockBehaviour.Properties props = propertiesSupplier.get();
            Supplier<BlockEntityType<FluidGeneratorBlockEntity>> lazyType = () -> typeSupplier.get().get();
            if (MekanismCompat.isMekanismLoaded()) {
                return MekanismCompat.createMekanismBlock(props, lazyType, fluidSupplier, descriptionKey);
            } else {
                return new FluidGeneratorBlock(props, lazyType, fluidSupplier, descriptionKey);
            }
        });

        MWGRItems.ITEMS.register(name, () -> {
            if (MekanismCompat.isMekanismLoaded()) {
                return MekanismCompat.createInfiniteFluidHandlerItem(block.get(), new Item.Properties(), hexColor, isBold, fluidSupplier);
            } else {
                return new MWGRColorBlockItems(block.get(), new Item.Properties(), hexColor, isBold);
            }
        });

        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}