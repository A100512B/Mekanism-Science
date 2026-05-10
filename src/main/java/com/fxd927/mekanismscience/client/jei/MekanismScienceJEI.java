package com.fxd927.mekanismscience.client.jei;

import com.fxd927.mekanismscience.client.jei.machine.AcidLeachingRecipeCategory;
import com.fxd927.mekanismscience.client.jei.machine.AntiExtractionRecipeCategory;
import com.fxd927.mekanismscience.client.jei.machine.ExtractionRecipeCategory;
import com.fxd927.mekanismscience.client.jei.machine.MetalElectrolysisRecipeCategory;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.client.jei.CatalystRegistryHelper;
import mekanism.client.jei.RecipeRegistryHelper;
import mekanism.client.jei.machine.GasToGasRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class MekanismScienceJEI implements IModPlugin {

    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return MekanismScience.rl("jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new GasToGasRecipeCategory(helper, MSJEIRecipeType.PRESSURIZED_POLYMERIZING, MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER));
        registry.addRecipeCategories(new AcidLeachingRecipeCategory(helper, MSJEIRecipeType.ACID_LEACHING));
        registry.addRecipeCategories(new ExtractionRecipeCategory(helper, MSJEIRecipeType.EXTRACTION));
        registry.addRecipeCategories(new AntiExtractionRecipeCategory(helper, MSJEIRecipeType.ANTI_EXTRACTION));
        registry.addRecipeCategories(new MetalElectrolysisRecipeCategory(helper, MSJEIRecipeType.METAL_ELECTROLYSIS));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        RecipeRegistryHelper.register(registry, MSJEIRecipeType.PRESSURIZED_POLYMERIZING, MSRecipeType.PRESSURIZED_POLYMERIZING);
        RecipeRegistryHelper.register(registry, MSJEIRecipeType.ACID_LEACHING, MSRecipeType.ACID_LEACHING);
        RecipeRegistryHelper.register(registry, MSJEIRecipeType.EXTRACTION, MSRecipeType.EXTRACTION);
        RecipeRegistryHelper.register(registry, MSJEIRecipeType.ANTI_EXTRACTION, MSRecipeType.ANTI_EXTRACTION);
        RecipeRegistryHelper.register(registry, MSJEIRecipeType.METAL_ELECTROLYSIS, MSRecipeType.METAL_ELECTROLYSIS);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER);
        CatalystRegistryHelper.register(registry, MSBlocks.ACID_LEACHER);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.EXTRACTION, MSBlocks.EXTRACTING_PLANT_CASING, MSBlocks.EXTRACTING_PLANT_PORT,
                MSBlocks.EXTRACTING_PILLAR);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.ANTI_EXTRACTION, MSBlocks.ANTI_EXTRACTING_PLANT_CASING, MSBlocks.ANTI_EXTRACTING_PLANT_PORT,
                MSBlocks.ANTI_EXTRACTING_PILLAR);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.METAL_ELECTROLYSIS, MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING, MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT,
                MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR, MSBlocks.METAL_ELECTROLYZING_ROD);
    }
}
