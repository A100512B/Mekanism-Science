package com.fxd927.mekanismscience.client.jei.machine;

import com.fxd927.mekanismscience.api.recipes.ItemStackGasToFluidRecipe;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import org.jetbrains.annotations.NotNull;

public class AcidLeachingRecipeCategory extends BaseRecipeCategory<ItemStackGasToFluidRecipe> {

    private final GuiGauge<?> gasInput;
    private final GuiSlot itemInput;
    private final GuiGauge<?> output;

    public AcidLeachingRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<ItemStackGasToFluidRecipe> recipeType) {
        super(helper, recipeType, MSBlocks.ACID_LEACHER, 3, 10, 170, 75);
        gasInput = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 28, 13));
        addSlot(SlotType.INPUT, 7, 55).with(SlotOverlay.MINUS);
        itemInput = addSlot(SlotType.INPUT, 7, 36);
        addConstantProgress(ProgressType.LARGE_RIGHT, 64, 40);
        output = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
        addSlot(SlotType.INPUT, 152, 55).with(SlotOverlay.PLUS);
        addElement(new GuiHorizontalPowerBar(this, FULL_BAR, 115, 75));
        addSlot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ItemStackGasToFluidRecipe recipe, @NotNull IFocusGroup group) {
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, gasInput, recipe.getChemicalInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.INPUT, itemInput, recipe.getItemInput().getRepresentations());
        initFluid(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }
}
