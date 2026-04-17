package com.fxd927.mekanismscience.common.tile.multiblock.extraction;

import com.fxd927.mekanismscience.common.block.attribute.AttributeStateExtractingPortMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateExtractingPortMode.ExtractingPortMode;
import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.Action;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.lib.multiblock.IMultiblockEjector;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

public class TileEntityExtractingPlantPort
        extends TileEntityExtractingPlantCasing
        implements IMultiblockEjector {

    private Set<Direction> outputDirections = Collections.emptySet();

    public TileEntityExtractingPlantPort(BlockPos pos, BlockState state) {
        super(MSBlocks.EXTRACTING_PLANT_PORT, pos, state);
    }

    @Override
    protected boolean onUpdateServer(ExtractingPlantMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) {
            if (getMode() == ExtractingPortMode.OUTPUT) {
                ChemicalUtil.emit(outputDirections, multiblock.outputTank, this);
            }
        }
        return needsPacket;
    }

    @NotNull
    @Override
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener) {
        return side -> getMultiblock().getGasTanks(side);
    }

    @NotNull
    @Override
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        return side -> getMultiblock().getFluidTanks(side);
    }

    @Override
    public boolean persists(SubstanceType type) {
        if (type == SubstanceType.GAS || type == SubstanceType.FLUID) {
            return false;
        }
        return super.persists(type);
    }

    @Override
    public void setEjectSides(Set<Direction> sides) {
        outputDirections = sides;
    }

    private ExtractingPortMode getMode() {
        return getBlockState().getValue(AttributeStateExtractingPortMode.modeProperty);
    }

    private void setMode(ExtractingPortMode mode) {
        if (mode != getMode()) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(AttributeStateExtractingPortMode.modeProperty, mode));
        }
    }

    @Override
    public InteractionResult onSneakRightClick(Player player) {
        if (!isRemote()) {
            ExtractingPortMode mode = getMode().getNext();
            setMode(mode);
            player.displayClientMessage(MekanismLang.BOILER_VALVE_MODE_CHANGE.translateColored(EnumColor.GRAY, mode), true);
        }
        return InteractionResult.SUCCESS;
    }

    @NotNull
    @Override
    public FluidStack insertFluid(@NotNull FluidStack stack, Direction side, @NotNull Action action) {
        FluidStack ret = super.insertFluid(stack, side, action);
        if (ret.getAmount() < stack.getAmount() && action.execute()) {
            getMultiblock().triggerValveTransfer(this);
        }
        return ret;
    }

    @Override
    public boolean insertGasCheck(int tank, @Nullable Direction side) {
        if (getMode() != ExtractingPortMode.INPUT_EXTRACTANT) {
            return false;
        }
        return super.insertGasCheck(tank, side);
    }

    @Override
    public boolean extractGasCheck(int tank, @Nullable Direction side) {
        ExtractingPortMode mode = getMode();
        if (mode != ExtractingPortMode.OUTPUT) {
            return false;
        }
        return super.extractGasCheck(tank, side);
    }
}
