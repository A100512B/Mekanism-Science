package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import com.fxd927.mekanismscience.api.MSJsonConstants;
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
public class FluidChemicalToFluidChemicalRecipeBuilder<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, INGREDIENT extends
        ChemicalStackIngredient<CHEMICAL, STACK>> extends MSRecipeBuilder<FluidChemicalToFluidChemicalRecipeBuilder<CHEMICAL,
        STACK, INGREDIENT>> {

    private final Function<FluidStack, JsonElement> fluidOutputSerializer;
    private final Function<STACK, JsonElement> chemicalOutputSerializer;
    private final FluidStackIngredient fluidInput;
    private final INGREDIENT chemicalInput;
    private final FluidStack fluidOutput;
    private final STACK chemicalOutput;

    protected FluidChemicalToFluidChemicalRecipeBuilder(FluidStackIngredient fluidInput, INGREDIENT chemicalInput,
                                                        FluidStack fluidOutput, STACK chemicalOutput,
                                                        Function<FluidStack, JsonElement> fluidOutputSerializer,
                                                        Function<STACK, JsonElement> chemicalOutputSerializer) {
        super(msSerializer("anti_extraction"));
        this.fluidInput = fluidInput;
        this.chemicalInput = chemicalInput;
        this.fluidOutput = fluidOutput;
        this.chemicalOutput = chemicalOutput;
        this.fluidOutputSerializer = fluidOutputSerializer;
        this.chemicalOutputSerializer = chemicalOutputSerializer;
    }

    public static FluidChemicalToFluidChemicalRecipeBuilder<Gas, GasStack, GasStackIngredient> antiExtraction(FluidStackIngredient fluidInput, GasStackIngredient chemicalInput,
                                                                                                              FluidStack fluidOutput, GasStack chemicalOutput) {
        if (fluidOutput.isEmpty() || chemicalOutput.isEmpty()) {
            throw new IllegalArgumentException("This anti-extraction recipe requires non empty outputs.");
        }
        return new FluidChemicalToFluidChemicalRecipeBuilder<>(fluidInput, chemicalInput, fluidOutput, chemicalOutput, SerializerHelper::serializeFluidStack, SerializerHelper::serializeGasStack);
    }

    @Override
    protected FluidChemicalToFluidChemicalRecipeResult getResult(ResourceLocation id) {
        return new FluidChemicalToFluidChemicalRecipeResult(id);
    }

    public class FluidChemicalToFluidChemicalRecipeResult extends RecipeResult {

        protected FluidChemicalToFluidChemicalRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonConstants.FLUID_INPUT, fluidInput.serialize());
            json.add(JsonConstants.CHEMICAL_INPUT, chemicalInput.serialize());
            json.add(JsonConstants.FLUID_OUTPUT, fluidOutputSerializer.apply(fluidOutput));
            json.add(MSJsonConstants.CHEMICAL_OUTPUT, chemicalOutputSerializer.apply(chemicalOutput));
        }
    }
}
