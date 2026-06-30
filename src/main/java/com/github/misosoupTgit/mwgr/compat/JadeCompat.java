package com.github.misosoupTgit.mwgr.compat;

import com.github.misosoupTgit.mwgr.MWGRLang;
import com.github.misosoupTgit.mwgr.MWGRMod;
import com.github.misosoupTgit.mwgr.block.FluidGeneratorBlock;
import com.github.misosoupTgit.mwgr.block.FluidGeneratorBlockEntity;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
    public static final ResourceLocation AUTO_EJECT = MWGRMod.rl("auto_eject");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                if (accessor.getBlockEntity() instanceof FluidGeneratorBlockEntity generator) {
                    boolean autoEject = generator.isAutoEject();
                    tooltip.add(MWGRLang.STATS_AUTO_EJECT.translate(
                            autoEject ? MekanismLang.ON.translateColored(EnumColor.BRIGHT_GREEN)
                                      : MekanismLang.OFF.translateColored(EnumColor.RED)
                    ));
                }
            }

            @Override
            public ResourceLocation getUid() {
                return AUTO_EJECT;
            }
        }, FluidGeneratorBlock.class);
    }
}
