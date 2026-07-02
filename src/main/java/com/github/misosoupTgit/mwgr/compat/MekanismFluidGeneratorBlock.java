package com.github.misosoupTgit.mwgr.compat;

import com.github.misosoupTgit.mwgr.block.FluidGeneratorBlock;
import com.github.misosoupTgit.mwgr.block.FluidGeneratorBlockEntity;
import mekanism.api.text.ILangEntry;
import mekanism.common.block.interfaces.IHasDescription;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class MekanismFluidGeneratorBlock extends FluidGeneratorBlock implements IHasDescription {
    private final ILangEntry langEntryDescription;

    public MekanismFluidGeneratorBlock(Properties props, Supplier<BlockEntityType<FluidGeneratorBlockEntity>> typeSupplier, Supplier<Fluid> fluidSupplier, String descriptionKey) {
        super(props, typeSupplier, fluidSupplier, descriptionKey);
        this.langEntryDescription = new ILangEntry() {
            @Override
            public String getTranslationKey() {
                return descriptionKey;
            }
        };
    }

    @Override
    public ILangEntry getDescription() {
        return langEntryDescription;
    }
}
