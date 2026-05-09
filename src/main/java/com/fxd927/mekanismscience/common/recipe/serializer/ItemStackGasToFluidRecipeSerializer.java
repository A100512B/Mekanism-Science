package com.fxd927.mekanismscience.common.recipe.serializer;

import com.fxd927.mekanismscience.api.recipes.ItemStackChemicalToFluidRecipe;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.common.recipe.ingredient.chemical.ChemicalIngredientDeserializer;

public class ItemStackGasToFluidRecipeSerializer<RECIPE extends ItemStackChemicalToFluidRecipe<Gas, GasStack, GasStackIngredient>>
        extends ItemStackChemicalToFluidRecipeSerializer<Gas, GasStack, GasStackIngredient, RECIPE> {

    public ItemStackGasToFluidRecipeSerializer(IFactory<Gas, GasStack, GasStackIngredient, RECIPE> factory) {
        super(factory);
    }

    @Override
    protected ChemicalIngredientDeserializer<Gas, GasStack, GasStackIngredient> getDeserializer() {
        return ChemicalIngredientDeserializer.GAS;
    }
}
