package com.fxd927.mekanismscience.datagen.client.model;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import com.fxd927.mekanismscience.common.registries.MSItems;
import mekanism.common.util.RegistryUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MSItemModelProvider extends ItemModelProvider {

    public MSItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MekanismScience.MODID, helper);
    }

    @Override
    protected void registerModels() {
        MSFluids.FLUIDS.getAllFluids().forEach(fluidRO -> withExistingParent(RegistryUtils.getPath(fluidRO.getBucket()), ResourceLocation.fromNamespaceAndPath("forge", "item/bucket"))
                .customLoader(DynamicFluidContainerModelBuilder::begin)
                .fluid(fluidRO.getStillFluid()));
        MSItems.ITEMS.getAllItems().forEach(item -> basicItem(item.asItem()));
    }
}
