package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import com.fxd927.mekanismscience.api.datagen.recipe.MSRecipeBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Function;

@NothingNullByDefault
public class ItemStackChemicalToFluidRecipeBuilder<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK>> extends MSRecipeBuilder<ItemStackChemicalToFluidRecipeBuilder<CHEMICAL,
        STACK, INGREDIENT>> {

    private final Function<FluidStack, JsonElement> outputSerializer;
    private final ItemStackIngredient itemInput;
    private final INGREDIENT chemicalInput;
    private final FluidStack output;

    protected ItemStackChemicalToFluidRecipeBuilder(ItemStackIngredient itemInput, INGREDIENT chemicalInput, FluidStack output, Function<FluidStack, JsonElement> outputSerializer) {
        super(msSerializer("acid_leaching"));
        this.itemInput = itemInput;
        this.chemicalInput = chemicalInput;
        this.output = output;
        this.outputSerializer = outputSerializer;
    }

    public static ItemStackChemicalToFluidRecipeBuilder<Gas, GasStack, GasStackIngredient> acidLeaching(ItemStackIngredient itemInput, GasStackIngredient gasInput, FluidStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This acid leaching recipe requires a non empty output.");
        }
        return new ItemStackChemicalToFluidRecipeBuilder<>(itemInput, gasInput, output, SerializerHelper::serializeFluidStack);
    }

    @Override
    protected ItemStackChemicalToFluidRecipeResult getResult(ResourceLocation id) {
        return new ItemStackChemicalToFluidRecipeResult(id);
    }

    public class ItemStackChemicalToFluidRecipeResult extends RecipeResult {

        protected ItemStackChemicalToFluidRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonConstants.ITEM_INPUT, itemInput.serialize());
            json.add(JsonConstants.CHEMICAL_INPUT, chemicalInput.serialize());
            json.add(JsonConstants.OUTPUT, outputSerializer.apply(output));
        }
    }
}
