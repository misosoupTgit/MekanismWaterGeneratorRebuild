package com.github.misosoupTgit.mwgr.datagen.server.loot;

import com.github.misosoupTgit.mwgr.block.MWGRBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MWGRBlockLootTables extends BlockLootSubProvider {
    protected MWGRBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(MWGRBlocks.WATER_GENERATOR.get());
        this.dropSelf(MWGRBlocks.LAVA_GENERATOR.get());
        this.dropSelf(MWGRBlocks.HEAVY_WATER_GENERATOR.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MWGRBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
