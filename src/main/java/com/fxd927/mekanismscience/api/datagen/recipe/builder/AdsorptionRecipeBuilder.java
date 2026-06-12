package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import com.fxd927.mekanismscience.api.datagen.recipe.MSRecipeBuilder;
import com.google.gson.JsonObject;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.merged.BoxedChemicalStack;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;

@NothingNullByDefault
public class AdsorptionRecipeBuilder extends MSRecipeBuilder<AdsorptionRecipeBuilder> {

    private final ItemStackIngredient itemInput;
    private final FluidStackIngredient fluidInput;
    private final BoxedChemicalStack output;

    protected AdsorptionRecipeBuilder(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, BoxedChemicalStack output) {
        super(msSerializer("adsorption"));
        this.itemInput = itemInput;
        this.fluidInput = fluidInput;
        this.output = output;
    }

    public static AdsorptionRecipeBuilder adsorption(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, BoxedChemicalStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This adsorption recipe requires a non empty output.");
        }
        return new AdsorptionRecipeBuilder(itemInput, fluidInput, output);
    }

    @Override
    protected AdsorptionRecipeResult getResult(ResourceLocation id) {
        return new AdsorptionRecipeResult(id);
    }

    public class AdsorptionRecipeResult extends RecipeResult {

        protected AdsorptionRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonConstants.ITEM_INPUT, itemInput.serialize());
            json.add(JsonConstants.FLUID_INPUT, fluidInput.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeBoxedChemicalStack(output));
        }
    }
}
