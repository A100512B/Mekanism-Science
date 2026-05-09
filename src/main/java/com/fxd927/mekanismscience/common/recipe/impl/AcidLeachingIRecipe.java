package com.fxd927.mekanismscience.common.recipe.impl;

import com.fxd927.mekanismscience.api.recipes.ItemStackGasToFluidRecipe;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

@NothingNullByDefault
public class AcidLeachingIRecipe extends ItemStackGasToFluidRecipe {

    public AcidLeachingIRecipe(ResourceLocation id, ItemStackIngredient itemInput, GasStackIngredient gasInput,
                               FluidStack output) {
        super(id, itemInput, gasInput, output);
    }

    @Override
    public RecipeType<?> getType() {
        return MSRecipeType.ACID_LEACHING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MSRecipeSerializers.ACID_LEACHING.get();
    }

    @Override
    public String getGroup() {
        return super.getGroup();
    }

    @Override
    public ItemStack getToastSymbol() {
        return super.getToastSymbol();
    }
}
