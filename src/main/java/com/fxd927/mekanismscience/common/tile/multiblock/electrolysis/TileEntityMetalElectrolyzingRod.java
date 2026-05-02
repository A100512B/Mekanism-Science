package com.fxd927.mekanismscience.common.tile.multiblock.electrolysis;

import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.common.tile.prefab.TileEntityInternalMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMetalElectrolyzingRod extends TileEntityInternalMultiblock {

    public TileEntityMetalElectrolyzingRod(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYZING_ROD, pos, state);
    }
}
