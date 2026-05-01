package com.fxd927.mekanismscience.common.recipe.serializer;

import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidChemicalRecipe;
import com.google.gson.JsonObject;
import mekanism.api.SerializerHelper;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.common.recipe.ingredient.chemical.ChemicalIngredientDeserializer;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class FluidGasToFluidGasRecipeSerializer<RECIPE extends FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>>
        extends FluidChemicalToFluidChemicalRecipeSerializer<Gas, GasStack, GasStackIngredient, RECIPE> {

    public FluidGasToFluidGasRecipeSerializer(IFactory<Gas, GasStack, GasStackIngredient, RECIPE> factory) {
        super(factory);
    }

    @Override
    protected ChemicalIngredientDeserializer<Gas, GasStack, GasStackIngredient> getDeserializer() {
        return ChemicalIngredientDeserializer.GAS;
    }

    @Override
    protected GasStack fromJson(@NotNull JsonObject json, @NotNull String key) {
        return SerializerHelper.getGasStack(json, key);
    }

    @Override
    protected GasStack fromBuffer(@NotNull FriendlyByteBuf buf) {
        return GasStack.readFromPacket(buf);
    }
}
