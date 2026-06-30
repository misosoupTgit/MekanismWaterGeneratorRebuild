package com.github.misosoupTgit.mwgr.datagen.server;

import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.block.MWGRBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MWGRBlockTagsProvider extends BlockTagsProvider {
    public MWGRBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MWGRMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MWGRBlocks.WATER_GENERATOR.get())
                .add(MWGRBlocks.LAVA_GENERATOR.get());
    }
}
