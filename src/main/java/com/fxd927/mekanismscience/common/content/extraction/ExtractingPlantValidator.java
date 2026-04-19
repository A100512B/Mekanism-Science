package com.fxd927.mekanismscience.common.content.extraction;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.registries.MSBlockTypes;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantExtractingPillar;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.math.voxel.VoxelCuboid;
import mekanism.common.lib.multiblock.CuboidStructureValidator;
import mekanism.common.lib.multiblock.FormationProtocol.CasingType;
import mekanism.common.lib.multiblock.FormationProtocol.FormationResult;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.stream.Collectors;

public class ExtractingPlantValidator extends CuboidStructureValidator<ExtractingPlantMultiblockData> {

    private int pillars;

    public ExtractingPlantValidator() {
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

    @Override
    public FormationResult postcheck(ExtractingPlantMultiblockData structure, Long2ObjectMap<ChunkAccess> chunkMap) {
        if (cuboid.length() != cuboid.width()) return FormationResult.fail(MSLang.EXTRACTING_PLANT_INVALID_NOT_SQUARE);
        if ((cuboid.length() & 1) == 0 || (cuboid.width() & 1) == 0) return FormationResult.fail(MSLang.EXTRACTING_PLANT_INVALID_EVEN_LENGTH);
        for (BlockPos pos : structure.internalLocations) {
            BlockEntity tile = WorldUtils.getTileEntity(TileEntityExtractingPlantExtractingPillar.class, world, chunkMap, pos);
            if (shouldPosBePillar(pos) && tile == null
                    || (!shouldPosBePillar(pos) && tile != null))
                return FormationResult.fail(MSLang.EXTRACTING_PLANT_INVALID_MALFORMED_EXTRACTING_PILLARS);
        }
        structure.setExtractantTankCapacity(pillars * MSConfig.generalConfig.extractionExtractantPerTank.get());
        return FormationResult.SUCCESS;
    }

    private boolean shouldPosBePillar(BlockPos pos) {
        BlockPos relative = pos.subtract(cuboid.getMinPos());
        int x = relative.getX(), z = relative.getZ(), l = cuboid.length();
        // If we set the relative (0, 0) on the XZ Plane as the original point,
        // the pillars should be on the line z = x or z = length() - x
        // Remember that a block's pos is floor of its center pos
        // Also, we use internalLocations so that we don't need to check Y pos
        return (x == z || x + z == l - 1) && (0 < x && x < l) && (0 < z && z < l);
    }
}
