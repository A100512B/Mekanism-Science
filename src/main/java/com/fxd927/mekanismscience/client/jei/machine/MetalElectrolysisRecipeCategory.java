package com.fxd927.mekanismscience.client.jei.machine;

import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import org.jetbrains.annotations.NotNull;

public class MetalElectrolysisRecipeCategory extends BaseRecipeCategory<MetalElectrolysisRecipe> {

    private final GuiGauge<?> input;
    private final GuiSlot output;

    public MetalElectrolysisRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<MetalElectrolysisRecipe> recipeType) {
        super(helper, recipeType, MSBlocks.METAL_ELECTROLYSIS_CHAMBER, 3, 12, 170, 62);
        input = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 42, 13));
        addSlot(SlotType.INPUT, 21, 56).with(SlotOverlay.MINUS);
        addConstantProgress(ProgressType.LARGE_RIGHT, 64, 40);
        output = addSlot(SlotType.OUTPUT, 116, 36);
        addElement(new GuiVerticalPowerBar(this, FULL_BAR, 164, 15));
        addSlot(SlotType.POWER, 144, 35).with(SlotOverlay.POWER);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull MetalElectrolysisRecipe recipe, @NotNull IFocusGroup group) {
        initFluid(builder, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
