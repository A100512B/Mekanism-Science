package com.fxd927.mekanismscience.common.content.electrolysis;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData.RodData;
import com.fxd927.mekanismscience.common.registries.MSBlockTypes;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberLaserAcceptor;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberPort;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolyzingRod;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mekanism.common.MekanismLang;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.math.voxel.VoxelCuboid;
import mekanism.common.lib.multiblock.CuboidStructureValidator;
import mekanism.common.lib.multiblock.FormationProtocol;
import mekanism.common.lib.multiblock.FormationProtocol.CasingType;
import mekanism.common.lib.multiblock.FormationProtocol.FormationResult;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.List;

public class MetalElectrolysisChamberValidator extends CuboidStructureValidator<MetalElectrolysisChamberMultiblockData> {

    public MetalElectrolysisChamberValidator() {
        super(new VoxelCuboid(5, 5, 5), new VoxelCuboid(16, 16, 16));
    }

    @Override
    protected CasingType getCasingType(BlockState state) {
        Block block = state.getBlock();
        if (BlockType.is(block, MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER_CASING)) {
            return CasingType.FRAME;
        } else if (BlockType.is(block, MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER_PORT)) {
            return CasingType.VALVE;
        } else if (BlockType.is(block, MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR)) {
            return CasingType.OTHER;
        } else {
            return CasingType.INVALID;
        }
    }

    @Override
    protected FormationResult validateFrame(FormationProtocol<MetalElectrolysisChamberMultiblockData> ctx, BlockPos pos, BlockState state, CasingType type, boolean needsFrame) {
        if (pos.getY() == cuboid.getMinPos().getY() || pos.getY() == cuboid.getMaxPos().getY()) {
            if (type != CasingType.FRAME && type != CasingType.VALVE)
                return FormationResult.fail(MekanismLang.MULTIBLOCK_INVALID_FRAME, pos);
        }
        return super.validateFrame(ctx, pos, state, type, needsFrame);
    }

    @Override
    protected boolean validateInner(BlockState state, Long2ObjectMap<ChunkAccess> chunkMap, BlockPos pos) {
        if (super.validateInner(state, chunkMap, pos)) {
            return true;
        }
        return BlockType.is(state.getBlock(), MSBlockTypes.METAL_ELECTROLYZING_ROD);
    }

