package com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantMultiblockData;
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

public class TileEntityAntiExtractingPlantCasing extends TileEntityMultiblock<AntiExtractingPlantMultiblockData> {

    private boolean handleSound;

    public TileEntityAntiExtractingPlantCasing(IBlockProvider block, BlockPos pos, BlockState state) {
        super(block, pos, state);
    }

    public TileEntityAntiExtractingPlantCasing(BlockPos pos, BlockState state) {
        super(MSBlocks.ANTI_EXTRACTING_PLANT_CASING, pos, state);
    }

    @Override
    public AntiExtractingPlantMultiblockData createMultiblock() {
        return new AntiExtractingPlantMultiblockData(this);
    }

    @Override
    public MultiblockManager<AntiExtractingPlantMultiblockData> getManager() {
        return MekanismScience.antiExtractingPlantManager;
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
        AntiExtractingPlantMultiblockData multiblock = getMultiblock();
        tag.putBoolean(NBTConstants.HANDLE_SOUND, multiblock.isFormed() && multiblock.handlesSound(this));
        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag) {
        super.handleUpdateTag(tag);
        AntiExtractingPlantMultiblockData multiblock = getMultiblock();
        NBTUtils.setBooleanIfPresent(tag, NBTConstants.HANDLE_SOUND, value -> handleSound = value);
    }
}
