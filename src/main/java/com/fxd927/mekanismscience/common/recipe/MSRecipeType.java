package com.fxd927.mekanismscience.common.recipe;

import com.fxd927.mekanismscience.api.recipes.*;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.ItemChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

public class MSRecipeType {

    // Do not initialize, change or mark them as final. They'll be initialized by mixin.
    public static RecipeTypeRegistryObject<GasToGasRecipe, SingleChemical<Gas, GasStack, GasToGasRecipe>> PRESSURIZED_POLYMERIZING;
    public static RecipeTypeRegistryObject<FluidGasToFluidRecipe, FluidChemical<Gas, GasStack, FluidGasToFluidRecipe>> EXTRACTION;
    public static RecipeTypeRegistryObject<FluidGasToFluidGasRecipe, FluidChemical<Gas, GasStack, FluidGasToFluidGasRecipe>> ANTI_EXTRACTION;
    public static RecipeTypeRegistryObject<MetalElectrolysisRecipe, SingleFluid<MetalElectrolysisRecipe>> METAL_ELECTROLYSIS;
}
