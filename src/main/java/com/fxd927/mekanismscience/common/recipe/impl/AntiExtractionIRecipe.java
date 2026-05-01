package com.fxd927.mekanismscience.common.recipe.impl;

import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidGasRecipe;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

@NothingNullByDefault
public class AntiExtractionIRecipe extends FluidGasToFluidGasRecipe {

    public AntiExtractionIRecipe(ResourceLocation id, FluidStackIngredient fluidInput, GasStackIngredient gasInput,
                                 FluidStack fluidOutput, GasStack gasOutput) {
        super(id, fluidInput, gasInput, fluidOutput, gasOutput);
    }

    @Override
    public RecipeType<?> getType() {
        return MSRecipeType.ANTI_EXTRACTION.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MSRecipeSerializers.ANTI_EXTRACTION.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.ANTI_EXTRACTING_PLANT_CASING.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.ANTI_EXTRACTING_PLANT_CASING.getItemStack();
    }
}
