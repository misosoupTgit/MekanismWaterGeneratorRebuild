package com.github.misosoupTgit.mwgr.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MWGRColorBlockItems extends BlockItem {
    protected final int hexColor;
    protected final boolean isBold;

    public MWGRColorBlockItems(Block block, Properties properties, String hexColorString, boolean isBold) {
        super(block, properties);
        this.hexColor = Integer.decode(hexColorString);
        this.isBold = isBold;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.translatable(this.getDescriptionId())
                .withStyle(style -> style
                        .withColor(TextColor.fromRgb(hexColor))
                        .withBold(isBold)
                        .withItalic(false));
    }
}