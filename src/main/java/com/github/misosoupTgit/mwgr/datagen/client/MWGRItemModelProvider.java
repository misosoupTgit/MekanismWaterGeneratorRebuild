package com.github.misosoupTgit.mwgr.datagen.client;

import com.github.misosoupTgit.mwgr.MWGRMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWGRItemModelProvider extends ItemModelProvider {
    public MWGRItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MWGRMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
