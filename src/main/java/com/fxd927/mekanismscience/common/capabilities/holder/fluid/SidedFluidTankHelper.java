package com.fxd927.mekanismscience.common.capabilities.holder.fluid;

import mekanism.api.RelativeSide;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;

import net.minecraft.core.Direction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SidedFluidTankHelper {

    private final IFluidTankHolder slotHolder;
    private boolean built;

    private SidedFluidTankHelper(IFluidTankHolder slotHolder) {
        this.slotHolder = slotHolder;
    }

    public static SidedFluidTankHelper forSide(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
        return new SidedFluidTankHelper(new SidedFluidTankHolder(facingSupplier, insertPredicate, extractPredicate));
    }

    public <TANK extends IExtendedFluidTank> TANK addTank(@NotNull TANK tank) {
        if (built) {
            throw new IllegalStateException("Builder has already built.");
        }
        if (slotHolder instanceof SidedFluidTankHolder slotHolder) {
            slotHolder.addTank(tank);
        } else {
            throw new IllegalArgumentException("Holder does not know how to add tanks");
        }
        return tank;
    }

    public <TANK extends IExtendedFluidTank> TANK addTank(@NotNull TANK tank, RelativeSide... sides) {
        if (built) {
            throw new IllegalStateException("Builder has already built.");
        }
        if (slotHolder instanceof SidedFluidTankHolder slotHolder) {
            slotHolder.addTank(tank, sides);
        } else {
            throw new IllegalArgumentException("Holder does not know how to add tanks on specific sides");
        }
        return tank;
    }

    public IFluidTankHolder build() {
        built = true;
        return slotHolder;
    }
}
