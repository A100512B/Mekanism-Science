package com.fxd927.mekanismscience.common.content.blocktype;

import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.level.block.Block.box;

public final class MSBlockShapes {

    public static final VoxelShape[] ACID_LEACHER = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];
    public static final VoxelShape[] ADSORPTION_SEPARATOR = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];
    public static final VoxelShape[] METAL_ELECTROLYSIS_CHAMBER = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];

    static {
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(-15, 0, -15, 31, 5, 31),
                box(-12, 5, 17, 21, 24, 30),
                box(-12, 5, 2, 21, 24, 15),
                box(-12, 5, -12, 21, 12, -3),
                box(31, 4, 20, 32, 12, 28),
                box(21, 5, 21, 31, 11, 27),
                box(31, 4, 4, 32, 12, 12),
                box(21, 5, 5, 31, 11, 11),
                box(31, 4, -12, 32, 12, -4),
                box(21, 5, -11, 31, 11, -5),
                box(4, 4, 31, 12, 12, 32),
                box(5, 5, 30, 11, 11, 31),
                box(-11, 4, 31, -3, 12, 32),
                box(-10, 5, 30, -5, 11, 31),
                box(-2, 12, -12, 12, 13, -6),
                box(9, 7, -3, 13, 11, 2),
                box(-4, 7, -3, 0, 11, 2),
                box(4, 12, -4, 6, 17, -3),
                box(-2, 17, -4, 12, 19, -3),
                box(-0.5, 14.25, -5.75, 10.5, 21.25, -5.75),
                box(-15.617316567634909, 14.750000000000021, -1.2895321333313632, -5.117316567634909, 21.25000000000002, -1.2895321333313632),
                box(-8, 24, 19, 17, 25, 28),
                box(16, 25, 19, 17, 26, 28),
                box(-8, 25, 19, -7, 26, 28),
                box(-7, 25, 19, 16, 26, 20),
                box(-7, 25, 27, 16, 26, 28),
                box(0, 25, 20, 1, 26, 27),
                box(8, 25, 20, 9, 26, 27),
                box(-7, 25, 20, 0, 26, 27),
                box(1, 25, 20, 8, 26, 27),
                box(9, 25, 20, 16, 26, 27),
                box(-8, 24, 4, 17, 25, 13),
                box(16, 25, 4, 17, 26, 13),
                box(-8, 25, 4, -7, 26, 13),
                box(-7, 25, 4, 16, 26, 5),
                box(-7, 25, 12, 16, 26, 13),
                box(0, 25, 5, 1, 26, 12),
                box(8, 25, 5, 9, 26, 12),
                box(-7, 25, 5, 0, 26, 12),
                box(1, 25, 5, 8, 26, 12),
                box(9, 25, 5, 16, 26, 12),
                box(11, 26, 7, 14, 32, 10),
                box(11, 29, 10, 14, 32, 22),
                box(11, 26, 22, 14, 32, 25),
                box(3, 26, 7, 6, 32, 10),
                box(3, 29, 10, 6, 32, 22),
                box(3, 26, 22, 6, 32, 25),
                box(-5, 26, 7, -2, 32, 10),
                box(-5, 29, 10, -2, 32, 22),
                box(-5, 26, 22, -2, 32, 25)
        ), ACID_LEACHER);

        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(0, 0, 0, 16, 4, 16), // base
                box(10, 4, 4, 14, 16, 12), // tank1
                box(2, 4, 4, 6, 16, 12), // tank2
                box(7, 4, 2, 9, 12, 12), // adsorbent
                box(9, 7, 7, 10, 9, 9), // connector1
                box(6, 7, 7, 7, 9, 9), // connector2
                box(0, 4, 12, 16, 16, 16), // behind
                box(14, 4, 2, 15, 14, 12), // fence1
                box(10, 4, 2, 14, 14, 4), // fence2
                box(1, 4, 2, 2, 14, 12), // fence3
                box(2, 4, 2, 6, 14, 4), // fence4
                box(15, 4, 4, 16, 12, 12), // port1
                box(0, 4, 4, 1, 12, 12), // port2
                box(16, 5, 5, 16, 11, 11), // portLED1
                box(0, 5, 5, 0, 11, 11) // portLED2
        ), ADSORPTION_SEPARATOR);

        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(-15, -16, -15, 31, -12, 31),
                box(-9, -10, -9, 25, 26, 25),
                box(4, 26, 12, 12, 27, 20),
                box(4, 26, 4, 12, 27, 12),
                box(4, 26, -4, 12, 27, 4),
                box(12, 26, 4, 20, 27, 12),
                box(-4, 26, 4, 4, 27, 12),
                box(-12, -12, 24, -8, 28, 28),
                box(-12, -12, -12, -8, 28, -8),
                box(-11, -11, -8, -9, 27, 24),
                box(-16, 4, 4, -15, 12, 12),
                box(-15, 5, 5, -11, 11, 11),
                box(24, -12, 24, 28, 28, 28),
                box(24, -12, -12, 28, 28, -8),
                box(25, -11, -8, 27, 27, 24),
                box(31, 4, 4, 32, 12, 12),
                box(27, 5, 5, 31, 11, 11),
                box(4, 4, 31, 12, 12, 32),
                box(5, 5, 25, 11, 11, 31),
                box(-6, 9, -12, 6, 17, -10),
                box(-5, 9.75, -12, 5, 16, -12),
                box(10, 9, -12, 22, 17, -10),
                box(11, 9.75, -12, 21, 16, -12)
        ).move(0, 1, 0), METAL_ELECTROLYSIS_CHAMBER);
    }

    private MSBlockShapes() {
    }
}
