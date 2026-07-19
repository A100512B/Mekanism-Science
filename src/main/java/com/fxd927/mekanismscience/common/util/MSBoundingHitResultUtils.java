package com.fxd927.mekanismscience.common.util;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.registries.MSBlockTypes;
import mekanism.common.block.BlockBounding;
import mekanism.common.content.blocktype.BlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class MSBoundingHitResultUtils {

    private static final double HIT_POSITION_EPSILON = 1.0E-5;

    private MSBoundingHitResultUtils() {
    }

    public static BlockHitResult normalize(Level level, BlockHitResult result) {
        BlockPos hitPos = result.getBlockPos();
        BlockPos mainPos = getLargeMachineMainPos(level, hitPos);
        if (mainPos == null) {
            return result;
        }
        int maxYOffset = getLargeMachineMaxYOffset(level.getBlockState(mainPos));
        BlockPos actualHitPos = findActualBoundingPos(level, result, mainPos, maxYOffset);
        if (actualHitPos == null) {
            if (isTooFarFromHitBlock(result, hitPos)) {
                MekanismScience.LOGGER.warn("Unable to remap large machine hit result from hit block {} and main block {} toward hit location {} on face {}.",
                        hitPos, mainPos, result.getLocation(), result.getDirection());
            }
            return result;
        }
        return actualHitPos.equals(hitPos) ? result : result.withPosition(actualHitPos);
    }

    private static BlockPos getLargeMachineMainPos(Level level, BlockPos hitPos) {
        BlockState hitState = level.getBlockState(hitPos);
        if (isLargeMachineMainBlock(hitState)) {
            return hitPos;
        }
        if (hitState.getBlock() instanceof BlockBounding) {
            BlockPos mainPos = BlockBounding.getMainBlockPos(level, hitPos);
            if (mainPos != null && isLargeMachineMainBlock(level.getBlockState(mainPos))) {
                return mainPos;
            }
        }
        return null;
    }

    private static boolean isLargeMachineMainBlock(BlockState state) {
        return BlockType.is(state.getBlock(), MSBlockTypes.ACID_LEACHER) ||
                BlockType.is(state.getBlock(), MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER);
    }

    private static int getLargeMachineMaxYOffset(BlockState state) {
        if (BlockType.is(state.getBlock(), MSBlockTypes.ACID_LEACHER)) {
            return 1;
        }
        if (BlockType.is(state.getBlock(), MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER)) {
            return 2;
        }
        throw new IllegalArgumentException("Expected a Mekanism: Science large machine main block, but got " + state);
    }

    private static BlockPos findActualBoundingPos(Level level, BlockHitResult result, BlockPos mainPos, int maxYOffset) {
        Direction hitFace = result.getDirection();
        Vec3 hitLocation = result.getLocation();
        BlockPos hitPos = result.getBlockPos();
        if (isValidBoundingRemap(level, result, hitPos, mainPos, maxYOffset)) {
            return hitPos;
        }
        BlockPos adjustedPos = BlockPos.containing(hitLocation.relative(hitFace.getOpposite(), HIT_POSITION_EPSILON));
        if (isValidBoundingRemap(level, result, adjustedPos, mainPos, maxYOffset)) {
            return adjustedPos;
        }
        BlockPos rawPos = BlockPos.containing(hitLocation);
        if (isValidBoundingRemap(level, result, rawPos, mainPos, maxYOffset)) {
            return rawPos;
        }
        int minX = (int) Math.floor(hitLocation.x()) - 1;
        int minY = (int) Math.floor(hitLocation.y()) - 1;
        int minZ = (int) Math.floor(hitLocation.z()) - 1;
        int maxX = minX + 2;
        int maxY = minY + 2;
        int maxZ = minZ + 2;
        for (BlockPos candidate : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            if (isValidBoundingRemap(level, result, candidate, mainPos, maxYOffset)) {
                return candidate.immutable();
            }
        }
        return null;
    }

    private static boolean isValidBoundingRemap(Level level, BlockHitResult result, BlockPos candidate, BlockPos mainPos, int maxYOffset) {
        return isExpectedBoundingPosition(level, candidate, mainPos, maxYOffset) && !isTooFarFromHitBlock(result, candidate);
    }

    private static boolean isExpectedBoundingPosition(Level level, BlockPos candidate, BlockPos mainPos, int maxYOffset) {
        if (!(level.getBlockState(candidate).getBlock() instanceof BlockBounding)) {
            return false;
        }
        int offsetX = candidate.getX() - mainPos.getX();
        int offsetY = candidate.getY() - mainPos.getY();
        int offsetZ = candidate.getZ() - mainPos.getZ();
        return Math.abs(offsetX) <= 1 && offsetY >= 0 && offsetY <= maxYOffset && Math.abs(offsetZ) <= 1 &&
                (offsetX != 0 || offsetY != 0 || offsetZ != 0);
    }

    private static boolean isTooFarFromHitBlock(BlockHitResult result, BlockPos blockPos) {
        Vec3 offset = result.getLocation().subtract(Vec3.atCenterOf(blockPos));
        return Math.abs(offset.x()) >= 1.0000001D || Math.abs(offset.y()) >= 1.0000001D || Math.abs(offset.z()) >= 1.0000001D;
    }
}
