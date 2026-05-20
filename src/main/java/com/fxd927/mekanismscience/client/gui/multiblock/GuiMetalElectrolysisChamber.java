package com.fxd927.mekanismscience.client.gui.multiblock;

import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData.RodData;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberLaserAcceptor;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberPort;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.bar.GuiBar.IBarInfoHandler;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.util.WorldUtils;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.function.BooleanSupplier;

public class GuiMetalElectrolysisChamber extends GuiMekanismTile<TileEntityMetalElectrolysisChamberCasing, MekanismTileContainer<TileEntityMetalElectrolysisChamberCasing>> {

    final List<MachineEnergyContainer<? extends TileEntityMetalElectrolysisChamberCasing>> containers
            = tile.getMultiblock().rodsList.stream().map(data -> getEnergyContainer(tile.getMultiblock(), data))
            .flatMap(List::stream)
            .toList();
    final FloatingLong cap = containers.stream().map(MachineEnergyContainer::getMaxEnergy)
            .reduce(FloatingLong::add)
            .get();

    public GuiMetalElectrolysisChamber(MekanismTileContainer<TileEntityMetalElectrolysisChamberCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        MetalElectrolysisChamberMultiblockData multiblock = tile.getMultiblock();
        FloatingLong totalEnergy = containers.stream().map(MachineEnergyContainer::getEnergy)
                .reduce(FloatingLong::add).get();

        addRenderableWidget(new GuiEnergyTab(this, () -> List.of(MekanismLang.USING.translate(EnergyDisplay.of(multiblock.lastEnergyUsed)),
                MekanismLang.NEEDED.translate(EnergyDisplay.of(containers.stream().map(IEnergyContainer::getNeeded).reduce(FloatingLong::add).get())))));
        addRenderableWidget(new GuiHorizontalPowerBar(this, new IBarInfoHandler() {
            @Override
            public double getLevel() {
                return totalEnergy.divideToLevel(cap);
            }

            @Override
            public Component getTooltip() {
                return EnergyDisplay.of(totalEnergy, cap).getTextComponent();
            }
        }, 115, 75))
                .warning(WarningType.NOT_ENOUGH_ENERGY, getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY))
                .warning(WarningType.NOT_ENOUGH_ENERGY_REDUCED_RATE, getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE));
        addRenderableWidget(new GuiFluidGauge(() -> multiblock.inputTank, () -> multiblock.getFluidTanks(null), GaugeType.STANDARD, this, 26, 10))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiProgress(() -> multiblock.lastEnergyUsed.greaterThan(FloatingLong.ZERO), ProgressType.LARGE_RIGHT, this, 64, 30))
                .jeiCategory(tile)
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        // Inventory slots have been done by server side
    }

    private BooleanSupplier getWarningCheck(RecipeError error) {
        return () -> tile.getMultiblock().hasWarning(error);
    }

    private static List<MachineEnergyContainer<? extends TileEntityMetalElectrolysisChamberCasing>> getEnergyContainer(MetalElectrolysisChamberMultiblockData multiblock, RodData data) {
        if (data.laser) {
            return List.of(
                    WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberLaserAcceptor.class, multiblock.getHandlerWorld(), data.minPos).getEnergyContainer(),
                    WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberLaserAcceptor.class, multiblock.getHandlerWorld(), data.maxPos).getEnergyContainer()
            );
        } else {
            return List.of(
                    WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberPort.class, multiblock.getHandlerWorld(), data.minPos).getEnergyContainer(),
                    WorldUtils.getTileEntity(TileEntityMetalElectrolysisChamberPort.class, multiblock.getHandlerWorld(), data.maxPos).getEnergyContainer()
            );
        }
    }
}
