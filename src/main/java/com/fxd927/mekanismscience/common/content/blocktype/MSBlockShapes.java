package com.fxd927.mekanismscience.common.content.blocktype;

import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class MSBlockShapes {

    private MSBlockShapes() {}

    private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static final VoxelShape[] METAL_ELECTROLYSIS_ROD = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];

    static {
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
            box(4, 4, 0, 12, 12, 2), // port1
            box(4, 4, 14, 12, 12, 16), // port2
            box(5, 5, 2, 11, 11, 14) // middle
        ), METAL_ELECTROLYSIS_ROD);
    }
}
