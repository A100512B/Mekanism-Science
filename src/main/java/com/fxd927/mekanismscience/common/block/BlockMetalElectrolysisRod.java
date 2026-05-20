package com.fxd927.mekanismscience.common.block;

import com.fxd927.mekanismscience.common.block.attribute.AttributeStateElectrolyzingRodMode;
import com.fxd927.mekanismscience.common.registries.MSBlockTypes;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolyzingRod;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class BlockMetalElectrolysisRod extends BlockTileModel<TileEntityMetalElectrolyzingRod, BlockTypeTile<TileEntityMetalElectrolyzingRod>> {

    public BlockMetalElectrolysisRod() {
        super(MSBlockTypes.METAL_ELECTROLYZING_ROD, properties -> properties.mapColor(MapColor.METAL));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return switch (state.getValue(AttributeStateElectrolyzingRodMode.modeProperty)) {
            case IDLE -> 0;
            case ACTIVE -> 8;
            case ACTIVE_LASER -> 15;
        };
    }
}
