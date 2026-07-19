package com.fxd927.mekanismscience.client.gui.multiblock;

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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

public class GuiAntiExtractingPlant extends GuiMekanismTile<TileEntityAntiExtractingPlantCasing, MekanismTileContainer<TileEntityAntiExtractingPlantCasing>> {

    public GuiAntiExtractingPlant(MekanismTileContainer<TileEntityAntiExtractingPlantCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        inventoryLabelY += 12;
        imageHeight += 12;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiFluidGauge(() -> tile.getMultiblock().extractTank, () -> tile.getMultiblock().getFluidTanks(null), GaugeType.STANDARD, this, 15, 20))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiGasGauge(() -> tile.getMultiblock().antiExtractantTank, () -> tile.getMultiblock().getGasTanks(null), GaugeType.STANDARD, this, 145, 20))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_SECONDARY_INPUT));
        addRenderableWidget(new GuiProgress(() -> tile.getMultiblock().lastGain != 0, ProgressType.SMALL_RIGHT, this, 36, 46)).jeiCategory(tile)
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiProgress(() -> tile.getMultiblock().lastGain != 0, ProgressType.SMALL_LEFT, this, 112, 46)).jeiCategory(tile)
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiFluidGauge(() -> tile.getMultiblock().concentrateTank, () -> tile.getMultiblock().getFluidTanks(null), GaugeType.STANDARD, this, 69, 20))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiGasGauge(() -> tile.getMultiblock().extractantTank, () -> tile.getMultiblock().getGasTanks(null), GaugeType.STANDARD, this, 91, 20))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
    }

    private BooleanSupplier getWarningCheck(RecipeError error) {
        return () -> tile.getMultiblock().hasWarning(error);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
