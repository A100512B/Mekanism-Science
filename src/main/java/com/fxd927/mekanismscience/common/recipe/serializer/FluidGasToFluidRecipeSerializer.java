package com.fxd927.mekanismscience.common.recipe.serializer;

import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidRecipe;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.common.recipe.ingredient.chemical.ChemicalIngredientDeserializer;

public class FluidGasToFluidRecipeSerializer<RECIPE extends FluidChemicalToFluidRecipe<Gas, GasStack, GasStackIngredient>>
        extends FluidChemicalToFluidRecipeSerializer<Gas, GasStack, GasStackIngredient, RECIPE> {

    public FluidGasToFluidRecipeSerializer(IFactory<Gas, GasStack, GasStackIngredient, RECIPE> factory) {
        super(factory);
    }

    @Override
    protected ChemicalIngredientDeserializer<Gas, GasStack, GasStackIngredient> getDeserializer() {
        return ChemicalIngredientDeserializer.GAS;
    }
}
