package com.fxd927.mekanismscience.common.recipe;

import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.chemical.FluidChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

public class MSRecipeType {

    // Do not initialize, change or mark them as final. They'll be initialized by mixin.
    public static RecipeTypeRegistryObject<GasToGasRecipe, SingleChemical<Gas, GasStack, GasToGasRecipe>> PRESSURIZED_POLYMERIZING;
    public static RecipeTypeRegistryObject<FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>, FluidChemical<Gas, GasStack, FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>>> EXTRACTING;
}
