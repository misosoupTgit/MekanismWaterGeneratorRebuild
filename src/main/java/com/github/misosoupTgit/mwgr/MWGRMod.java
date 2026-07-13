package com.github.misosoupTgit.mwgr;

import com.github.misosoupTgit.mwgr.block.MWGRBlockEntities;
import com.github.misosoupTgit.mwgr.block.MWGRBlocks;
import com.github.misosoupTgit.mwgr.config.MWGRClientConfig;
import com.github.misosoupTgit.mwgr.config.MWGRConfig;
import com.github.misosoupTgit.mwgr.item.MWGRItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MWGRMod.MOD_ID)
public class MWGRMod {
    public static final String MOD_ID = "mwgr";

    public MWGRMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MWGRConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MWGRClientConfig.SPEC);

        MWGRBlocks.register(bus);
        MWGRItems.register(bus);
        MWGRBlockEntities.register(bus);

        bus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(MWGRBlocks.WATER_GENERATOR.get());
            event.accept(MWGRBlocks.HEAVY_WATER_GENERATOR.get());
            event.accept(MWGRBlocks.LAVA_GENERATOR.get());
        }
    }

    public static net.minecraft.resources.ResourceLocation rl(String path) {
        return new net.minecraft.resources.ResourceLocation(MOD_ID, path);
    }
}