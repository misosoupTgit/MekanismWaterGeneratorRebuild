package com.github.misosoupTgit.mwgr.compat;

import mekanism.common.item.ItemConfigurator;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import com.github.misosoupTgit.mwgr.block.FluidGeneratorBlockEntity;
import com.github.misosoupTgit.mwgr.config.MWGRConfig;

public class MekanismIntegration {

    public static InteractionResult handleConfigurator(Level level, BlockPos pos, Player player, InteractionHand hand, Block block) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof ItemConfigurator configurator) {
            String modeName = configurator.getMode(stack).name();
            boolean isWrench = modeName.equals("WRENCH");

            if (isWrench && player.isShiftKeyDown()) {
                if (!level.isClientSide) {
                    level.removeBlock(pos, false);
                    Block.popResource(level, pos, new ItemStack(block));
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }

            boolean canToggle = MWGRConfig.ALLOW_ALL_MODES.get() || isWrench;

            if (canToggle && !player.isShiftKeyDown()) {
                if (level.getBlockEntity(pos) instanceof FluidGeneratorBlockEntity generatorBE) {
                    if (!level.isClientSide) {
                        boolean currentState = generatorBE.toggleAutoEject();
                        String key = currentState ? "message.mwgr.auto_eject.on" : "message.mwgr.auto_eject.off";
                        player.displayClientMessage(Component.translatable(key), true);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}