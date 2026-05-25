package com.fxd927.mekanismscience.common.recipe.impl;

import com.fxd927.mekanismscience.api.recipes.IrradiatingRecipe;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public class IrradiatingIRecipe extends IrradiatingRecipe {

    public IrradiatingIRecipe(ResourceLocation id, ItemStackIngredient itemInput, GasStackIngredient gasInput, ChemicalStack<?> output) {
        super(id, itemInput, gasInput, output);
    }

    @Override
    @NotNull
    public RecipeType<IrradiatingRecipe> getType() {
        return MSRecipeType.IRRADIATING.get();
    }

    @Override
    @NotNull
    public RecipeSerializer<IrradiatingRecipe> getSerializer() {
        return MSRecipeSerializers.IRRADIATING.get();
    }

    @Override
    @NotNull
    public String getGroup() {
        return MSBlocks.IRRADIATOR.getName();
    }

    @Override
    @NotNull
    public ItemStack getToastSymbol() {
        return MSBlocks.IRRADIATOR.getItemStack();
    }
}
