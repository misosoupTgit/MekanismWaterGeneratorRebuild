package com.github.misosoupTgit.mwgr.datagen.client;
 
import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.block.MWGRBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
 
import java.util.Locale;
 
public class JAJPLanguageProvider extends LanguageProvider {
    public JAJPLanguageProvider(PackOutput output) {
        super(output, MWGRMod.MOD_ID, Locale.JAPAN.toString().toLowerCase());
    }
 
    @Override
    protected void addTranslations() {
        addBlock(MWGRBlocks.WATER_GENERATOR, "水生成機");
        addBlock(MWGRBlocks.LAVA_GENERATOR, "溶岩生成機");
        add("message.mwgr.auto_eject.on", "§3自動搬出: 有効");
        add("message.mwgr.auto_eject.off", "§c自動搬出: 無効");
 
        add("advancements.mwgr.get_water_gen.title", "タービンいらず");
        add("advancements.mwgr.get_water_gen.description", "水生成機を入手する");
 
        add("advancements.mwgr.get_lava_gen.title", "PCに優しい黒曜石量産方法");
        add("advancements.mwgr.get_lava_gen.description", "溶岩生成機を入手する");
        add("tooltip.mwgr.hold_for_details", "%s を押して詳細を表示");
        add("tooltip.mwgr.hold_for_stats", "%s を押してステータスを表示");
        add("tooltip.mwgr.shift", "SHIFT");
        add("tooltip.mwgr.ctrl", "CTRL");
        add("description.mwgr.water_generator", "  水を無限に供給します。");
        add("description.mwgr.lava_generator", "  溶岩を無限に供給します。");
        add("stats.mwgr.auto_eject", "自動搬出: %s");
        add("config.jade.plugin_mwgr.auto_eject", "自動搬出の状態");
    }
}
