package com.fxd927.mekanismscience.api.datagen.recipe;

import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

public abstract class MSRecipeBuilder<BUILDER extends MSRecipeBuilder<BUILDER>> extends MekanismRecipeBuilder<BUILDER> {

    public static ResourceLocation msSerializer(String name) {
        return ResourceLocation.fromNamespaceAndPath(MekanismScience.MODID, name);
    }

    protected MSRecipeBuilder(ResourceLocation serializerName) {
        super(serializerName);
    }
}
