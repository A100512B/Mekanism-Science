package com.fxd927.mekanismscience.common.recipe.impl;

import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSRecipeSerializers;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class PressurizedPolymerizingIRecipe extends GasToGasRecipe {

    public PressurizedPolymerizingIRecipe(ResourceLocation id, GasStackIngredient input, GasStack output) {
        super(id, input, output);
    }

    @Override
    public RecipeType<?> getType() {
        return MSRecipeType.PRESSURIZED_POLYMERIZING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MSRecipeSerializers.PRESSURIZED_POLYMERIZING.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER.getItemStack();
    }
}
