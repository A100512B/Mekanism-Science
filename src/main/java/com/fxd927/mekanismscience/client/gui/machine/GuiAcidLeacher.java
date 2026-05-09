package com.fxd927.mekanismscience.client.gui.machine;

import com.fxd927.mekanismscience.common.tile.machine.TileEntityAcidLeacher;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiAcidLeacher extends GuiMekanismTile<TileEntityAcidLeacher, MekanismTileContainer<TileEntityAcidLeacher>> {

    public GuiAcidLeacher(MekanismTileContainer<TileEntityAcidLeacher> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiHorizontalPowerBar(this, tile.getEnergyContainer(), 115, 75))
                .warning(WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY));
        addRenderableWidget(new GuiGasGauge(() -> tile.gasInputTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 5, 13))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_SECONDARY_INPUT));
        // Item input slot is done by server side
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.LARGE_RIGHT, this, 64, 30))
                .jeiCategory(tile)
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiFluidGauge(() -> tile.outputTank, () -> tile.getFluidTanks(null), GaugeType.STANDARD, this, 131, 13))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        // Gas input, fluid output and energy slots are done by server side
    }
}
