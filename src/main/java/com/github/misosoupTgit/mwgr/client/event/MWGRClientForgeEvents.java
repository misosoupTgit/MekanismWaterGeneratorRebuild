package com.github.misosoupTgit.mwgr.client.event;

import com.github.misosoupTgit.mwgr.client.tooltip.aprilfool.WaveNameTooltipComponent;
import com.github.misosoupTgit.mwgr.config.MWGRClientConfig;
import com.github.misosoupTgit.mwgr.item.MWGRColorBlockItems;
import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.time.MonthDay;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MWGRClientForgeEvents {

    private static final MonthDay APRIL_FOOLS = MonthDay.of(4, 1);

    private static boolean isAprilFools() {
        return APRIL_FOOLS.equals(MonthDay.from(ZonedDateTime.now(ZoneId.systemDefault())));
    }

    @SubscribeEvent
    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        if (!MWGRClientConfig.ENABLE_APRIL_FOOLS_EVENT.get()) return;
        if (!isAprilFools()) return;
        if (!(event.getItemStack().getItem() instanceof MWGRColorBlockItems)) return;

        List<Either<FormattedText, TooltipComponent>> elements = event.getTooltipElements();
        if (elements.isEmpty()) return;

        elements.set(0, Either.right(new WaveNameTooltipComponent(event.getItemStack().getHoverName())));
    }
}
