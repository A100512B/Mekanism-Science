package com.fxd927.mekanismscience.client.jei.machine;

import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEIRecipeType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MetalElectrolysisRecipeCategory extends BaseRecipeCategory<MetalElectrolysisRecipe> {

    private static final ResourceLocation iconRL = MekanismScience.rl("gui/electrolysis.png");
    private final GuiGauge<?> input;
    private final GuiSlot output;

    public MetalElectrolysisRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<MetalElectrolysisRecipe> recipeType) {
        super(helper, recipeType, MSLang.METAL_ELECTROLYSIS_CHAMBER.translate(), createIcon(helper, iconRL), 3, 12, 170, 62);
        input = GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 26, 10);
        addConstantProgress(ProgressType.LARGE_RIGHT, 64, 30);
        output = addSlot(SlotType.OUTPUT, 131, 26);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull MetalElectrolysisRecipe recipe, @NotNull IFocusGroup group) {
        initFluid(builder, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
