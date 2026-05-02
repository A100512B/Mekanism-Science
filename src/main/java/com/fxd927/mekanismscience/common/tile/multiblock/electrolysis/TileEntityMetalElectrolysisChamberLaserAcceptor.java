package com.fxd927.mekanismscience.common.tile.multiblock.electrolysis;

import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.lasers.ILaserReceptor;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.resolver.BasicCapabilityResolver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityMetalElectrolysisChamberLaserAcceptor
        extends TileEntityMetalElectrolysisChamberCasing
        implements ILaserReceptor {

    public int index = 0x67676767; // Should be set in validator. If not, then SIX SEVEN!!!

    public TileEntityMetalElectrolysisChamberLaserAcceptor(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR, pos, state);
        addCapabilityResolver(BasicCapabilityResolver.constant(Capabilities.LASER_RECEPTOR, this));
    }

    @Override
    public void receiveLaserEnergy(@NotNull FloatingLong energy) {
        MetalElectrolysisChamberMultiblockData multiblock = getMultiblock();
        multiblock.energyContainer.insert(energy, Action.EXECUTE, AutomationType.INTERNAL);
    }

    @Override
    protected boolean onUpdateServer(MetalElectrolysisChamberMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (getInputRate().greaterOrEqual(MSConfig.usageConfig.metalElectrolyzingLaserRod.get()))
            multiblock.rodsList.get(index).active = true;
        return needsPacket;
    }

    @Override
    public boolean canLasersDig() {
        return false;
    }
}
