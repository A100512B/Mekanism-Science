package com.fxd927.mekanismscience.common.content.anti_extraction;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.registries.MSBlockTypes;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantAntiExtractingPillar;
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

public class AntiExtractingPlantValidator extends CuboidStructureValidator<AntiExtractingPlantMultiblockData> {

    private int pillars;

    public AntiExtractingPlantValidator() {
        super(new VoxelCuboid(5, 7, 5), new VoxelCuboid(15, 15, 25));
    }

    @Override
    protected CasingType getCasingType(BlockState state) {
        Block block = state.getBlock();
        if (BlockType.is(block, MSBlockTypes.ANTI_EXTRACTING_PLANT_CASING)) {
            return CasingType.FRAME;
        } else if (BlockType.is(block, MSBlockTypes.ANTI_EXTRACTING_PLANT_PORT)) {
            return CasingType.VALVE;
        }
        return CasingType.INVALID;
    }

    @Override
    protected boolean validateInner(BlockState state, Long2ObjectMap<ChunkAccess> chunkMap, BlockPos pos) {
        if (super.validateInner(state, chunkMap, pos)) {
            return true;
        }
        return BlockType.is(state.getBlock(), MSBlockTypes.ANTI_EXTRACTING_PILLAR);
    }

    @Override
    public FormationResult postcheck(AntiExtractingPlantMultiblockData structure, Long2ObjectMap<ChunkAccess> chunkMap) {
        if (cuboid.length() != cuboid.width()) return FormationResult.fail(MSLang.ANTI_EXTRACTING_PLANT_INVALID_NOT_SQUARE);
        if ((cuboid.length() & 1) == 0 || (cuboid.width() & 1) == 0) return FormationResult.fail(MSLang.ANTI_EXTRACTING_PLANT_INVALID_EVEN_LENGTH);
        for (BlockPos pos : structure.internalLocations) {
            BlockEntity tile = WorldUtils.getTileEntity(TileEntityAntiExtractingPlantAntiExtractingPillar.class, world, chunkMap, pos);
            if (shouldPosBePillar(pos) && tile == null
                    || (!shouldPosBePillar(pos) && tile != null))
                return FormationResult.fail(MSLang.ANTI_EXTRACTING_PLANT_INVALID_MALFORMED_ANTI_EXTRACTING_PILLARS);
            if (tile != null) pillars += 1;
        }
        structure.setAntiExtractantTankCapacity(pillars * MSConfig.generalConfig.antiExtractionAntiExtractantPerTank.get());
        return FormationResult.SUCCESS;
    }

    private boolean shouldPosBePillar(BlockPos pos) {
        BlockPos relative = pos.subtract(cuboid.getMinPos());
        int x = relative.getX(), z = relative.getZ(), l = cuboid.length();
        // Similar to extracting plant, just invert the first condition
        return !(x == z || x + z == l - 1) && (0 < x && x < l) && (0 < z && z < l);
    }
}
