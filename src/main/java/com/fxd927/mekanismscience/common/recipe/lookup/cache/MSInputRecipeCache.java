package com.fxd927.mekanismscience.common.recipe.lookup.cache;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.MekanismRecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public final class MSInputRecipeCache {

    private MSInputRecipeCache() {
    }

    public static class SingleFluid<RECIPE extends MekanismRecipe & Predicate<FluidStack>>
            extends mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid<RECIPE> {

        public SingleFluid(MekanismRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputExtractor) {
            super(recipeType, inputExtractor);
        }

        @Override
        public boolean containsInput(@Nullable Level world, FluidStack input) {
            return world != null && super.containsInput(world, input);
        }

        @Nullable
        @Override
        public RECIPE findFirstRecipe(@Nullable Level world, FluidStack input) {
            return world == null ? null : super.findFirstRecipe(world, input);
        }

        @Nullable
        @Override
        public RECIPE findTypeBasedRecipe(@Nullable Level world, FluidStack input) {
            return world == null ? null : super.findTypeBasedRecipe(world, input);
        }

        @Nullable
        @Override
        public RECIPE findTypeBasedRecipe(@Nullable Level world, FluidStack input, Predicate<RECIPE> matchCriteria) {
            return world == null ? null : super.findTypeBasedRecipe(world, input, matchCriteria);
        }
    }

    public static class SingleChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
            RECIPE extends MekanismRecipe & Predicate<STACK>>
            extends mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical<CHEMICAL, STACK, RECIPE> {

        public SingleChemical(MekanismRecipeType<RECIPE, ?> recipeType,
                              Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputExtractor) {
            super(recipeType, inputExtractor);
        }

        @Override
        public boolean containsInput(@Nullable Level world, STACK input) {
            return world != null && super.containsInput(world, input);
        }

        @Nullable
        @Override
        public RECIPE findFirstRecipe(@Nullable Level world, STACK input) {
            return world == null ? null : super.findFirstRecipe(world, input);
        }

        @Nullable
        @Override
        public RECIPE findTypeBasedRecipe(@Nullable Level world, STACK input) {
            return world == null ? null : super.findTypeBasedRecipe(world, input);
        }

        @Nullable
        @Override
        public RECIPE findTypeBasedRecipe(@Nullable Level world, STACK input, Predicate<RECIPE> matchCriteria) {
            return world == null ? null : super.findTypeBasedRecipe(world, input, matchCriteria);
        }
    }

    public static class ItemChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
            RECIPE extends MekanismRecipe & BiPredicate<ItemStack, STACK>>
            extends mekanism.common.recipe.lookup.cache.InputRecipeCache.ItemChemical<CHEMICAL, STACK, RECIPE> {

        public ItemChemical(MekanismRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                            Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputBExtractor) {
            super(recipeType, inputAExtractor, inputBExtractor);
        }

        @Override
        public boolean containsInputA(@Nullable Level world, ItemStack input) {
            return world != null && super.containsInputA(world, input);
        }

        @Override
        public boolean containsInputB(@Nullable Level world, STACK input) {
            return world != null && super.containsInputB(world, input);
        }

        @Override
        public boolean containsInputAB(@Nullable Level world, ItemStack inputA, STACK inputB) {
            return world != null && super.containsInputAB(world, inputA, inputB);
        }

        @Override
        public boolean containsInputBA(@Nullable Level world, ItemStack inputA, STACK inputB) {
            return world != null && super.containsInputBA(world, inputA, inputB);
        }

        @Nullable
        @Override
        public RECIPE findFirstRecipe(@Nullable Level world, ItemStack inputA, STACK inputB) {
            return world == null ? null : super.findFirstRecipe(world, inputA, inputB);
        }

        @Nullable
        @Override
        public RECIPE findFirstRecipe(@Nullable Level world, ItemStack inputA, STACK inputB, boolean useCacheA) {
            return world == null ? null : super.findFirstRecipe(world, inputA, inputB, useCacheA);
        }

        @Nullable
        @Override
        public RECIPE findTypeBasedRecipe(@Nullable Level world, ItemStack inputA, STACK inputB, Predicate<RECIPE> matchCriteria) {
            return world == null ? null : super.findTypeBasedRecipe(world, inputA, inputB, matchCriteria);
        }
    }

    public static class FluidChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
            RECIPE extends MekanismRecipe & BiPredicate<FluidStack, STACK>>
            extends mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical<CHEMICAL, STACK, RECIPE> {

        public FluidChemical(MekanismRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputAExtractor,
                             Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputBExtractor) {
            super(recipeType, inputAExtractor, inputBExtractor);
        }

        @Override
        public boolean containsInputA(@Nullable Level world, FluidStack input) {
            return world != null && super.containsInputA(world, input);
        }

        @Override
        public boolean containsInputB(@Nullable Level world, STACK input) {
            return world != null && super.containsInputB(world, input);
        }

        @Override
        public boolean containsInputAB(@Nullable Level world, FluidStack inputA, STACK inputB) {
            return world != null && super.containsInputAB(world, inputA, inputB);
        }

        @Override
        public boolean containsInputBA(@Nullable Level world, FluidStack inputA, STACK inputB) {
            return world != null && super.containsInputBA(world, inputA, inputB);
        }

        @Nullable
        @Override
        public RECIPE findFirstRecipe(@Nullable Level world, FluidStack inputA, STACK inputB) {
            return world == null ? null : super.findFirstRecipe(world, inputA, inputB);
        }

        @Nullable
        @Override
        public RECIPE findFirstRecipe(@Nullable Level world, FluidStack inputA, STACK inputB, boolean useCacheA) {
            return world == null ? null : super.findFirstRecipe(world, inputA, inputB, useCacheA);
        }

        @Nullable
        @Override
        public RECIPE findTypeBasedRecipe(@Nullable Level world, FluidStack inputA, STACK inputB, Predicate<RECIPE> matchCriteria) {
            return world == null ? null : super.findTypeBasedRecipe(world, inputA, inputB, matchCriteria);
        }
    }

    public static class ItemFluid<RECIPE extends MekanismRecipe & BiPredicate<ItemStack, FluidStack>>
            extends com.fxd927.mekanismscience.common.recipe.lookup.cache.ItemFluid<RECIPE> {

        public ItemFluid(MekanismRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                         Function<RECIPE, FluidStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, inputBExtractor);
        }
    }
}
