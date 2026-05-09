package com.fxd927.mekanismscience.common.capabilities.holder.chemical;

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
import mekanism.common.capabilities.holder.BasicHolder;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SidedChemicalTankHolder<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, TANK extends IChemicalTank<CHEMICAL, STACK>>
        extends BasicHolder<TANK> implements IChemicalTankHolder<CHEMICAL, STACK, TANK> {

    @Nullable
    private final Predicate<RelativeSide> insertPredicate;
    @Nullable
    private final Predicate<RelativeSide> extractPredicate;

    protected SidedChemicalTankHolder(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
        super(facingSupplier);
        this.insertPredicate = insertPredicate;
        this.extractPredicate = extractPredicate;
    }

    void addTank(@NotNull TANK tank, RelativeSide... sides) {
        addSlotInternal(tank, sides);
    }

    @Override
    @NotNull
    public List<TANK> getTanks(@Nullable Direction side) {
        return getSlots(side);
    }

    @Override
    public boolean canInsert(@Nullable Direction direction) {
        return direction != null && (insertPredicate == null || insertPredicate.test(RelativeSide.fromDirections(facingSupplier.get(), direction)));
    }

    @Override
    public boolean canExtract(@Nullable Direction direction) {
        return direction != null && (extractPredicate == null || extractPredicate.test(RelativeSide.fromDirections(facingSupplier.get(), direction)));
    }

    public static class SidedGasTankHolder extends SidedChemicalTankHolder<Gas, GasStack, IGasTank> {

        public SidedGasTankHolder(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
            super(facingSupplier, insertPredicate, extractPredicate);
        }
    }

    public static class SidedInfusionTankHolder extends SidedChemicalTankHolder<InfuseType, InfusionStack, IInfusionTank> {

        public SidedInfusionTankHolder(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
            super(facingSupplier, insertPredicate, extractPredicate);
        }
    }

    public static class SidedPigmentTankHolder extends SidedChemicalTankHolder<Pigment, PigmentStack, IPigmentTank> {

        public SidedPigmentTankHolder(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
            super(facingSupplier, insertPredicate, extractPredicate);
        }
    }

    public static class SidedSlurryTankHolder extends SidedChemicalTankHolder<Slurry, SlurryStack, ISlurryTank> {

        public SidedSlurryTankHolder(Supplier<Direction> facingSupplier, @Nullable Predicate<RelativeSide> insertPredicate, @Nullable Predicate<RelativeSide> extractPredicate) {
            super(facingSupplier, insertPredicate, extractPredicate);
        }
    }
}
