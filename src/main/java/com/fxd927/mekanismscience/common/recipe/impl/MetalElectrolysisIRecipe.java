package com.fxd927.mekanismscience.common.recipe.impl;

import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class MetalElectrolysisIRecipe extends MetalElectrolysisRecipe {

    public MetalElectrolysisIRecipe(ResourceLocation id, FluidStackIngredient input, ItemStack output, FloatingLong energyRequired) {
        super(id, input, output, energyRequired);
    }

    @Override
    public RecipeType<?> getType() {
        return MSRecipeType.METAL_ELECTROLYSIS.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MSRecipeSerializers.METAL_ELECTROLYSIS.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING.getItemStack();
    }
}
