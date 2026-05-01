package com.fxd927.mekanismscience.common.recipe;

import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidChemicalRecipe;
import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidRecipe;
import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.chemical.FluidChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

public class MSRecipeType {

    // Do not initialize, change or mark them as final. They'll be initialized by mixin.
    public static RecipeTypeRegistryObject<GasToGasRecipe, SingleChemical<Gas, GasStack, GasToGasRecipe>> PRESSURIZED_POLYMERIZING;
    public static RecipeTypeRegistryObject<FluidChemicalToFluidRecipe<Gas, GasStack, GasStackIngredient>, FluidChemical<Gas, GasStack, FluidChemicalToFluidRecipe<Gas, GasStack, GasStackIngredient>>> EXTRACTION;
    public static RecipeTypeRegistryObject<FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>, FluidChemical<Gas, GasStack, FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>>> ANTI_EXTRACTION;
    public static RecipeTypeRegistryObject<MetalElectrolysisRecipe, SingleFluid<MetalElectrolysisRecipe>> METAL_ELECTROLYSIS;
}
