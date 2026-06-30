package com.github.misosoupTgit.mwgr.datagen.client;
 
import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.block.MWGRBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
 
import java.util.Locale;
 
public class ENUSLanguageProvider extends LanguageProvider {
    public ENUSLanguageProvider(PackOutput output) {
        super(output, MWGRMod.MOD_ID, Locale.US.toString().toLowerCase());
    }
 
    @Override
    protected void addTranslations() {
        addBlock(MWGRBlocks.WATER_GENERATOR, "Water Generator");
        addBlock(MWGRBlocks.LAVA_GENERATOR, "Lava Generator");
        add("message.mwgr.auto_eject.on", "§3Auto Eject: Enabled");
        add("message.mwgr.auto_eject.off", "§cAuto Eject: Disabled");
 
        add("advancements.mwgr.get_water_gen.title", "No Turbine Needed");
        add("advancements.mwgr.get_water_gen.description", "Get a Water Generator");
 
        add("advancements.mwgr.get_lava_gen.title", "PC Friendly Obsidian Farming");
        add("advancements.mwgr.get_lava_gen.description", "Get a Lava Generator");
        add("tooltip.mwgr.hold_for_details", "Hold %s for details");
        add("tooltip.mwgr.hold_for_stats", "Hold %s for stats");
        add("tooltip.mwgr.shift", "SHIFT");
        add("tooltip.mwgr.ctrl", "CTRL");
        add("description.mwgr.water_generator", "  Provides an infinite supply of water.");
        add("description.mwgr.lava_generator", "  Provides an infinite supply of lava.");
        add("stats.mwgr.auto_eject", "Auto Eject: %s");
        add("config.jade.plugin_mwgr.auto_eject", "Auto Eject Status");
    }
}
