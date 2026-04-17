package com.fxd927.mekanismscience.common.tile.multiblock.extraction;

import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.common.tile.prefab.TileEntityInternalMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityExtractingPlantExtractingPillar extends TileEntityInternalMultiblock {

    public TileEntityExtractingPlantExtractingPillar(BlockPos pos, BlockState state) {
        super(MSBlocks.EXTRACTING_PILLAR, pos, state);
    }
}
