package com.fxd927.mekanismscience.common.recipe.serializer;

import com.fxd927.mekanismscience.api.MSJsonConstants;
import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidChemicalRecipe;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.ingredient.chemical.ChemicalIngredientDeserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FluidChemicalToFluidChemicalRecipeSerializer<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE extends FluidChemicalToFluidChemicalRecipe<CHEMICAL, STACK,
        INGREDIENT>> implements RecipeSerializer<RECIPE> {

    private IFactory<CHEMICAL, STACK, INGREDIENT, RECIPE> factory;

    public FluidChemicalToFluidChemicalRecipeSerializer(IFactory<CHEMICAL, STACK, INGREDIENT, RECIPE> factory) {
        this.factory = factory;
    }

    protected abstract ChemicalIngredientDeserializer<CHEMICAL, STACK, INGREDIENT> getDeserializer();

    protected abstract STACK fromJson(@NotNull JsonObject json, @NotNull String key);

    protected abstract STACK fromBuffer(@NotNull FriendlyByteBuf buf);

    @Override
    @NotNull
    public RECIPE fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        JsonElement fluidInput = GsonHelper.isArrayNode(json, JsonConstants.FLUID_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.FLUID_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.FLUID_INPUT);
        FluidStackIngredient fluidIngredient = IngredientCreatorAccess.fluid().deserialize(fluidInput);
        JsonElement chemicalInput = GsonHelper.isArrayNode(json, JsonConstants.CHEMICAL_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.CHEMICAL_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.CHEMICAL_INPUT);
        INGREDIENT chemicalIngredient = getDeserializer().deserialize(chemicalInput);
        FluidStack fluidOutput = SerializerHelper.getFluidStack(json, JsonConstants.FLUID_OUTPUT);
        if (fluidOutput.isEmpty())
            throw new JsonSyntaxException("Recipe fluid output must not be empty.");
        STACK chemicalOutput = fromJson(json, MSJsonConstants.CHEMICAL_OUTPUT);
        if (chemicalOutput.isEmpty())
            throw new JsonSyntaxException("Recipe chemical output must not be empty.");
        return factory.create(id, fluidIngredient, chemicalIngredient, fluidOutput, chemicalOutput);
    }

    @Override
    @Nullable
    public RECIPE fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        try {
            FluidStackIngredient fluidInput = IngredientCreatorAccess.fluid().read(buf);
            INGREDIENT chemicalInput = getDeserializer().read(buf);
            FluidStack fluidOutput = FluidStack.readFromPacket(buf);
            STACK chemicalOutput = fromBuffer(buf);
            return factory.create(id, fluidInput, chemicalInput, fluidOutput, chemicalOutput);
        } catch (Exception e) {
            MekanismScience.LOGGER.error("Error reading fluid chemical to fluid chemical recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull RECIPE recipe) {
        try {
            recipe.write(buf);
        } catch (Exception e) {
            MekanismScience.LOGGER.error("Error writing fluid chemical to fluid chemical recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, INGREDIENT extends
            ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE extends FluidChemicalToFluidChemicalRecipe<CHEMICAL, STACK, INGREDIENT>> {

        RECIPE create(ResourceLocation id, FluidStackIngredient fluidInput, INGREDIENT chemicalInput, FluidStack fluidOutput, STACK chemicalOutput);
    }
}
