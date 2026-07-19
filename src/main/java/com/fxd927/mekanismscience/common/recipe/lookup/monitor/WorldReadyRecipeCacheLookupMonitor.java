package com.fxd927.mekanismscience.common.recipe.lookup.monitor;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public class WorldReadyRecipeCacheLookupMonitor<RECIPE extends MekanismRecipe> extends RecipeCacheLookupMonitor<RECIPE> {

    private final BooleanSupplier canLookupRecipes;

    public WorldReadyRecipeCacheLookupMonitor(IRecipeLookupHandler<RECIPE> handler, BooleanSupplier canLookupRecipes) {
        super(handler);
        this.canLookupRecipes = Objects.requireNonNull(canLookupRecipes, "canLookupRecipes");
    }

    @Override
    public boolean updateAndProcess() {
        if (!canLookupRecipes.getAsBoolean()) {
            onChange();
            return false;
        }
        return super.updateAndProcess();
    }
}
