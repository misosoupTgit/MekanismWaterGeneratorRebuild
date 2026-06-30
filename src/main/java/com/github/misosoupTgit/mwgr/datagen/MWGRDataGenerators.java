package com.github.misosoupTgit.mwgr.datagen;

import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.datagen.client.ENUSLanguageProvider;
import com.github.misosoupTgit.mwgr.datagen.client.MWGRItemModelProvider;
import com.github.misosoupTgit.mwgr.datagen.client.JAJPLanguageProvider;
import com.github.misosoupTgit.mwgr.datagen.server.MWGRAdvancementProvider;
import com.github.misosoupTgit.mwgr.datagen.server.MWGRBlockTagsProvider;
import com.github.misosoupTgit.mwgr.datagen.server.loot.MWGRLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MWGRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MWGRDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookUpProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new MWGRItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ENUSLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new JAJPLanguageProvider(packOutput));

        generator.addProvider(event.includeServer(), MWGRLootTables.create(packOutput));
        generator.addProvider(event.includeServer(), new MWGRBlockTagsProvider(packOutput, lookUpProvider, existingFileHelper));


        generator.addProvider(event.includeServer(), new ForgeAdvancementProvider(
                packOutput,
                lookUpProvider,
                existingFileHelper,
                List.of(new MWGRAdvancementProvider())
        ));
    }
}