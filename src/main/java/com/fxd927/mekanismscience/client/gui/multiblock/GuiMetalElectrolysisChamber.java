package com.fxd927.mekanismscience.client.gui.multiblock;

import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberCasing;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.function.BooleanSupplier;

public class GuiMetalElectrolysisChamber extends GuiMekanismTile<TileEntityMetalElectrolysisChamberCasing, MekanismTileContainer<TileEntityMetalElectrolysisChamberCasing>> {

    public GuiMetalElectrolysisChamber(MekanismTileContainer<TileEntityMetalElectrolysisChamberCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        MetalElectrolysisChamberMultiblockData multiblock = tile.getMultiblock();
        addRenderableWidget(new GuiEnergyTab(this, () -> List.of(MekanismLang.USING.translate(EnergyDisplay.of(multiblock.lastEnergyUsed)),
                MekanismLang.NEEDED.translate(EnergyDisplay.of(multiblock.energyContainer.getNeeded())))));
        addRenderableWidget(new GuiHorizontalPowerBar(this, multiblock.energyContainer, 115, 75))
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
}
