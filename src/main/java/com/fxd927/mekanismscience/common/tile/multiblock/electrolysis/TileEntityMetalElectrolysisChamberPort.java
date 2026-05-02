package com.fxd927.mekanismscience.common.tile.multiblock.electrolysis;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.IContentsListener;
import mekanism.api.text.EnumColor;
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
import net.minecraft.core.Direction;
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

    public TileEntityMetalElectrolysisChamberPort(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT, pos, state);
    }

    @Override
    protected boolean onUpdateServer(MetalElectrolysisChamberMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) {
            if (getActive()) {
                // Outputting items
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
                // Maybe inputting energy
                if (getInputRate().greaterOrEqual(MSConfig.usageConfig.metalElectrolyzingRod.get()))
                    multiblock.rodsList.get(index).active = true;
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
        return side -> getMultiblock().getEnergyContainers(side);
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
            player.displayClientMessage(MSLang.METAL_ELECTROLYSIS_CHAMBER_PORT_MODE.translateColored(EnumColor.GRAY, InputOutput.of(oldMode, true)), true);
        }
        return InteractionResult.SUCCESS;
    }
}
