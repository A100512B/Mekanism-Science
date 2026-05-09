package com.fxd927.mekanismscience.common.recipe.serializer;

import com.fxd927.mekanismscience.api.recipes.ItemStackChemicalToFluidRecipe;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.ingredient.chemical.ChemicalIngredientDeserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ItemStackChemicalToFluidRecipeSerializer<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE extends ItemStackChemicalToFluidRecipe<CHEMICAL,
        STACK, INGREDIENT>> implements RecipeSerializer<RECIPE> {

    private IFactory<CHEMICAL, STACK, INGREDIENT, RECIPE> factory;

    public ItemStackChemicalToFluidRecipeSerializer(IFactory<CHEMICAL, STACK, INGREDIENT, RECIPE> factory) {
        this.factory = factory;
    }

    protected abstract ChemicalIngredientDeserializer<CHEMICAL, STACK, INGREDIENT> getDeserializer();

    @Override
    @NotNull
    public RECIPE fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        JsonElement itemInput = GsonHelper.isArrayNode(json, JsonConstants.ITEM_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.ITEM_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.ITEM_INPUT);
        ItemStackIngredient fluidIngredient = IngredientCreatorAccess.item().deserialize(itemInput);
        JsonElement chemicalInput = GsonHelper.isArrayNode(json, JsonConstants.CHEMICAL_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.CHEMICAL_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.CHEMICAL_INPUT);
        INGREDIENT chemicalIngredient = getDeserializer().deserialize(chemicalInput);
        FluidStack output = SerializerHelper.getFluidStack(json, JsonConstants.FLUID_OUTPUT);
        if (output.isEmpty())
            throw new JsonSyntaxException("Recipe fluid output must not be empty.");
        return factory.create(id, fluidIngredient, chemicalIngredient, output);
    }

    @Override
    @Nullable
    public RECIPE fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        try {
            ItemStackIngredient itemInput = IngredientCreatorAccess.item().read(buf);
            INGREDIENT chemicalInput = getDeserializer().read(buf);
            FluidStack output = FluidStack.readFromPacket(buf);
            return factory.create(id, itemInput, chemicalInput, output);
        } catch (Exception e) {
            MekanismScience.LOGGER.error("Error reading item stack chemical to fluid recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull RECIPE recipe) {
        try {
            recipe.write(buf);
        } catch (Exception e) {
            MekanismScience.LOGGER.error("Error writing fluid chemical to fluid recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, INGREDIENT extends
            ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE extends ItemStackChemicalToFluidRecipe<CHEMICAL, STACK, INGREDIENT>> {

        RECIPE create(ResourceLocation id, ItemStackIngredient itemInput, INGREDIENT chemicalInput, FluidStack output);
    }
}
