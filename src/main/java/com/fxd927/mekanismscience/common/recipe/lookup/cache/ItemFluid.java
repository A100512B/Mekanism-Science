package com.fxd927.mekanismscience.common.recipe.lookup.cache;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.DoubleInputRecipeCache;
import mekanism.common.recipe.lookup.cache.type.FluidInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class ItemFluid<RECIPE extends MekanismRecipe & BiPredicate<ItemStack, FluidStack>> extends DoubleInputRecipeCache<ItemStack,
        ItemStackIngredient, FluidStack, FluidStackIngredient, RECIPE, ItemInputCache<RECIPE>, FluidInputCache<RECIPE>> {

    public ItemFluid(MekanismRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                     Function<RECIPE, FluidStackIngredient> inputBExtractor) {
        super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>());
    }

    @Override
    public boolean containsInputA(@Nullable Level world, ItemStack input) {
        return world != null && super.containsInputA(world, input);
    }

    @Override
    public boolean containsInputB(@Nullable Level world, FluidStack input) {
        return world != null && super.containsInputB(world, input);
    }

    @Override
    public boolean containsInputAB(@Nullable Level world, ItemStack inputA, FluidStack inputB) {
        return world != null && super.containsInputAB(world, inputA, inputB);
    }

    @Override
    public boolean containsInputBA(@Nullable Level world, ItemStack inputA, FluidStack inputB) {
        return world != null && super.containsInputBA(world, inputA, inputB);
    }

    @Nullable
    @Override
    public RECIPE findFirstRecipe(@Nullable Level world, ItemStack inputA, FluidStack inputB) {
        return world == null ? null : super.findFirstRecipe(world, inputA, inputB);
    }

    @Nullable
    @Override
    public RECIPE findFirstRecipe(@Nullable Level world, ItemStack inputA, FluidStack inputB, boolean useCacheA) {
        return world == null ? null : super.findFirstRecipe(world, inputA, inputB, useCacheA);
    }

    @Nullable
    @Override
    public RECIPE findTypeBasedRecipe(@Nullable Level world, ItemStack inputA, FluidStack inputB, Predicate<RECIPE> matchCriteria) {
        return world == null ? null : super.findTypeBasedRecipe(world, inputA, inputB, matchCriteria);
    }
}
