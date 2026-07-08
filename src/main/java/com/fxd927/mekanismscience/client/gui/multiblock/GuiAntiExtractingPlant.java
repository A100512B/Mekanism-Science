package com.fxd927.mekanismscience.client.gui.multiblock;

import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.function.BooleanSupplier;

public class GuiAntiExtractingPlant extends GuiMekanismTile<TileEntityAntiExtractingPlantCasing, MekanismTileContainer<TileEntityAntiExtractingPlantCasing>> {

    public GuiAntiExtractingPlant(MekanismTileContainer<TileEntityAntiExtractingPlantCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        AntiExtractingPlantMultiblockData multiblock = tile.getMultiblock();
        addRenderableWidget(new GuiFluidGauge(() -> multiblock.extractTank, () -> multiblock.getFluidTanks(null), GaugeType.STANDARD, this, 15, 10))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiGasGauge(() -> multiblock.antiExtractantTank, () -> multiblock.getGasTanks(null), GaugeType.STANDARD, this, 145, 10))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_SECONDARY_INPUT));
        addRenderableWidget(new GuiProgress(() -> multiblock.lastGain != 0, ProgressType.SMALL_RIGHT, this, 36, 35)).jeiCategory(tile)
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiProgress(() -> multiblock.lastGain != 0, ProgressType.SMALL_RIGHT, this, 112, 35)).jeiCategory(tile)
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiFluidGauge(() -> multiblock.concentrateTank, () -> multiblock.getFluidTanks(null), GaugeType.STANDARD, this, 69, 10))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiGasGauge(() -> multiblock.extractantTank, () -> multiblock.getGasTanks(null), GaugeType.STANDARD, this, 91, 10))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
    }

    private BooleanSupplier getWarningCheck(RecipeError error) {
        return () -> tile.getMultiblock().hasWarning(error);
    }
}
