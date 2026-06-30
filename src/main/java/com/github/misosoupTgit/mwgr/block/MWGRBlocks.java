package com.github.misosoupTgit.mwgr.block;

import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.MWGRLang;
import com.github.misosoupTgit.mwgr.compat.InfiniteFluidHandlerItem; // 新設クラス
import com.github.misosoupTgit.mwgr.item.MWGRItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final RegistryObject<Block> WATER_GENERATOR = registryGenerator(
            "water_generator",
            () -> new FluidGeneratorBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.IRON_BLOCK.defaultMapColor())
                    .strength(0.8f, 128.0f)
                    .sound(SoundType.STONE),
                    MWGRBlockEntities.WATER_GENERATOR_BE, Fluids.WATER, MWGRLang.DESCRIPTION_WATER_GENERATOR),
            "#00AAFF", true, Fluids.WATER
    );

    public static final RegistryObject<Block> LAVA_GENERATOR = registryGenerator(
            "lava_generator",
            () -> new FluidGeneratorBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.STONE.defaultMapColor())
                    .strength(0.8f, 128.0f)
                    .sound(SoundType.STONE),
                    MWGRBlockEntities.LAVA_GENERATOR_BE, Fluids.LAVA, MWGRLang.DESCRIPTION_LAVA_GENERATOR),
            "#FF4400", true, Fluids.LAVA
    );

    // Generator専用の登録メソッド
    private static <T extends Block> RegistryObject<T> registryGenerator(String name, Supplier<T> supplier, String hexColor, boolean isBold, Fluid fluid) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        MWGRItems.ITEMS.register(name, () -> new InfiniteFluidHandlerItem(block.get(), new Item.Properties(), hexColor, isBold, fluid));
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}