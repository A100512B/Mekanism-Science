package com.fxd927.mekanismscience.common.capabilities.holder.chemical;

import com.fxd927.mekanismscience.common.capabilities.holder.chemical.SidedChemicalTankHolder.SidedGasTankHolder;
import com.fxd927.mekanismscience.common.capabilities.holder.chemical.SidedChemicalTankHolder.SidedInfusionTankHolder;
import com.fxd927.mekanismscience.common.capabilities.holder.chemical.SidedChemicalTankHolder.SidedPigmentTankHolder;
import com.fxd927.mekanismscience.common.capabilities.holder.chemical.SidedChemicalTankHolder.SidedSlurryTankHolder;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.IPigmentTank;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.ISlurryTank;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SidedChemicalTankHelper<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, TANK extends IChemicalTank<CHEMICAL, STACK>> {

    private final IChemicalTankHolder<CHEMICAL, STACK, TANK> slotHolder;
    private boolean built;

    private SidedChemicalTankHelper(IChemicalTankHolder<CHEMICAL, STACK, TANK> slotHolder) {
        this.slotHolder = slotHolder;
    }

    public static SidedChemicalTankHelper<Gas, GasStack, IGasTank> forSideGas(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
        return new SidedChemicalTankHelper<>(new SidedGasTankHolder(facingSupplier, insertPredicate, extractPredicate));
    }

    public static SidedChemicalTankHelper<InfuseType, InfusionStack, IInfusionTank> forSideInfusion(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
        return new SidedChemicalTankHelper<>(new SidedInfusionTankHolder(facingSupplier, insertPredicate, extractPredicate));
    }

    public static SidedChemicalTankHelper<Pigment, PigmentStack, IPigmentTank> forSidePigment(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
        return new SidedChemicalTankHelper<>(new SidedPigmentTankHolder(facingSupplier, insertPredicate, extractPredicate));
    }

    public static SidedChemicalTankHelper<Slurry, SlurryStack, ISlurryTank> forSideSlurry(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
        return new SidedChemicalTankHelper<>(new SidedSlurryTankHolder(facingSupplier, insertPredicate, extractPredicate));
    }

    public TANK addTank(@NotNull TANK tank) {
        if (built) {
            throw new IllegalStateException("Builder has already built.");
        }
        if (slotHolder instanceof SidedChemicalTankHolder<CHEMICAL, STACK, TANK> slotHolder) {
            slotHolder.addTank(tank);
        } else {
            throw new IllegalArgumentException("Holder does not know how to add tanks");
        }
        return tank;
    }

    public TANK addTank(@NotNull TANK tank, RelativeSide... sides) {
        if (built) {
            throw new IllegalStateException("Builder has already built.");
        }
        if (slotHolder instanceof SidedChemicalTankHolder<CHEMICAL, STACK, TANK> slotHolder) {
            slotHolder.addTank(tank, sides);
        } else {
            throw new IllegalArgumentException("Holder does not know how to add tanks on specific sides");
        }
        return tank;
    }

    public IChemicalTankHolder<CHEMICAL, STACK, TANK> build() {
        built = true;
        return slotHolder;
    }
}
