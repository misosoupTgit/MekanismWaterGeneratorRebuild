package com.github.misosoupTgit.mwgr.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MWGRClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_APRIL_FOOLS_EVENT;

    static {
        BUILDER.push("Client Settings");

        ENABLE_APRIL_FOOLS_EVENT = BUILDER
                .comment("If true, generator block item names will display a wave animation on April 1st.")
                .define("enableAprilFoolsEvent", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
