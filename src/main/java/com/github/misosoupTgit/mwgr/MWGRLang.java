package com.github.misosoupTgit.mwgr;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MWGRLang implements ILangEntry {
    MWGR("constants", "mod_name"),
    HOLD_FOR_DETAILS("tooltip", "hold_for_details"),
    HOLD_FOR_STATS("tooltip", "hold_for_stats"),
    SHIFT("tooltip", "shift"),
    CTRL("tooltip", "ctrl"),
    DESCRIPTION_WATER_GENERATOR("description", "water_generator"),
    DESCRIPTION_LAVA_GENERATOR("description", "lava_generator"),
    STATS_AUTO_EJECT("stats", "auto_eject");

    private final String key;

    MWGRLang(String type, String path) {
        this.key = Util.makeDescriptionId(type, MWGRMod.rl(path));
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}
