package com.fxd927.mekanismscience.common.recipe.serializer;

import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MetalElectrolysisRecipeSerializer<RECIPE extends MetalElectrolysisRecipe>
        implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public MetalElectrolysisRecipeSerializer(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @Override
    @NotNull
    public RECIPE fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        JsonElement input = GsonHelper.isArrayNode(json, JsonConstants.INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.INPUT);
        FluidStackIngredient inputIngredient = IngredientCreatorAccess.fluid().deserialize(input);
        ItemStack outputStack = SerializerHelper.getItemStack(json, JsonConstants.OUTPUT);
        if (outputStack.isEmpty())
            throw new JsonSyntaxException("Recipe outputs must not be empty.");
        FloatingLong energyRequired = SerializerHelper.getFloatingLong(json, JsonConstants.ENERGY_REQUIRED);
        return factory.create(id, inputIngredient, outputStack, energyRequired);
    }

    @Override
    @Nullable
    public RECIPE fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        try {
            FloatingLong energyRequired = FloatingLong.readFromBuffer(buf);
            FluidStackIngredient inputIngredient = IngredientCreatorAccess.fluid().read(buf);
            ItemStack outputStack = buf.readItem();
            return factory.create(id, inputIngredient, outputStack, energyRequired);
        } catch (Exception e) {
            MekanismScience.LOGGER.error("Error reading metal electrolysis recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull RECIPE recipe) {
        try {
            recipe.write(buf);
        } catch (Exception e) {
            MekanismScience.LOGGER.error("Error writing metal electrolysis recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends MetalElectrolysisRecipe> {

        RECIPE create(ResourceLocation id, FluidStackIngredient input, ItemStack output, FloatingLong energyRequired);
    }
}
