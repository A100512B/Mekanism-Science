package com.fxd927.mekanismscience.common.recipe;

import com.fxd927.mekanismscience.api.recipes.*;
import com.fxd927.mekanismscience.common.recipe.lookup.cache.ItemFluid;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.ItemChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

import static com.fxd927.mekanismscience.common.mixin.MekanismRecipeTypeInvoker.invokeRegister;

public class MSRecipeType {

    public static final RecipeTypeRegistryObject<GasToGasRecipe, SingleChemical<Gas, GasStack, GasToGasRecipe>> PRESSURIZED_POLYMERIZING = invokeRegister("pressurized_polymerizing", recipeType -> new SingleChemical<>(recipeType, GasToGasRecipe::getInput));
    public static final RecipeTypeRegistryObject<FluidGasToFluidRecipe, FluidChemical<Gas, GasStack, FluidGasToFluidRecipe>> EXTRACTION = invokeRegister("extraction", recipeType -> new FluidChemical<>(recipeType, FluidChemicalToFluidRecipe::getFluidInput, FluidChemicalToFluidRecipe::getChemicalInput));
    public static final RecipeTypeRegistryObject<FluidGasToFluidGasRecipe, FluidChemical<Gas, GasStack, FluidGasToFluidGasRecipe>> ANTI_EXTRACTION = invokeRegister("anti_extraction", recipeType -> new FluidChemical<>(recipeType, FluidChemicalToFluidChemicalRecipe::getFluidInput, FluidChemicalToFluidChemicalRecipe::getChemicalInput));
    public static final RecipeTypeRegistryObject<MetalElectrolysisRecipe, SingleFluid<MetalElectrolysisRecipe>> METAL_ELECTROLYSIS = invokeRegister("metal_electrolysis", recipeType -> new SingleFluid<>(recipeType, MetalElectrolysisRecipe::getInput));
    public static final RecipeTypeRegistryObject<ItemStackGasToFluidRecipe, ItemChemical<Gas, GasStack, ItemStackGasToFluidRecipe>> ACID_LEACHING = invokeRegister("acid_leaching", recipeType -> new ItemChemical<>(recipeType, ItemStackChemicalToFluidRecipe::getItemInput, ItemStackChemicalToFluidRecipe::getChemicalInput));
    public static final RecipeTypeRegistryObject<AdsorptionRecipe, ItemFluid<AdsorptionRecipe>> ADSORPTION = invokeRegister("adsorption", recipeType -> new ItemFluid<>(recipeType, AdsorptionRecipe::getItemInput, AdsorptionRecipe::getFluidInput));
    public static final RecipeTypeRegistryObject<IrradiatingRecipe, ItemChemical<Gas, GasStack, IrradiatingRecipe>> IRRADIATING = invokeRegister("irradiating", recipeType -> new ItemChemical<>(recipeType, IrradiatingRecipe::getItemInput, IrradiatingRecipe::getGasInput));
}
