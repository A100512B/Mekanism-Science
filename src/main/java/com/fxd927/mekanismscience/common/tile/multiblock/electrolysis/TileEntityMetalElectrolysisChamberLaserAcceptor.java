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
import mekanism.api.lasers.ILaserReceptor;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.resolver.BasicCapabilityResolver;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityMetalElectrolysisChamberLaserAcceptor
        extends TileEntityMetalElectrolysisChamberCasing
        implements ILaserReceptor {

    public int index = 0x67676767; // Should be set in validator. If not, then SIX SEVEN!!!

    @Getter
    private final MachineEnergyContainer<TileEntityMetalElectrolysisChamberLaserAcceptor> energyContainer = MachineEnergyContainer.input(this, null);
    private FloatingLong lastEnergyUsed;

    public TileEntityMetalElectrolysisChamberLaserAcceptor(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR, pos, state);
        addCapabilityResolver(BasicCapabilityResolver.constant(Capabilities.LASER_RECEPTOR, this));
    }

    @Override
    public void receiveLaserEnergy(@NotNull FloatingLong energy) {
        energyContainer.insert(energy, Action.EXECUTE, AutomationType.EXTERNAL);
    }

    @Override
    protected boolean onUpdateServer(MetalElectrolysisChamberMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);

        FloatingLong baseEnergyPerTick = MSConfig.usageConfig.metalElectrolyzingRod.get();
        int maxOperations = energyContainer.getMaxEnergy().divideToInt(baseEnergyPerTick);
        FloatingLong currentEnergyUsed = energyContainer.extract(baseEnergyPerTick.multiply(maxOperations), Action.EXECUTE, AutomationType.INTERNAL);
        boolean needsToUpdateRods = currentEnergyUsed.isZero() ^ !lastEnergyUsed.isZero();
        boolean newRodActive = needsToUpdateRods && !currentEnergyUsed.isZero();
        lastEnergyUsed = currentEnergyUsed;

        if (needsToUpdateRods) {
            RodData data = multiblock.rodsList.get(index);
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
                        newRodActive ? MetalElectrolyzingRodMode.ACTIVE_LASER : MetalElectrolyzingRodMode.IDLE));
            }
        }

        return needsPacket;
    }

    @Override
    public boolean canLasersDig() {
        return false;
    }
}
