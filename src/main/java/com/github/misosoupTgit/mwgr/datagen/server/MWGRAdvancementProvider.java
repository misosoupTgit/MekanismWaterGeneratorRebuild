package com.github.misosoupTgit.mwgr.datagen.server;

import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.block.MWGRBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MWGRAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<Advancement> saver, @NotNull ExistingFileHelper existingFileHelper) {

        Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStack(MWGRBlocks.WATER_GENERATOR.get()),
                        Component.translatable("advancements.mwgr.get_water_gen.title"),
                        Component.translatable("advancements.mwgr.get_water_gen.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false))
                .addCriterion("has_water_gen", InventoryChangeTrigger.TriggerInstance.hasItems(MWGRBlocks.WATER_GENERATOR.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MWGRMod.MOD_ID, "get_water_gen").toString());

        Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStack(MWGRBlocks.LAVA_GENERATOR.get()),
                        Component.translatable("advancements.mwgr.get_lava_gen.title"),
                        Component.translatable("advancements.mwgr.get_lava_gen.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false))
                .addCriterion("has_lava_gen", InventoryChangeTrigger.TriggerInstance.hasItems(MWGRBlocks.LAVA_GENERATOR.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MWGRMod.MOD_ID, "get_lava_gen").toString());

        Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStack(MWGRBlocks.HEAVY_WATER_GENERATOR.get()),
                        Component.translatable("advancements.mwgr.get_heavywater_gen.title"),
                        Component.translatable("advancements.mwgr.get_heavywater_gen.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false))
                .addCriterion("has_heavywater_gen", InventoryChangeTrigger.TriggerInstance.hasItems(MWGRBlocks.HEAVY_WATER_GENERATOR.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MWGRMod.MOD_ID, "get_heavywater_gen").toString());
    }
}