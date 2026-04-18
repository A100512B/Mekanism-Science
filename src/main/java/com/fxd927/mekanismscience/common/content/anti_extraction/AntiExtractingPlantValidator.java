package com.fxd927.mekanismscience.common.content.anti_extraction;

import com.fxd927.mekanismscience.common.registries.MSBlockTypes;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.math.voxel.VoxelCuboid;
import mekanism.common.lib.multiblock.CuboidStructureValidator;
import mekanism.common.lib.multiblock.FormationProtocol.CasingType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class AntiExtractingPlantValidator extends CuboidStructureValidator<AntiExtractingPlantMultiblockData> {

    private int pillars;

    public AntiExtractingPlantValidator() {
        super(new VoxelCuboid(5, 5, 7), new VoxelCuboid(15, 15, 25));
    }

    @Override
    protected CasingType getCasingType(BlockState state) {
        Block block = state.getBlock();
        if (BlockType.is(block, MSBlockTypes.EXTRACTING_PLANT_CASING)) {
            return CasingType.FRAME;
        } else if (BlockType.is(block, MSBlockTypes.EXTRACTING_PLANT_PORT)) {
            return CasingType.VALVE;
        } else if (BlockType.is(block, MSBlockTypes.EXTRACTING_PLANT_CONTROLLER)) {
            return CasingType.OTHER;
        }
        return CasingType.INVALID;
    }

    @Override
    protected boolean validateInner(BlockState state, Long2ObjectMap<ChunkAccess> chunkMap, BlockPos pos) {
        if (super.validateInner(state, chunkMap, pos)) {
            return true;
        }
        return BlockType.is(state.getBlock(), MSBlockTypes.EXTRACTING_PILLAR);
    }
}
