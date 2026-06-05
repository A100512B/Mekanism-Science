package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import mekanism.api.chemical.gas.GasStack;
import mekanism.api.datagen.recipe.builder.GasToGasRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import net.minecraft.resources.ResourceLocation;

import static com.fxd927.mekanismscience.api.datagen.recipe.MSRecipeBuilder.msSerializer;

public class MSGasToGasRecipeBuilder extends GasToGasRecipeBuilder {

    protected MSGasToGasRecipeBuilder(GasStackIngredient input, GasStack output, ResourceLocation serializerName) {
        super(input, output, serializerName);
    }

    public static MSGasToGasRecipeBuilder polymerizing(GasStackIngredient input, GasStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This polymerizing recipe requires a non empty gas output.");
        }
        return new MSGasToGasRecipeBuilder(input, output, msSerializer("polymerizing"));
    }
}
