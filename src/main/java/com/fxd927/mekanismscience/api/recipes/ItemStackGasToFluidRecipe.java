package com.fxd927.mekanismscience.api.recipes;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public abstract class ItemStackGasToFluidRecipe extends ItemStackChemicalToFluidRecipe<Gas, GasStack, GasStackIngredient> {

    public ItemStackGasToFluidRecipe(ResourceLocation id, ItemStackIngredient itemInput, GasStackIngredient gasInput, FluidStack output) {
        super(id, itemInput, gasInput, output);
    }
}
