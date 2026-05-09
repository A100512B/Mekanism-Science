package com.fxd927.mekanismscience.common.mixin;

import com.fxd927.mekanismscience.api.ITileEntityMekanismAccessor;
import mekanism.common.capabilities.resolver.manager.ChemicalHandlerManager.GasHandlerManager;
import mekanism.common.capabilities.resolver.manager.ChemicalHandlerManager.InfusionHandlerManager;
import mekanism.common.capabilities.resolver.manager.ChemicalHandlerManager.PigmentHandlerManager;
import mekanism.common.capabilities.resolver.manager.ChemicalHandlerManager.SlurryHandlerManager;
import mekanism.common.capabilities.resolver.manager.EnergyHandlerManager;
import mekanism.common.capabilities.resolver.manager.FluidHandlerManager;
import mekanism.common.capabilities.resolver.manager.HeatHandlerManager;
import mekanism.common.tile.base.TileEntityMekanism;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TileEntityMekanism.class, remap = false)
public abstract class MixinTileEntityMekanism implements ITileEntityMekanismAccessor {

    @Accessor
    public abstract GasHandlerManager getGasHandlerManager();

    @Accessor
    public abstract InfusionHandlerManager getInfusionHandlerManager();

    @Accessor
    public abstract PigmentHandlerManager getPigmentHandlerManager();

    @Accessor
    public abstract SlurryHandlerManager getSlurryHandlerManager();

    @Accessor
    public abstract FluidHandlerManager getFluidHandlerManager();

    @Accessor
    public abstract EnergyHandlerManager getEnergyHandlerManager();

    @Accessor
    public abstract HeatHandlerManager getHeatHandlerManager();
}
