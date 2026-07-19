package com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateAntiExtractingPortMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateAntiExtractingPortMode.AntiExtractingPortMode;
import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantMultiblockData;
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
import mekanism.common.util.FluidUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

public class TileEntityAntiExtractingPlantPort
        extends TileEntityAntiExtractingPlantCasing
        implements IMultiblockEjector {

    private Set<Direction> outputDirections = Collections.emptySet();

    public TileEntityAntiExtractingPlantPort(BlockPos pos, BlockState state) {
        super(MSBlocks.ANTI_EXTRACTING_PLANT_PORT, pos, state);
    }

    @Override
    protected boolean onUpdateServer(AntiExtractingPlantMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) {
            switch (getMode()) {
                case OUTPUT_EXTRACTANT -> ChemicalUtil.emit(outputDirections, multiblock.extractantTank, this);
                case OUTPUT_CONCENTRATE -> FluidUtils.emit(outputDirections, multiblock.concentrateTank, this);
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
    public boolean handles(SubstanceType type) {
        if (type == SubstanceType.GAS || type == SubstanceType.FLUID) {
            return true;
        }
        return super.handles(type);
    }

    @Override
    public void setEjectSides(Set<Direction> sides) {
        outputDirections = sides;
    }

    private AntiExtractingPortMode getMode() {
        return getBlockState().getValue(AttributeStateAntiExtractingPortMode.modeProperty);
    }

    private void setMode(AntiExtractingPortMode mode) {
        if (mode != getMode()) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(AttributeStateAntiExtractingPortMode.modeProperty, mode));
        }
    }

    @Override
    public InteractionResult onSneakRightClick(Player player) {
        if (!isRemote()) {
            AntiExtractingPortMode mode = getMode().getNext();
            setMode(mode);
            player.displayClientMessage(MekanismLang.BOILER_VALVE_MODE_CHANGE.translateColored(EnumColor.GRAY, mode), true);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onActivate(Player player, InteractionHand hand, ItemStack stack) {
        if (player.isShiftKeyDown() || !getMultiblock().isFormed()) {
            return super.onActivate(player, hand, stack);
        } else if (isRemote()) {
            return InteractionResult.SUCCESS;
        }
        TileEntityAntiExtractingPlantCasing guiCasing = getGuiCasing();
        if (guiCasing == null) {
            MekanismScience.LOGGER.error("Unable to open Anti-Extracting Plant GUI from port at {}, no non-port casing was found in the formed multiblock.", worldPosition);
            return InteractionResult.FAIL;
        }
        return guiCasing.openGui(player);
    }

    @Nullable
    private TileEntityAntiExtractingPlantCasing getGuiCasing() {
        AntiExtractingPlantMultiblockData multiblock = getMultiblock();
        BlockPos minPos = multiblock.getMinPos();
        BlockPos maxPos = multiblock.getMaxPos();
        for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
            for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!multiblock.locations.contains(pos)) {
                        continue;
                    }
                    TileEntityAntiExtractingPlantCasing tile = WorldUtils.getTileEntity(TileEntityAntiExtractingPlantCasing.class, level, pos);
                    if (tile != null && !(tile instanceof TileEntityAntiExtractingPlantPort)) {
                        return tile;
                    }
                }
            }
        }
        return null;
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
        AntiExtractingPortMode mode = getMode();
        if (mode == AntiExtractingPortMode.OUTPUT_EXTRACTANT || mode == AntiExtractingPortMode.OUTPUT_CONCENTRATE) {
            return false;
        }
        return super.insertGasCheck(tank, side);
    }

    @Override
    public boolean extractGasCheck(int tank, @Nullable Direction side) {
        AntiExtractingPortMode mode = getMode();
        if (mode == AntiExtractingPortMode.INPUT_ANTI_EXTRACTANT || mode == AntiExtractingPortMode.INPUT_EXTRACT) {
            return false;
        }
        return super.extractGasCheck(tank, side);
    }
}
