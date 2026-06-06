package com.fxd927.mekanismscience.datagen.common.recipe.compat;

import com.fxd927.mekanismscience.api.datagen.recipe.builder.FluidChemicalToFluidChemicalRecipeBuilder;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.FluidChemicalToFluidRecipeBuilder;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.ItemStackChemicalToFluidRecipeBuilder;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.MetalElectrolysisRecipeBuilder;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import com.fxd927.mekanismscience.common.registries.MSGases;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.FloatingLong;
import mekanism.common.registries.MekanismGases;
import net.allthemods.alltheores.blocks.BlockList;
import net.allthemods.alltheores.blocks.mek_reg.ATOResource;
import net.allthemods.alltheores.infos.ItemTagRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

import static com.fxd927.mekanismscience.common.MekanismScience.rl;
import static mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess.*;

@NothingNullByDefault
public class ForgeRecipeProvider extends RecipeProvider {

    private static final ICondition modLoaded = new ModLoadedCondition("alltheores");

    public ForgeRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        processing(writer);
    }

    private void processing(Consumer<FinishedRecipe> writer) {
        String basePath = "compat/forge/processing/";
        // Aluminum
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.ALUMINUM_ORE_ITEM, 1),
                        gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.ALUMINUM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "aluminum/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.RAW_ALUMINUM, 3),
                        gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.ALUMINUM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "aluminum/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.ALUMINUM), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.ALUMINUM).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "aluminum/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.ALUMINUM), 15),
                        gas().from(MekanismGases.SULFURIC_ACID, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.ALUMINUM).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "aluminum/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.ALUMINUM), 10),
                        new ItemStack(BlockList.ALUMINUM_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "aluminum/electrolysis"));
        // Iridium
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.IRIDIUM_ORE_ITEM, 1),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.IRIDIUM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "iridium/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.RAW_IRIDIUM, 3),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.IRIDIUM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "iridium/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.IRIDIUM), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.IRIDIUM).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "iridium/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.IRIDIUM), 15),
                        gas().from(MSGases.POTASSIUM_NITRATE, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.IRIDIUM).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "iridium/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.IRIDIUM), 10),
                        new ItemStack(BlockList.IRIDIUM_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "iridium/electrolysis"));
        // Nickel
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.NICKEL_ORE_ITEM, 1),
                        gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.NICKEL).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "nickel/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.RAW_NICKEL, 3),
                        gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.NICKEL).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "nickel/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.NICKEL), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.NICKEL).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "nickel/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.NICKEL), 15),
                        gas().from(MekanismGases.SULFURIC_ACID, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.NICKEL).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "nickel/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.NICKEL), 10),
                        new ItemStack(BlockList.NICKEL_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "nickel/electrolysis"));
        // Platinum
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.PLATINUM_ORE_ITEM, 1),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.PLATINUM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "platinum/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.RAW_PLATINUM, 3),
                        gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.PLATINUM).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "platinum/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.PLATINUM), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.PLATINUM).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "platinum/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.PLATINUM), 15),
                        gas().from(MSGases.POTASSIUM_NITRATE, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.PLATINUM).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "platinum/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.PLATINUM), 10),
                        new ItemStack(BlockList.PLATINUM_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "platinum/electrolysis"));
        // Silver
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.SILVER_ORE_ITEM, 1),
                        gas().from(MSGases.NITRIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.SILVER).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "silver/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.RAW_SILVER, 3),
                        gas().from(MSGases.NITRIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.SILVER).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "silver/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.SILVER), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.SILVER).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "silver/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.SILVER), 15),
                        gas().from(MSGases.NITRIC_ACID, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.SILVER).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "silver/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.SILVER), 10),
                        new ItemStack(BlockList.SILVER_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "silver/electrolysis"));
        // Zinc
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.ZINC_ORE_ITEM, 1),
                        gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.ZINC).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "zinc/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                        item().from(ItemTagRegistry.RAW_ZINC, 3),
                        gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                        MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.ZINC).getFluidStack(1_000))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "zinc/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                        fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(ATOResource.ZINC), 10),
                        gas().from(MSGases.P204, 5),
                        MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.ZINC).getFluidStack(15))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "zinc/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                        fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(ATOResource.ZINC), 15),
                        gas().from(MekanismGases.SULFURIC_ACID, 5),
                        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.ZINC).getFluidStack(5),
                        MSGases.P204.getStack(10))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "zinc/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                        fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(ATOResource.ZINC), 10),
                        new ItemStack(BlockList.ZINC_DUST.get(), 1),
                        FloatingLong.createConst(250))
                .addCondition(modLoaded)
                .build(writer, rl(basePath + "zinc/electrolysis"));
    }
}
