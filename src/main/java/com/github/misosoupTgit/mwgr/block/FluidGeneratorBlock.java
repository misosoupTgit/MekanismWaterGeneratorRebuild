package com.github.misosoupTgit.mwgr.block;

import com.github.misosoupTgit.mwgr.compat.MekanismCompat;
import com.github.misosoupTgit.mwgr.compat.MekanismIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FluidGeneratorBlock extends Block implements EntityBlock {
    private final Supplier<BlockEntityType<FluidGeneratorBlockEntity>> typeSupplier;
    private final Supplier<Fluid> fluidSupplier;
    protected final String descriptionKey;

    public FluidGeneratorBlock(Properties props, Supplier<BlockEntityType<FluidGeneratorBlockEntity>> typeSupplier,
            Supplier<Fluid> fluidSupplier, String descriptionKey) {
        super(props);
        this.typeSupplier = typeSupplier;
        this.fluidSupplier = fluidSupplier;
        this.descriptionKey = descriptionKey;
    }

    public Fluid getFluid() {
        return fluidSupplier.get();
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
            @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        // バケツ処理
        Fluid currentFluid = getFluid();
        if (stack.is(Items.BUCKET)) {
            if (currentFluid == Fluids.WATER) {
                level.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.setItemInHand(hand,
                        ItemUtils.createFilledResult(stack, player, new ItemStack(Items.WATER_BUCKET)));
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else if (currentFluid == Fluids.LAVA) {
                level.playSound(player, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.setItemInHand(hand,
                        ItemUtils.createFilledResult(stack, player, new ItemStack(Items.LAVA_BUCKET)));
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        if (MekanismCompat.isMekanismLoaded()) {
            return MekanismIntegration.handleConfigurator(level, pos, player, hand, this);
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return typeSupplier.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state,
            @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : (lvl, pos, st, be) -> {
            if (be instanceof FluidGeneratorBlockEntity generatorBE) {
                generatorBE.serverTick();
            }
        };
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }
}