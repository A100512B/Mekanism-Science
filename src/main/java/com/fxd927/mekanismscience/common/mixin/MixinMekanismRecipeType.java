package com.fxd927.mekanismscience.common.mixin;

import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidChemicalRecipe;
import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidRecipe;
import com.fxd927.mekanismscience.api.recipes.ItemStackChemicalToFluidRecipe;
import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.chemical.ChemicalToChemicalRecipe;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.ItemChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(value = MekanismRecipeType.class, remap = false)
public abstract class MixinMekanismRecipeType<RECIPE extends MekanismRecipe, INPUT_CACHE extends IInputRecipeCache> implements RecipeType<RECIPE>,
        IMekanismRecipeTypeProvider<RECIPE, INPUT_CACHE> {

    @Shadow
    private static <RECIPE extends MekanismRecipe, INPUT_CACHE extends IInputRecipeCache> RecipeTypeRegistryObject<RECIPE, INPUT_CACHE> register(String name,
                                                                                                                                                 Function<MekanismRecipeType<RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void ms$initRecipeType(CallbackInfo ci) {
        MSRecipeType.PRESSURIZED_POLYMERIZING = register("pressurized_polymerizing", recipeType -> new SingleChemical<>(recipeType, ChemicalToChemicalRecipe::getInput));
        MSRecipeType.EXTRACTION = register("extraction", recipeType -> new FluidChemical<>(recipeType, FluidChemicalToFluidRecipe::getFluidInput, FluidChemicalToFluidRecipe::getChemicalInput));
        MSRecipeType.ANTI_EXTRACTION = register("anti_extraction", recipeType -> new FluidChemical<>(recipeType, FluidChemicalToFluidChemicalRecipe::getFluidInput, FluidChemicalToFluidChemicalRecipe::getChemicalInput));
        MSRecipeType.METAL_ELECTROLYSIS = register("metal_electrolysis", recipeType -> new SingleFluid<>(recipeType, MetalElectrolysisRecipe::getInput));
        MSRecipeType.ACID_LEACHING = register("acid_leaching", recipeType -> new ItemChemical<>(recipeType, ItemStackChemicalToFluidRecipe::getItemInput, ItemStackChemicalToFluidRecipe::getChemicalInput));
    }
}
