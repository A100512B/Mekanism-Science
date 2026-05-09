package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidGasRecipe;
import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidRecipe;
import com.fxd927.mekanismscience.api.recipes.ItemStackGasToFluidRecipe;
import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.recipe.impl.*;
import com.fxd927.mekanismscience.common.recipe.serializer.FluidGasToFluidGasRecipeSerializer;
import com.fxd927.mekanismscience.common.recipe.serializer.FluidGasToFluidRecipeSerializer;
import com.fxd927.mekanismscience.common.recipe.serializer.ItemStackGasToFluidRecipeSerializer;
import com.fxd927.mekanismscience.common.recipe.serializer.MetalElectrolysisRecipeSerializer;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.common.recipe.serializer.GasToGasRecipeSerializer;
import mekanism.common.registration.impl.RecipeSerializerDeferredRegister;
import mekanism.common.registration.impl.RecipeSerializerRegistryObject;

public class MSRecipeSerializers {

    private MSRecipeSerializers() {}

    public static final RecipeSerializerDeferredRegister RECIPE_SERIALIZERS = new RecipeSerializerDeferredRegister(MekanismScience.MODID);

    public static final RecipeSerializerRegistryObject<GasToGasRecipe> PRESSURIZED_POLYMERIZING = RECIPE_SERIALIZERS.register("pressurized_polymerizing", () -> new GasToGasRecipeSerializer<>(PressurizedPolymerizingIRecipe::new));
    public static final RecipeSerializerRegistryObject<FluidGasToFluidRecipe> EXTRACTION = RECIPE_SERIALIZERS.register("extraction", () -> new FluidGasToFluidRecipeSerializer<>(ExtractionIRecipe::new));
    public static final RecipeSerializerRegistryObject<FluidGasToFluidGasRecipe> ANTI_EXTRACTION = RECIPE_SERIALIZERS.register("anti_extraction", () -> new FluidGasToFluidGasRecipeSerializer<>(AntiExtractionIRecipe::new));
    public static final RecipeSerializerRegistryObject<MetalElectrolysisRecipe> METAL_ELECTROLYSIS = RECIPE_SERIALIZERS.register("metal_electrolysis", () -> new MetalElectrolysisRecipeSerializer<>(MetalElectrolysisIRecipe::new));
    public static final RecipeSerializerRegistryObject<ItemStackGasToFluidRecipe> ACID_LEACHING = RECIPE_SERIALIZERS.register("acid_leaching", () -> new ItemStackGasToFluidRecipeSerializer<>(AcidLeachingIRecipe::new));
}
