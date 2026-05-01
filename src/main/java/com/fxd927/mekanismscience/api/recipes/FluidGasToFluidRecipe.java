package com.fxd927.mekanismscience.api.recipes;

import mekanism.api.annotations.ParametersAreNotNullByDefault;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@ParametersAreNotNullByDefault
public abstract class FluidGasToFluidRecipe extends FluidChemicalToFluidRecipe<Gas, GasStack, GasStackIngredient> {

    public FluidGasToFluidRecipe(ResourceLocation id, FluidStackIngredient fluidInput, GasStackIngredient gasInput, FluidStack output) {
        super(id, fluidInput, gasInput, output);
    }
}
