package com.fxd927.mekanismscience.common.tile.multiblock.extraction;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.NBTConstants;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityMultiblock;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityExtractingPlantCasing extends TileEntityMultiblock<ExtractingPlantMultiblockData> {

    private boolean handleSound;

    public TileEntityExtractingPlantCasing(IBlockProvider block, BlockPos pos, BlockState state) {
        super(block, pos, state);
    }

    public TileEntityExtractingPlantCasing(BlockPos pos, BlockState state) {
        super(MSBlocks.EXTRACTING_PLANT_CASING, pos, state);
    }

    @Override
    public ExtractingPlantMultiblockData createMultiblock() {
        return new ExtractingPlantMultiblockData(this);
    }

    @Override
    public MultiblockManager<ExtractingPlantMultiblockData> getManager() {
        return MekanismScience.extractingPlantManager;
    }

    @Override
    public boolean canBeMaster() {
        return false;
    }

    @Override
    protected boolean canPlaySound() {
        return getMultiblock().isFormed() && handleSound;
    }

    @Override
    @NotNull
    public CompoundTag getReducedUpdateTag() {
        CompoundTag tag = super.getReducedUpdateTag();
        ExtractingPlantMultiblockData multiblock = getMultiblock();
        tag.putBoolean(NBTConstants.HANDLE_SOUND, multiblock.isFormed() && multiblock.handlesSound(this) && multiblock.lastGain > 0);
        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag) {
        super.handleUpdateTag(tag);
        NBTUtils.setBooleanIfPresent(tag, NBTConstants.HANDLE_SOUND, value -> handleSound = value);
    }

}
