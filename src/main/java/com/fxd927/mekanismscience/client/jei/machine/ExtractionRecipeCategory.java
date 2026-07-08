package com.fxd927.mekanismscience.client.jei.machine;

import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidRecipe;
import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ExtractionRecipeCategory extends BaseRecipeCategory<FluidGasToFluidRecipe> {

    private static final ResourceLocation iconRL = MekanismScience.rl("gui/extraction.png");
    private final GuiGauge<?> fluidInput;
    private final GuiGauge<?> gasInput;
    private final GuiGauge<?> output;

    public ExtractionRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<FluidGasToFluidRecipe> recipeType) {
        super(helper, recipeType, MSLang.EXTRACTING_PLANT.translate(), createIcon(helper, iconRL), 3, 12, 170, 62);
        fluidInput = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 25, 10));
        gasInput = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 133, 10));
        output = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 79, 10));
        addConstantProgress(ProgressType.SMALL_RIGHT, 47, 35);
        addConstantProgress(ProgressType.SMALL_LEFT, 101, 35);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull FluidGasToFluidRecipe recipe, @NotNull IFocusGroup group) {
        initFluid(builder, RecipeIngredientRole.INPUT, fluidInput, recipe.getFluidInput().getRepresentations());
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, gasInput, recipe.getChemicalInput().getRepresentations());
        initFluid(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
