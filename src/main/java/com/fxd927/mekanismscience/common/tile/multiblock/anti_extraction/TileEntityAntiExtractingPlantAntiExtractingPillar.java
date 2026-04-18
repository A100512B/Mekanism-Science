package com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction;

import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.common.tile.prefab.TileEntityInternalMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAntiExtractingPlantAntiExtractingPillar extends TileEntityInternalMultiblock {

    public TileEntityAntiExtractingPlantAntiExtractingPillar(BlockPos pos, BlockState state) {
        super(MSBlocks.ANTI_EXTRACTING_PILLAR, pos, state);
    }
}
