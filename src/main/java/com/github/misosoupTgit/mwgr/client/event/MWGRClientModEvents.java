package com.github.misosoupTgit.mwgr.client.event;

import com.github.misosoupTgit.mwgr.client.tooltip.aprilfool.WaveNameClientTooltipComponent;
import com.github.misosoupTgit.mwgr.client.tooltip.aprilfool.WaveNameTooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MWGRClientModEvents {

    @SubscribeEvent
    public static void onRegisterTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(WaveNameTooltipComponent.class, WaveNameClientTooltipComponent::new);
    }
}
