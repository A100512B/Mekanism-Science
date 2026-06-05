package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import com.fxd927.mekanismscience.api.datagen.recipe.MSRecipeBuilder;
import com.google.gson.JsonObject;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@NothingNullByDefault
public class MetalElectrolysisRecipeBuilder extends MSRecipeBuilder<MetalElectrolysisRecipeBuilder> {

    private final FluidStackIngredient input;
    private final ItemStack output;
    private final FloatingLong energyRequired;

    protected MetalElectrolysisRecipeBuilder(FluidStackIngredient input, ItemStack output, FloatingLong energyRequired) {
        super(msSerializer("metal_electrolysis"));
        this.input = input;
        this.output = output;
        this.energyRequired = energyRequired;
    }

    public static MetalElectrolysisRecipeBuilder metalElectrolysis(FluidStackIngredient input, ItemStack output, FloatingLong energyRequired) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This metal electrolysis recipe requires a non empty item output.");
        }
        if (energyRequired.smallerOrEqual(FloatingLong.ZERO)) {
            throw new IllegalArgumentException("This metal electrolysis recipe requires a positive energy consumption.");
        }
        return new MetalElectrolysisRecipeBuilder(input, output, energyRequired);
    }

    @Override
    protected MetalElectrolysisRecipeResult getResult(ResourceLocation id) {
        return new MetalElectrolysisRecipeResult(id);
    }

    public class MetalElectrolysisRecipeResult extends RecipeResult {

        protected MetalElectrolysisRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonConstants.INPUT, input.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeItemStack(output));
            json.addProperty(JsonConstants.ENERGY_REQUIRED, energyRequired);
        }
    }
}
