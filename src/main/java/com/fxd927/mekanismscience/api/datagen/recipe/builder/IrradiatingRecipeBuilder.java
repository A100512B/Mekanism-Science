package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import com.fxd927.mekanismscience.api.datagen.recipe.MSRecipeBuilder;
import com.google.gson.JsonObject;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.merged.BoxedChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;

@NothingNullByDefault
public class IrradiatingRecipeBuilder extends MSRecipeBuilder<IrradiatingRecipeBuilder> {

    private final ItemStackIngredient itemInput;
    private final GasStackIngredient gasInput;
    private final BoxedChemicalStack output;

    protected IrradiatingRecipeBuilder(ItemStackIngredient itemInput, GasStackIngredient gasInput, BoxedChemicalStack output) {
        super(msSerializer("irradiating"));
        this.itemInput = itemInput;
        this.gasInput = gasInput;
        this.output = output;
    }

    public static IrradiatingRecipeBuilder irradiating(ItemStackIngredient itemInput, GasStackIngredient gasInput, BoxedChemicalStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This irradiating recipe requires a non empty output.");
        }
        return new IrradiatingRecipeBuilder(itemInput, gasInput, output);
    }

    @Override
    public IrradiatingRecipeResult getResult(ResourceLocation id) {
        return new IrradiatingRecipeResult(id);
    }

    public class IrradiatingRecipeResult extends RecipeResult {

        protected IrradiatingRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonConstants.ITEM_INPUT, itemInput.serialize());
            json.add(JsonConstants.GAS_INPUT, gasInput.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeBoxedChemicalStack(output));
        }
    }
}