    @Override
    public FormationResult postcheck(MetalElectrolysisChamberMultiblockData structure, Long2ObjectMap<ChunkAccess> chunkMap) {
        BlockPos minPos = structure.getMinPos();
        Direction.Axis globalAxis = WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberCasing.class, world, chunkMap,
                minPos.offset(1, 1, 0)) != null ? Direction.Axis.Z : Direction.Axis.X;
        List<RodData> rodDataList = new ObjectArrayList<>();
        // Check the middle layers one by one
        for (int y = 1; y < cuboid.height() - 1; y++) {
            if (globalAxis == Direction.Axis.Z) {
                for (int x = 0; x < cuboid.length() - 1; x++) {
                    // This line should be casing or port or laser acceptor
                    BlockPos posToCheck = minPos.offset(x, y, 0);
                    BlockEntity tile = WorldUtils.getTileEntity(world, chunkMap, posToCheck);
                    if (tile instanceof TileEntityMetalElectrolysisChamberCasing) {
                        if (tile instanceof TileEntityMetalElectrolysisChamberPort) {
                            if (shouldNotBeRods(rodDataList, posToCheck)) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD_TOO_CLOSE, posToCheck);
                            }
                            for (int z = 1; z < cuboid.width() - 2; z++) {
                                posToCheck = minPos.offset(x, y, z);
                                if (WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, world, chunkMap, posToCheck) == null)
                                    return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            posToCheck = posToCheck.relative(Direction.Axis.Z, 1);
                            if (WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberPort.class, world, chunkMap, posToCheck) == null) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            rodDataList.add(new RodData(minPos.offset(x, y, 0), posToCheck, false, false));
                            ((TileEntityMetalElectrolysisChamberPort) tile).index = rodDataList.size() - 1;
                        } else if (tile instanceof TileEntityMetalElectrolysisChamberLaserAcceptor) {
                            if (shouldNotBeRods(rodDataList, posToCheck)) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD_TOO_CLOSE, posToCheck);
                            }
                            for (int z = 1; z < cuboid.width() - 2; z++) {
                                posToCheck = minPos.offset(x, y, z);
                                if (WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, world, chunkMap, posToCheck) == null)
                                    return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            posToCheck = posToCheck.relative(Direction.Axis.Z, 1);
                            if (WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberLaserAcceptor.class, world, chunkMap, posToCheck) == null) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            rodDataList.add(new RodData(minPos.offset(x, y, 0), posToCheck, true, false));
                            ((TileEntityMetalElectrolysisChamberLaserAcceptor) tile).index = rodDataList.size() - 1;
                        } else {
                            // Shouldn't be rods
                            for (int z = 1; z < cuboid.width() - 2; z++) {
                                posToCheck = minPos.offset(x, y, z);
                                if (WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, world, chunkMap, posToCheck) != null)
                                    return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            posToCheck = posToCheck.relative(Direction.Axis.Z, 1);
                            var tile1 = WorldUtils.getTileEntity(world, chunkMap, posToCheck);
                            if (tile1 instanceof TileEntityMetalElectrolysisChamberPort
                                    || tile1 instanceof TileEntityMetalElectrolysisChamberLaserAcceptor) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                        }
                    } else {
                        return FormationResult.fail(MekanismLang.MULTIBLOCK_INVALID_FRAME, posToCheck);
                    }
                }
            } else {
                for (int z = 0; z < cuboid.width() - 1; z++) {
                    // This line should be casing or port or laser acceptor
                    BlockPos posToCheck = minPos.offset(z, y, 0);
                    BlockEntity tile = WorldUtils.getTileEntity(world, chunkMap, posToCheck);
                    if (tile instanceof TileEntityMetalElectrolysisChamberCasing) {
                        if (tile instanceof TileEntityMetalElectrolysisChamberPort) {
                            if (shouldNotBeRods(rodDataList, posToCheck)) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD_TOO_CLOSE, posToCheck);
                            }
                            for (int x = 1; x < cuboid.length() - 2; x++) {
                                posToCheck = minPos.offset(x, y, z);
                                if (WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, world, chunkMap, posToCheck) == null)
                                    return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            posToCheck = posToCheck.relative(Direction.Axis.X, 1);
                            if (WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberPort.class, world, chunkMap, posToCheck) == null) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            rodDataList.add(new RodData(minPos.offset(0, y, z), posToCheck, false, false));
                            ((TileEntityMetalElectrolysisChamberPort) tile).index = rodDataList.size() - 1;
                        } else if (tile instanceof TileEntityMetalElectrolysisChamberLaserAcceptor) {
                            if (shouldNotBeRods(rodDataList, posToCheck)) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD_TOO_CLOSE, posToCheck);
                            }
                            for (int x = 1; x < cuboid.length() - 2; x++) {
                                posToCheck = minPos.offset(x, y, z);
                                if (WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, world, chunkMap, posToCheck) == null)
                                    return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            posToCheck = posToCheck.relative(Direction.Axis.X, 1);
                            if (WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberLaserAcceptor.class, world, chunkMap, posToCheck) == null) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            ((TileEntityMetalElectrolysisChamberLaserAcceptor) tile).index = rodDataList.size() - 1;
                        } else {
                            // Shouldn't be rods
                            for (int x = 1; x < cuboid.length() - 2; x++) {
                                posToCheck = minPos.offset(x, y, x);
                                if (WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, world, chunkMap, posToCheck) != null)
                                    return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                            posToCheck = posToCheck.relative(Direction.Axis.Z, 1);
                            var tile1 = WorldUtils.getTileEntity(world, chunkMap, posToCheck);
                            if (tile1 instanceof TileEntityMetalElectrolysisChamberPort
                                    || tile1 instanceof TileEntityMetalElectrolysisChamberLaserAcceptor) {
                                return FormationResult.fail(MSLang.METAL_ELECTROLYSIS_CHAMBER_INVALID_ROD, posToCheck);
                            }
                        }
                    } else {
                        return FormationResult.fail(MekanismLang.MULTIBLOCK_INVALID_FRAME, posToCheck);
                    }
                }
            }
        }
        structure.rodsList = rodDataList;
        return FormationResult.SUCCESS;
    }

    private static boolean shouldNotBeRods(List<RodData> existedRods, BlockPos posToCheck) {
        // Assume posToCheck must be the min pos
        return existedRods.stream().anyMatch(rod -> rod.minPos.distManhattan(posToCheck) <= (rod.laser ? 2 : 1));
    }
}
