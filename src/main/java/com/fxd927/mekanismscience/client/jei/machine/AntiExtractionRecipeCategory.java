package com.fxd927.mekanismscience.client.jei.machine;

import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidGasRecipe;
import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AntiExtractionRecipeCategory extends BaseRecipeCategory<FluidGasToFluidGasRecipe> {

    private static final ResourceLocation iconRL = MekanismScience.rl("gui/anti_extraction.png");
    private final GuiFluidGauge fluidInput;
    private final GuiGasGauge gasInput;
    private final GuiFluidGauge fluidOutput;
    private final GuiGasGauge gasOutput;

    public AntiExtractionRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<FluidGasToFluidGasRecipe> recipeType) {
        super(helper, recipeType, MSLang.ANTI_EXTRACTING_PLANT.translate(), createIcon(helper, iconRL), 3, 10, 170, 62);
        fluidInput = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 15, 10));
        gasInput = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 145, 10));
        addConstantProgress(ProgressType.SMALL_RIGHT, 36, 35);
        addConstantProgress(ProgressType.SMALL_LEFT, 112, 35);
        fluidOutput = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 69, 10));
        gasOutput = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 91, 10));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull FluidGasToFluidGasRecipe recipe, @NotNull IFocusGroup group) {
        initFluid(builder, RecipeIngredientRole.INPUT, fluidInput, recipe.getFluidInput().getRepresentations());
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, gasInput, recipe.getChemicalInput().getRepresentations());
        List<FluidStack> fluidOutputs = new ArrayList<>();
        List<GasStack> gasOutputs = new ArrayList<>();
        recipe.getOutputDefinition().forEach(output -> {
            fluidOutputs.add(output.fluidOutput());
            gasOutputs.add(output.chemicalOutput());
        });
        initFluid(builder, RecipeIngredientRole.OUTPUT, fluidOutput, fluidOutputs);
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.OUTPUT, gasOutput, gasOutputs);
    }
}
