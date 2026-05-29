package com.fxd927.mekanismscience.common.tile.multiblock.electrolysis;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateElectrolyzingRodMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateElectrolyzingRodMode.MetalElectrolyzingRodMode;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData.RodData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import lombok.Getter;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.lib.inventory.TransitRequest;
import mekanism.common.lib.multiblock.IMultiblockEjector;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.tile.transmitter.TileEntityLogisticalTransporterBase;
import mekanism.common.util.InventoryUtils;
import mekanism.common.util.WorldUtils;
import mekanism.common.util.text.BooleanStateDisplay.InputOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

public class TileEntityMetalElectrolysisChamberPort
        extends TileEntityMetalElectrolysisChamberCasing
        implements IMultiblockEjector {

    public int index = 0x67676767; // Should be set in validator. If not, then SIX SEVEN!!!

    private Set<Direction> outputDirections = Collections.emptySet();
    @Getter
    private final MachineEnergyContainer<TileEntityMetalElectrolysisChamberPort> energyContainer = MachineEnergyContainer.input(this, null);
    private FloatingLong lastEnergyUsed;

    public TileEntityMetalElectrolysisChamberPort(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT, pos, state);
    }

    @Override
    protected boolean onUpdateServer(MetalElectrolysisChamberMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) {
            if (getActive()) {
                for (Direction direction : outputDirections) {
                    BlockEntity target = WorldUtils.getTileEntity(level, getBlockPos().relative(direction));
                    TransitRequest ejectMap = InventoryUtils.getEjectItemMap(this, direction, getInventorySlots(direction));
                    if (!ejectMap.isEmpty()) {
                        TransitRequest.TransitResponse response;
                        if (target instanceof TileEntityLogisticalTransporterBase transporter) {
                            response = transporter.getTransmitter().insert(this, ejectMap, transporter.getTransmitter().getColor(), true, 0);
                        } else {
                            response = ejectMap.addToInventory(target, direction.getOpposite(), 0, false);
                        }
                        if (!response.isEmpty()) {
                            response.useAll();
                        }
                    }
                }
            } else {
                FloatingLong baseEnergyPerTick = MSConfig.usageConfig.metalElectrolyzingRod.get();
                int maxOperations = energyContainer.getMaxEnergy().divideToInt(baseEnergyPerTick);
                FloatingLong currentEnergyUsed = energyContainer.extract(baseEnergyPerTick.multiply(maxOperations), Action.EXECUTE, AutomationType.INTERNAL);
                boolean needsToUpdateRods = currentEnergyUsed.isZero() ^ !lastEnergyUsed.isZero();
                boolean newRodActive = needsToUpdateRods && !currentEnergyUsed.isZero();
                lastEnergyUsed = currentEnergyUsed;

                if (needsToUpdateRods) {
                    RodData data = multiblock.rodsList.get(index);
                    data.active = newRodActive;
                    Axis axis = data.maxPos.subtract(data.minPos).getX() == 0 ? Axis.Z : Axis.X;
                    for (MutableBlockPos pos = data.minPos.relative(axis, 1).mutable();
                         pos.equals(data.maxPos);
                         pos.move(Direction.get(AxisDirection.POSITIVE, axis))) {

                        TileEntityMetalElectrolyzingRod tile = WorldUtils.getTileEntity(TileEntityMetalElectrolyzingRod.class, getLevel(), pos);
                        if (tile == null) {
                            MekanismScience.LOGGER.error("Failed to set rod state at {} as the type mismatched. Should not be possible.", pos);
                            throw new RuntimeException();
                        }
                        // noinspection deprecation
                        tile.setBlockState(tile.getBlockState().setValue(AttributeStateElectrolyzingRodMode.modeProperty,
                                newRodActive ? MetalElectrolyzingRodMode.ACTIVE : MetalElectrolyzingRodMode.IDLE));
                    }
                }
            }
        }
        return needsPacket;
    }

    @NotNull
    @Override
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        return side -> getMultiblock().getFluidTanks(side);
    }

    @Override
    @Nullable
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        return side -> Collections.singletonList(energyContainer);
    }

    @Override
    public boolean persists(SubstanceType type) {
        if (type == SubstanceType.FLUID || type == SubstanceType.ENERGY)
            return false;
        return super.persists(type);
    }

    @Override
    public void setEjectSides(Set<Direction> sides) {
        outputDirections = sides;
    }

    @Override
    public InteractionResult onSneakRightClick(Player player) {
        if (!isRemote()) {
            boolean oldMode = getActive();
            setActive(!oldMode);
            player.displayClientMessage(MekanismLang.BOILER_VALVE_MODE_CHANGE.translateColored(EnumColor.GRAY, InputOutput.of(oldMode, true)), true);
        }
        return InteractionResult.SUCCESS;
    }
}
