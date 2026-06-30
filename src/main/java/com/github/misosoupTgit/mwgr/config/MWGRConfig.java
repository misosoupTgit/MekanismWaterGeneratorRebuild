package com.github.misosoupTgit.mwgr.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MWGRConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue DEFAULT_AUTO_EJECT;
    public static final ForgeConfigSpec.BooleanValue ALLOW_ALL_MODES;

    static {
        BUILDER.push("General Settings");

        DEFAULT_AUTO_EJECT = BUILDER
                .comment("Initial state of auto-eject when the block is placed.")
                .define("defaultAutoEject", false);

        ALLOW_ALL_MODES = BUILDER
                .comment("If true, auto-eject can be toggled in any Configurator mode. If false, only WRENCH mode is allowed.")
                .define("allowAllModes", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}