package com.fxd927.mekanismscience.common.tile.multiblock.extraction;

import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityExtractingPlantController extends TileEntityExtractingPlantCasing {

    public TileEntityExtractingPlantController(BlockPos pos, BlockState state) {
        super(MSBlocks.EXTRACTING_PLANT_CONTROLLER, pos, state);
        delaySupplier = NO_DELAY;
    }

    @Override
    protected boolean onUpdateServer(ExtractingPlantMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        setActive(multiblock.isFormed());
        return needsPacket;
    }

    @Override
    public boolean canBeMaster() {
        return true;
    }
}
