package com.fxd927.mekanismscience.datagen.common.recipe;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public interface IMSCompatRecipeProvider {

    void buildRecipes(Consumer<FinishedRecipe> writer);
}
