package com.fxd927.mekanismscience.common.recipe.impl;

import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidRecipe;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

@NothingNullByDefault
public class ExtractionIRecipe extends FluidGasToFluidRecipe {

    public ExtractionIRecipe(ResourceLocation id, FluidStackIngredient fluidInput, GasStackIngredient gasInput,
                             FluidStack output) {
        super(id, fluidInput, gasInput, output);
    }

    @Override
    public RecipeType<?> getType() {
        return MSRecipeType.EXTRACTION.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MSRecipeSerializers.EXTRACTION.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.EXTRACTING_PLANT_CASING.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.EXTRACTING_PLANT_CASING.getItemStack();
    }
}
