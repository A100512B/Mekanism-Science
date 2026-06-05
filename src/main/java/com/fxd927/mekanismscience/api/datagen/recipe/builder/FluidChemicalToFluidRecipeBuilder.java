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
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Function;

@NothingNullByDefault
public class FluidChemicalToFluidRecipeBuilder<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, INGREDIENT extends
        ChemicalStackIngredient<CHEMICAL, STACK>> extends MSRecipeBuilder<FluidChemicalToFluidRecipeBuilder<CHEMICAL, STACK, INGREDIENT>> {

    private final Function<FluidStack, JsonElement> outputSerializer;
    private final FluidStackIngredient fluidInput;
    private final INGREDIENT chemicalInput;
    private final FluidStack output;

    protected FluidChemicalToFluidRecipeBuilder(FluidStackIngredient fluidInput, INGREDIENT chemicalInput, FluidStack output, Function<FluidStack, JsonElement> outputSerializer) {
        super(msSerializer("extraction"));
        this.fluidInput = fluidInput;
        this.chemicalInput = chemicalInput;
        this.output = output;
        this.outputSerializer = outputSerializer;
    }

    public static FluidChemicalToFluidRecipeBuilder<Gas, GasStack, GasStackIngredient> extraction(FluidStackIngredient fluidInput, GasStackIngredient chemicalInput, FluidStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This acid leaching recipe requires a non empty output.");
        }
        return new FluidChemicalToFluidRecipeBuilder<>(fluidInput, chemicalInput, output, SerializerHelper::serializeFluidStack);
    }

    @Override
    protected FluidChemicalToFluidRecipeResult getResult(ResourceLocation id) {
        return new FluidChemicalToFluidRecipeResult(id);
    }

    public class FluidChemicalToFluidRecipeResult extends RecipeResult {

        protected FluidChemicalToFluidRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonConstants.FLUID_INPUT, fluidInput.serialize());
            json.add(JsonConstants.CHEMICAL_INPUT, chemicalInput.serialize());
            json.add(JsonConstants.OUTPUT, outputSerializer.apply(output));
        }
    }
}
