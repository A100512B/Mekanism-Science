package com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction;

import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAntiExtractingPlantController extends TileEntityAntiExtractingPlantCasing {

    public TileEntityAntiExtractingPlantController(BlockPos pos, BlockState state) {
        super(MSBlocks.ANTI_EXTRACTING_PLANT_CONTROLLER, pos, state);
        delaySupplier = NO_DELAY;
    }

    @Override
    protected boolean onUpdateServer(AntiExtractingPlantMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        setActive(multiblock.isFormed());
        return needsPacket;
    }

    @Override
    public boolean canBeMaster() {
        return true;
    }

}
