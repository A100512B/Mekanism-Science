package com.fxd927.mekanismscience.client.gui.multiblock;

import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
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

public class GuiExtractingPlant extends GuiMekanismTile<TileEntityExtractingPlantCasing, MekanismTileContainer<TileEntityExtractingPlantCasing>> {

    public GuiExtractingPlant(MekanismTileContainer<TileEntityExtractingPlantCasing> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        inventoryLabelY += 12;
        imageHeight += 12;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        ExtractingPlantMultiblockData multiblock = tile.getMultiblock();
        addRenderableWidget(new GuiFluidGauge(() -> multiblock.leachateTank, () -> multiblock.getFluidTanks(null), GaugeType.STANDARD, this, 25, 20))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiGasGauge(() -> multiblock.extractantTank, () -> multiblock.getGasTanks(null), GaugeType.STANDARD, this, 133, 20))
                .warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_SECONDARY_INPUT));
        addRenderableWidget(new GuiFluidGauge(() -> multiblock.outputTank, () -> multiblock.getFluidTanks(null), GaugeType.STANDARD, this, 79, 20))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.SMALL_RIGHT, this, 47, 46).jeiCategory(tile))
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.SMALL_LEFT, this, 101, 46).jeiCategory(tile))
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
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
