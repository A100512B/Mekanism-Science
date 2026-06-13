package com.fxd927.mekanismscience.datagen.common.recipe.compat;

import com.fxd927.mekanismscience.api.datagen.recipe.builder.FluidChemicalToFluidChemicalRecipeBuilder;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.FluidChemicalToFluidRecipeBuilder;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.ItemStackChemicalToFluidRecipeBuilder;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.MetalElectrolysisRecipeBuilder;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import com.fxd927.mekanismscience.common.registries.MSGases;
import com.fxd927.mekanismscience.datagen.common.recipe.IMSCompatRecipeProvider;
import com.thevortex.allthemodium.registry.ModRegistry;
import com.thevortex.allthemodium.registry.TagRegistry;
import com.thevortex.allthemodium.registry.mek_reg.ATMResource;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.FloatingLong;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

import static com.fxd927.mekanismscience.common.MekanismScience.rl;
import static mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess.*;

@NothingNullByDefault
public class ATMRecipeProvider implements IMSCompatRecipeProvider {

    private static final ICondition modLoaded = new ModLoadedCondition("allthemodium");

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> writer) {
        processing(writer);
    }

    private void processing(Consumer<FinishedRecipe> writer) {
        String basePath = "compat/allthemodium/processing/";
        // ATM
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(TagRegistry.ALLTHEMODIUM_ORE_ITEM, 1),
                        gas().from(MSGases.AQUA_REGIA, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.ATM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "allthemodium/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(TagRegistry.RAW_ALLTHEMODIUM, 3),
                        gas().from(MSGases.AQUA_REGIA, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.ATM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "allthemodium/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.ATM), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATMResource.ATM).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "allthemodium/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATMResource.ATM), 15),
                        gas().from(MSGases.AQUA_REGIA, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATMResource.ATM).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "allthemodium/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATMResource.ATM), 10),
                        new ItemStack(ModRegistry.ALLTHEMODIUM_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "allthemodium/electrolysis"));
        // Vibranium
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(TagRegistry.VIBRANIUM_ORE_ITEM, 1),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.VIB).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "vibranium/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(TagRegistry.RAW_VIBRANIUM, 3),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.VIB).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "vibranium/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.VIB), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATMResource.ATM).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "vibranium/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATMResource.VIB), 15),
                        gas().from(MSGases.POTASSIUM_NITRATE, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATMResource.ATM).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "vibranium/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATMResource.VIB), 10),
                        new ItemStack(ModRegistry.VIBRANIUM_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "vibranium/electrolysis"));
        // Unobtainium
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(TagRegistry.UNOBTAINIUM_ORE_ITEM, 1),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.UNOB).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "unobtainium/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(TagRegistry.RAW_UNOBTAINIUM, 3),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.UNOB).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "unobtainium/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATMResource.UNOB), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATMResource.ATM).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "unobtainium/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATMResource.UNOB), 15),
                        gas().from(MSGases.POTASSIUM_NITRATE, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATMResource.ATM).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "unobtainium/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATMResource.UNOB), 10),
                        new ItemStack(ModRegistry.UNOBTAINIUM_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "unobtainium/electrolysis"));

    }
}
