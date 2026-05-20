package com.fxd927.mekanismscience.common.content.blocktype;

import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class MSBlockShapes {

    public static final VoxelShape[] ACID_LEACHER = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];

    private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static final VoxelShape[] METAL_ELECTROLYSIS_ROD = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];

    static {
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(4, 4, 0, 12, 12, 2), // port1
                box(4, 4, 14, 12, 12, 16), // port2
                box(5, 5, 2, 11, 11, 14), // middle
                box(6, 6, 3, 10, 10, 13) // inner
        ), METAL_ELECTROLYSIS_ROD);

        VoxelShapeUtils.setShape(VoxelShapeUtils.rotate(VoxelShapeUtils.combine(
                box(-15, 0, -15, 31, 5, 31), // body/base
                box(17, 5, -5, 30, 24, 28), // body/tank1
                box(2, 5, -5, 15, 24, 28), // body/tank2
                box(-12, 5, -5, -3, 12, 28), // body/desk
                box(20, 4, -16, 20, 12, -15), // ports/port_front1/port
                box(21, 5, -15, 27, 11, -5), // ports/port_front1/connector
                box(4, 4, -16, 12, 12, -15), // ports/port_front2/port
                box(5, 5, -15, 11, 11, -5), // ports/port_front2/connector
                box(-12, 4, -16, -4, 12, -15), // ports/port_front3/port
                box(-11, 5, -15, -5, 11, -5), // ports/port_front3/connector
                box(31, 4, 4, 32, 12, 12), // ports/port_east1/port
                box(30, 5, 5, 31, 11, 11), // ports/port_east1/connector
                box(31, 4, 19, 32, 12, 27), // ports/port_east2/port
                box(30, 5, 21, 31, 11, 26), // ports/port_east2/connector
                box(-11, 14, -6, -9, 22, 6), // controller/screen1
                box(-6, 14, 5.25, -4, 22, 17.25), // controller/screen2
                box(-6, 14, 17.5, -4, 22, 29.5), // controller/screen3
                box(-12, 12, 4, -6, 13, 18), // controller/keyboard
                box(-3, 7, 3, 2, 11, 7), // controller/connector1
                box(-3, 7, 16, 2, 11, 20), // controller/connector2,
                box(-4, 12, 10, -3, 17, 12), // controller/connector3
                box(-4, 17, 4, -3, 19, 18), // controller/connector4
                box(-6.25, 17, 17, -5.25, 19, 25), // controller/connector5
                box(-7.25, 17, -3, -6.25, 19, 5), // controller/connector6
                box(-5.75, 14.75, -7.5, -5.75, 21.25, 3), // screens/screen1
                box(-5.75, 14.25, 5.5, -5.75, 21.25, 16.5), // screens/screen2
                box(-11.5, 14.75, 18.25, -11.5, 21.25, 28.75), // screens/screen3
                box(19, 24, -1, 28, 25, 24), // tank1_top/top_base/base1
                box(19, 25, -1, 28, 26, 0), // tank1_top/top_base/base2_1
                box(19, 25, 23, 28, 26, 24), // tank1_top/top_base/base2_2
                box(19, 25, 0, 20, 26, 23), // tank1_top/top_base/base3_1
                box(27, 25, 0, 28, 26, 23), // tank1_top/top_base/base3_2
                box(20, 25, 15, 27, 26, 16), // tank1_top/top_base/base4_1
                box(20, 25, 7, 27, 26, 8), // tank1_top/top_base/base4_2
                box(20, 25, 16, 27, 26, 23), // tank1_top/top1
                box(20, 25, 8, 27, 26, 15), // tank1_top/top2
                box(20, 25, 0, 27, 26, 7), // tank1_top/top3
                box(4, 24, -1, 13, 25, 24), // tank2_top/top_base/base1
                box(4, 25, -1, 13, 26, 0), // tank2_top/top_base/base2_1
                box(4, 25, 23, 13, 26, 24), // tank2_top/top_base/base2_2
                box(4, 25, 0, 5, 26, 23), // tank2_top/top_base/base3_1
                box(12, 25, 0, 13, 26, 23), // tank2_top/top_base/base3_2
                box(5, 25, 15, 12, 26, 16), // tank2_top/top_base/base4_1
                box(5, 25, 7, 12, 26, 8), // tank2_top/top_base/base4_2
                box(5, 25, 16, 12, 26, 23), // tank2_top/top1
                box(5, 25, 8, 12, 26, 15), // tank2_top/top2
                box(5, 25, 0, 12, 26, 7), // tank2_top/top3
                box(7, 26, 2, 10, 32, 5), // pipes/pipe1_1
                box(10, 29, 2, 22, 32, 5), // pipes/pipe1_2
                box(22, 26, 2, 25, 32, 5), // pipes/pipe1_3
                box(7, 26, 10, 10, 32, 13), // pipes/pipe2_1
                box(10, 29, 10, 22, 32, 13), // pipes/pipe2_2
                box(22, 26, 10, 25, 32, 13), // pipes/pipe2_3
                box(7, 26, 18, 10, 32, 21), // pipes/pipe3_1,
                box(10, 29, 18, 22, 32, 21), // pipes/pipe3_2,
                box(22, 26, 18, 25, 32, 21) // pipes/pipe3_3
        ), Rotation.CLOCKWISE_90), ACID_LEACHER);
    }

    private MSBlockShapes() {
    }
}
