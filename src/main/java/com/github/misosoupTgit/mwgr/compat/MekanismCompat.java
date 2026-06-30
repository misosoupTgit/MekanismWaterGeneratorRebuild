package com.github.misosoupTgit.mwgr.compat;

import net.minecraftforge.fml.ModList;

public class MekanismCompat {
    public static final String MEKANISM_MODID = "mekanism";

    public static boolean isMekanismLoaded() {
        return ModList.get().isLoaded(MEKANISM_MODID);
    }
}