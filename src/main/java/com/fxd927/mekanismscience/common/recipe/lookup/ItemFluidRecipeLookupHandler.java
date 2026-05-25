package com.fxd927.mekanismscience.common.recipe.lookup;

import com.fxd927.mekanismscience.common.recipe.lookup.cache.ItemFluid;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.IDoubleRecipeLookupHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.BiPredicate;

public interface ItemFluidRecipeLookupHandler<RECIPE extends MekanismRecipe & BiPredicate<ItemStack, FluidStack>>
        extends IDoubleRecipeLookupHandler<ItemStack, FluidStack, RECIPE, ItemFluid<RECIPE>> {
}
