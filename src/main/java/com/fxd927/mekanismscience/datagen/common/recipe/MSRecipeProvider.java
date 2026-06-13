package com.fxd927.mekanismscience.datagen.common.recipe;

import com.fxd927.mekanismscience.api.datagen.recipe.builder.*;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.BaseRecipeBuilder.RecipePattern;
import com.fxd927.mekanismscience.api.datagen.recipe.builder.BaseRecipeBuilder.RecipePattern.TripleLine;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import com.fxd927.mekanismscience.common.registries.MSGases;
import com.fxd927.mekanismscience.common.registries.MSItems;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.merged.BoxedChemicalStack;
import mekanism.api.datagen.recipe.builder.*;
import mekanism.api.math.FloatingLong;
import mekanism.common.registration.impl.FluidDeferredRegister.MekanismFluidType;
import mekanism.common.registration.impl.FluidRegistryObject;
import mekanism.common.registration.impl.GasRegistryObject;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.MiscResource;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.resource.ore.OreType;
import mekanism.common.tags.MekanismTags;
import mekanism.generators.common.registries.GeneratorsGases;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;

import java.util.function.Consumer;

import static com.fxd927.mekanismscience.common.MekanismScience.rl;
import static mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess.*;

@NothingNullByDefault
public class MSRecipeProvider extends RecipeProvider {

    public MSRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        activating(writer);
        adsorption(writer);
        centrifuging(writer);
        chemicalInfusing(writer);
        crafting(writer);
        crystallizing(writer);
        dissolution(writer);
        enriching(writer);
        evaporating(writer);
        injecting(writer);
        irradiating(writer);
        nucleosynthesizing(writer);
        oxidizing(writer);
        polymerizing(writer);
        processing(writer);
        reaction(writer);
        rotary(writer);
        separating(writer);
        smelting(writer);
    }

    private void activating(Consumer<FinishedRecipe> writer) {
        String basePath = "activating/";
        GasToGasRecipeBuilder.activating(
                gas().from(MekanismGases.PLUTONIUM, 2),
                MSGases.AMERICIUM.getStack(1)
        ).build(writer, rl(basePath + "americium"));
        GasToGasRecipeBuilder.activating(
                gas().from(MekanismGases.URANIUM_HEXAFLUORIDE, 2),
                MSGases.STRONTIUM.getStack(1)
        ).build(writer, rl(basePath + "strontium"));
    }

    private void adsorption(Consumer<FinishedRecipe> writer) {
        String basePath = "adsorption/";
        AdsorptionRecipeBuilder.adsorption(
                item().from(ItemTags.COALS, 1),
                fluid().from(MSFluids.COMPRESSED_AIR, 50),
                BoxedChemicalStack.box(MSGases.NITROGEN.getStack(400))
        ).build(writer, rl(basePath + "nitrogen"));
    }

    private void centrifuging(Consumer<FinishedRecipe> writer) {
        String basePath = "centrifuging/";
        GasToGasRecipeBuilder.centrifuging(
                gas().from(MSGases.COMPRESSED_AIR, 50),
                MSGases.HELIUM.getStack(1)
        ).build(writer, rl(basePath + "helium"));
    }

    private void chemicalInfusing(Consumer<FinishedRecipe> writer) {
        String basePath = "chemical_infusing";
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.NITROGEN, 1),
                gas().from(MekanismGases.HYDROGEN, 3),
                MSGases.AMMONIA.getStack(2)
        ).build(writer, rl(basePath + "ammonia"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.NITRIC_ACID, 1),
                gas().from(MekanismGases.HYDROGEN_CHLORIDE, 3),
                MSGases.AQUA_REGIA.getStack(1)
        ).build(writer, rl(basePath + "aqua_regia"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.CONCENTRATED_SEAWATER, 10),
                gas().from(MekanismGases.CHLORINE, 10),
                MSGases.BROMINE.getStack(1)
        ).build(writer, rl(basePath + "bromine"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.WATER_GAS, 1),
                gas().from(MSGases.PROPYLENE, 1),
                MSGases.BUTYRALDEHYDE_MIXTURE.getStack(1)
        ).build(writer, rl(basePath + "butyraldehyde_mixture"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.METHANOL, 1),
                gas().from(MekanismGases.CHLORINE, 1),
                MSGases.CHLOROMETHANE.getStack(1)
        ).build(writer, rl(basePath + "chloromethane"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MekanismGases.WATER_VAPOR, 1),
                gas().from(MekanismGases.ETHENE, 1),
                MSGases.ETHANOL.getStack(1)
        ).build(writer, rl(basePath + "ethanol"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.CONCENTRATED_SEAWATER, 10),
                gas().from(MSGases.BROMINE, 10),
                MSGases.IODINE.getStack(1)
        ).build(writer, rl(basePath + "iodine"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MekanismGases.HYDROGEN, 5),
                gas().from(MSGases._2_ETHYL_2_HEXENAL, 1),
                MSGases.ISOOCTANOL.getStack(1)
        ).build(writer, rl(basePath + "isooctanol"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.ETHANOL, 1),
                gas().from(MSGases.WHEY, 1),
                MSGases.LACTOSE.getStack(1)
        ).build(writer, rl(basePath + "lactose"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.CHLOROMETHANE, 1),
                gas().from(MSGases.AMMONIA, 1),
                MSGases.METHYLAMINE.getStack(1)
        ).build(writer, rl(basePath + "methylamine"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.NITROGEN_DIOXIDE, 2),
                gas().from(MekanismGases.WATER_VAPOR, 1),
                MSGases.NITRIC_ACID.getStack(2)
        ).build(writer, rl(basePath + "nitric_acid"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.AMMONIA, 4),
                gas().from(MekanismGases.OXYGEN, 5),
                MSGases.NITRIC_OXIDE.getStack(4)
        ).build(writer, rl(basePath + "nitric_oxide"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.NITRIC_OXIDE, 2),
                gas().from(MekanismGases.OXYGEN, 1),
                MSGases.NITROGEN_DIOXIDE.getStack(2)
        ).build(writer, rl(basePath + "nitrogen_dioxide"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MSGases.ISOOCTANOL, 1),
                gas().from(MSGases.PHOSPHORYL_CHLORIDE, 1),
                MSGases.P204.getStack(2)
        ).build(writer, rl(basePath + "p204"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MekanismGases.ETHENE, 1),
                gas().from(MekanismGases.HYDROFLUORIC_ACID, 4),
                MSGases.TETRAFLUOROETHYLENE.getStack(1)
        ).build(writer, rl(basePath + "tetrafluoroethylene"));
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                gas().from(MekanismGases.ETHENE, 1),
                gas().from(MSGases._2_BUTENE, 1),
                MSGases.PROPYLENE.getStack(2)
        ).build(writer, rl(basePath + "propylene"));
    }

    private void crystallizing(Consumer<FinishedRecipe> writer) {
        String basePath = "crystallizing/";
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                gas().from(MSGases.LACTOSE, 500),
                MSItems.EXCIPIENT.getItemStack(1)
        ).build(writer, rl(basePath + "excipient"));
    }

    private void crafting(Consumer<FinishedRecipe> writer) {
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.ACID_LEACHER)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('P', 'U', 'P'),
                        TripleLine.of('D', 'I', 'D'),
                        TripleLine.of('B', 'B', 'B')))
                .key('P', MSItems.PTFE_SHEET)
                .key('U', ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "circuits/ultimate")))
                .key('D', MekanismBlocks.DYNAMIC_TANK)
                .key('I', MekanismBlocks.CHEMICAL_INJECTION_CHAMBER)
                .key('B', MekanismTags.Items.STORAGE_BLOCKS_STEEL)
                .build(writer);
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.ADSORPTION_SEPARATOR)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('A', 'C', 'A'),
                        TripleLine.of('S', 'X', 'S'),
                        TripleLine.of('A', 'C', 'A')))
                .key('C', MekanismTags.Items.CIRCUITS_ELITE)
                .key('S', Items.IRON_BARS)
                .key('A', MekanismTags.Items.ALLOYS_REINFORCED)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.AIR_COMPRESSOR)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('A', 'C', 'A'),
                        TripleLine.of('S', 'X', 'S'),
                        TripleLine.of('A', 'T', 'A')))
                .key('T', MekanismBlocks.BASIC_CHEMICAL_TANK)
                .key('C', MekanismTags.Items.CIRCUITS_ADVANCED)
                .key('S', MekanismItems.HDPE_PELLET)
                .key('A', MekanismTags.Items.ALLOYS_INFUSED)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.ANTI_EXTRACTING_PLANT_CASING)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('S', 'S', 'S'),
                        TripleLine.of('S', 'X', 'S'),
                        TripleLine.of('S', 'S', 'S')))
                .key('S', MSItems.PTFE_SHEET)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.ANTI_EXTRACTING_PILLAR, 3)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('P', 'C', 'P'),
                        TripleLine.of('P', 'T', 'P'),
                        TripleLine.of('P', 'A', 'P')
                ))
                .key('P', MSBlocks.ANTI_EXTRACTING_PLANT_CASING)
                .key('C', MekanismTags.Items.CIRCUITS_ADVANCED)
                .key('T', MekanismBlocks.BASIC_CHEMICAL_TANK)
                .key('A', MekanismTags.Items.ALLOYS_INFUSED)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.ANTI_EXTRACTING_PLANT_PORT, 2)
                .pattern(RecipePattern.createPattern(TripleLine.of('C', 'A', 'C')))
                .key('C', MSBlocks.ANTI_EXTRACTING_PLANT_CASING)
                .key('A', MekanismTags.Items.CIRCUITS_ADVANCED)
                .build(writer);
        ExtendedShapelessRecipeBuilder.shapelessRecipe(MSItems.BONE_ASHES, 4)
                .addIngredient(MSItems.BONE_ASHES, 4)
                .addIngredient(ItemTags.COALS)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.EXTRACTING_PLANT_CASING)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(' ', 'S', ' '),
                        TripleLine.of('S', 'X', 'S'),
                        TripleLine.of(' ', 'S', ' ')))
                .key('S', MSItems.PTFE_SHEET)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.EXTRACTING_PILLAR, 3)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('P', 'C', 'P'),
                        TripleLine.of('P', 'T', 'P'),
                        TripleLine.of('P', 'A', 'P')
                ))
                .key('P', MSBlocks.EXTRACTING_PLANT_CASING)
                .key('C', MekanismTags.Items.CIRCUITS_ADVANCED)
                .key('T', MekanismBlocks.BASIC_CHEMICAL_TANK)
                .key('A', MekanismTags.Items.ALLOYS_INFUSED)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.EXTRACTING_PLANT_PORT, 2)
                .pattern(RecipePattern.createPattern(TripleLine.of('C', 'A', 'C')))
                .key('C', MSBlocks.EXTRACTING_PLANT_CASING)
                .key('A', MekanismTags.Items.CIRCUITS_ADVANCED)
                .build(writer);
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.IRRADIATOR)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('L', 'S', 'L'),
                        TripleLine.of('C', 'X', 'C'),
                        TripleLine.of('L', 'P', 'L')))
                .key('L', ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "storage_blocks/lead")))
                .key('S', MekanismBlocks.LASER)
                .key('C', MekanismTags.Items.CIRCUITS_ULTIMATE)
                .key('P', MekanismItems.POLONIUM_PELLET)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('S', 'C', 'S'),
                        TripleLine.of('W', 'X', 'W'),
                        TripleLine.of('S', 'C', 'S')))
                .key('S', MSItems.PTFE_SHEET)
                .key('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .key('W', MekanismBlocks.ULTIMATE_UNIVERSAL_CABLE)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(' ', 'C', ' '),
                        TripleLine.of('C', 'R', 'C'),
                        TripleLine.of(' ', 'C', ' ')))
                .key('C', MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING)
                .key('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .build(writer);
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT, 2)
                .pattern(RecipePattern.createPattern(TripleLine.of('C', 'U', 'C')))
                .key('C', MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING)
                .key('U', MekanismTags.Items.CIRCUITS_ULTIMATE)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MSBlocks.METAL_ELECTROLYZING_ROD, 3)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('S', 'S', 'S'),
                        TripleLine.of('C', 'O', 'C'),
                        TripleLine.of('S', 'S', 'S')))
                .key('S', MSItems.PTFE_SHEET)
                .key('C', MekanismBlocks.ULTIMATE_UNIVERSAL_CABLE)
                .key('O', MekanismItems.ELECTROLYTIC_CORE)
                .build(writer);
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('S', 'S', 'S'),
                        TripleLine.of('E', 'P', 'E'),
                        TripleLine.of('S', 'S', 'S')))
                .key('S', MekanismTags.Items.INGOTS_STEEL)
                .key('E', MekanismTags.Items.CIRCUITS_ELITE)
                .key('P', MekanismBlocks.PRESSURIZED_REACTION_CHAMBER)
                .build(writer);
        MekDataShapedRecipeBuilder.shapedRecipe(MSBlocks.SEAWATER_PUMP)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('S', 'B', 'S'),
                        TripleLine.of('E', 'X', 'E'),
                        TripleLine.of('S', 'P', 'S')))
                .key('S', MekanismTags.Items.INGOTS_STEEL)
                .key('B', Items.BUCKET)
                .key('E', MekanismTags.Items.CIRCUITS_ELITE)
                .key('P', MekanismBlocks.ELECTRIC_PUMP)
                .key('X', MekanismBlocks.STEEL_CASING)
                .build(writer);
        ExtendedShapedRecipeBuilder.shapedRecipe(MekanismBlocks.STRUCTURAL_GLASS, 8)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of('G', 'G', 'G'),
                        TripleLine.of('G', 'C', 'G'),
                        TripleLine.of('G', 'G', 'G')))
                .key('G', Tags.Items.GLASS)
                .key('C', MSItems.DUST_CALCIUM_OXIDE)
                .build(writer);
    }

    private void dissolution(Consumer<FinishedRecipe> writer) {
        String basePath = "dissolution/";
        ChemicalDissolutionRecipeBuilder.dissolution(
                item().from(Tags.Items.ORES_EMERALD, 1),
                gas().from(MekanismGases.SULFURIC_ACID, 100),
                MSGases.BERYLLIUM.getStack(100)
        ).build(writer, rl(basePath + "beryllium"));
        ChemicalDissolutionRecipeBuilder.dissolution(
                item().from(Tags.Items.GUNPOWDER, 1),
                gas().from(MekanismGases.SULFURIC_ACID, 100),
                MSGases.POTASSIUM_NITRATE.getStack(200)
        );
    }

    private void enriching(Consumer<FinishedRecipe> writer) {
        String basePath = "enriching/";
        ItemStackToItemStackRecipeBuilder.enriching(
                item().from(MSItems.PTFE_PELLET, 4),
                MSItems.PTFE_SHEET.getItemStack(1)
        ).build(writer, rl(basePath + "ptfe_sheet"));
    }

    private void evaporating(Consumer<FinishedRecipe> writer) {
        String basePath = "evaporating/";
        FluidToFluidRecipeBuilder.evaporating(
                fluid().from(MSFluids.SEAWATER, 10),
                MSFluids.CONCENTRATED_SEAWATER.getFluidStack(1)
        ).build(writer, rl(basePath + "concentrated_seawater"));
        FluidToFluidRecipeBuilder.evaporating(
                fluid().from(MSFluids.BUTYRALDEHYDE_MIXTURE, 2),
                MSFluids.N_BUTYRALDEHYDE.getFluidStack(1)
        ).build(writer, rl(basePath + "n_butyraldehyde"));
        FluidToFluidRecipeBuilder.evaporating(
                fluid().from(MSFluids.IMPURE_PHOSPHORYL_CHLORIDE, 1),
                MSFluids.PHOSPHORYL_CHLORIDE.getFluidStack(1)
        ).build(writer, rl(basePath + "phosphoryl_chloride"));
    }

    private void injecting(Consumer<FinishedRecipe> writer) {
        String basePath = "injecting";
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                item().from(MekanismItems.SUBSTRATE, 1),
                gas().from(MekanismGases.SULFURIC_ACID, 25),
                MSItems.ACIDIC_SUBSTRATE.getItemStack(1)
        ).build(writer, rl(basePath + "acidic_substrate"));
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                item().from(MekanismItems.SUBSTRATE, 1),
                gas().from(MSGases.SODIUM_HYDROXIDE, 25),
                MSItems.ALKALINE_SUBSTRATE.getItemStack(1)
        ).build(writer, rl(basePath + "alkaline_substrate"));
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                item().from(MSItems.EXCIPIENT, 1),
                gas().from(MSGases.CHLOROMETHANE, 20),
                MSItems.TABLET_ANESTHETIC.getItemStack(1)
        ).build(writer, rl(basePath + "tablet_anesthetic"));
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                item().from(MSItems.EXCIPIENT, 1),
                gas().from(MSGases.BROMINE, 20),
                MSItems.TABLET_FIRE_RESISTANCE.getItemStack(1)
        ).build(writer, rl(basePath + "tablet_fire_resistance"));
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                item().from(MSItems.EXCIPIENT, 1),
                gas().from(MSGases.IODINE, 20),
                MSItems.TABLET_IODINE.getItemStack(1)
        ).build(writer, rl(basePath + "tablet_iodine"));
    }

    private void irradiating(Consumer<FinishedRecipe> writer) {
        String basePath = "irradiating/";
        IrradiatingRecipeBuilder.irradiating(
                item().from(MSItems.NEUTRON_SOURCE_PELLET, 1),
                gas().from(MekanismGases.NUCLEAR_WASTE, 200),
                BoxedChemicalStack.box(MekanismGases.POLONIUM.getStack(200))
        ).build(writer, rl(basePath + "polonium"));
        IrradiatingRecipeBuilder.irradiating(
                item().from(MSItems.NEUTRON_SOURCE_PELLET, 2),
                gas().from(MekanismGases.URANIUM_HEXAFLUORIDE, 200),
                BoxedChemicalStack.box(MekanismGases.NUCLEAR_WASTE.getStack(200))
        ).build(writer, rl(basePath + "nuclear_waste"));
        IrradiatingRecipeBuilder.irradiating(
                item().from(MSItems.NEUTRON_SOURCE_PELLET, 3),
                gas().from(MekanismGases.URANIUM_HEXAFLUORIDE, 200),
                BoxedChemicalStack.box(MekanismGases.PLUTONIUM.getStack(200))
        ).build(writer, rl(basePath + "plutonium"));
        IrradiatingRecipeBuilder.irradiating(
                item().from(MSItems.NEUTRON_SOURCE_PELLET, 1),
                gas().from(MekanismGases.HYDROGEN, 1000),
                BoxedChemicalStack.box(GeneratorsGases.DEUTERIUM.getStack(1000))
        ).build(writer, rl(basePath + "deuterium"));
        IrradiatingRecipeBuilder.irradiating(
                item().from(MSItems.NEUTRON_SOURCE_PELLET, 2),
                gas().from(MekanismGases.LITHIUM, 1000),
                BoxedChemicalStack.box(GeneratorsGases.TRITIUM.getStack(1000))
        ).build(writer, rl(basePath + "tritium"));
        IrradiatingRecipeBuilder.irradiating(
                item().from(MSItems.HIGH_DENSITY_NEUTRON_SOURCE_PELLET, 1),
                gas().from(MekanismGases.PLUTONIUM, 1000),
                BoxedChemicalStack.box(MSGases.CALIFORNIUM.getStack(500))
        ).build(writer, rl(basePath + "californium"));
    }

    private void nucleosynthesizing(Consumer<FinishedRecipe> writer) {
        String basePath = "nucleosynthesizing";
        NucleosynthesizingRecipeBuilder.nucleosynthesizing(
                item().from(MSItems.NEUTRON_SOURCE_PELLET, 3),
                gas().from(MekanismGases.ANTIMATTER, 2),
                MSItems.HIGH_DENSITY_NEUTRON_SOURCE_PELLET.getItemStack(2),
                1_000
        ).build(writer, rl(basePath + "high_density_neutron_source_pellet"));
    }

    private void oxidizing(Consumer<FinishedRecipe> writer) {
        String basePath = "oxidizing/";
        ItemStackToChemicalRecipeBuilder.oxidizing(
                item().from(MekanismItems.BIO_FUEL, 1),
                MSGases.METHANOL.getStack(100)
        ).build(writer, rl(basePath + "methanol"));
    }

    private void polymerizing(Consumer<FinishedRecipe> writer) {
        String basePath = "polymerizing/";
        MSGasToGasRecipeBuilder.polymerizing(
                gas().from(MekanismGases.ETHENE, 2),
                MSGases._2_BUTENE.getStack(1)
        ).build(writer, rl(basePath + "2_butene"));
        MSGasToGasRecipeBuilder.polymerizing(
                gas().from(MSGases.AMMONIA, 2),
                MSGases.HYDRAZINE.getStack(1)
        ).build(writer, rl(basePath + "hydrazine"));
        MSGasToGasRecipeBuilder.polymerizing(
                gas().from(MSGases.TETRAFLUOROETHYLENE, 64),
                MSGases.PTFE.getStack(1)
        ).build(writer, rl(basePath + "ptfe"));
    }

    private void processing(Consumer<FinishedRecipe> writer) {
        String basePath = "processing/";
        // Copper
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.ORES_COPPER, 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.COPPER).getFluidStack(1_000)
        ).build(writer, rl(basePath + "copper/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.RAW_MATERIALS_COPPER, 3),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.COPPER).getFluidStack(1_000)
        ).build(writer, rl(basePath + "copper/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.COPPER), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.COPPER).getFluidStack(15)
        ).build(writer, rl(basePath + "copper/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.COPPER), 15),
                gas().from(MekanismGases.SULFURIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.COPPER).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "copper/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.COPPER), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.COPPER).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "copper/electrolysis"));
        // Gold
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.ORES_GOLD, 1),
                gas().from(MSGases.AQUA_REGIA, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.GOLD).getFluidStack(1_000)
        ).build(writer, rl(basePath + "gold/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.RAW_MATERIALS_GOLD, 3),
                gas().from(MSGases.AQUA_REGIA, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.GOLD).getFluidStack(1_000)
        ).build(writer, rl(basePath + "gold/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.GOLD), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.GOLD).getFluidStack(15)
        ).build(writer, rl(basePath + "gold/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.GOLD), 15),
                gas().from(MSGases.AQUA_REGIA, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.GOLD).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "gold/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.GOLD), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.GOLD).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "gold/electrolysis"));
        // Iron
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.ORES_IRON, 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.IRON).getFluidStack(1_000)
        ).build(writer, rl(basePath + "iron/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.RAW_MATERIALS_IRON, 3),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.IRON).getFluidStack(1_000)
        ).build(writer, rl(basePath + "iron/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.IRON), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.IRON).getFluidStack(15)
        ).build(writer, rl(basePath + "iron/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.IRON), 15),
                gas().from(MekanismGases.SULFURIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.IRON).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "iron/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.IRON), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.IRON).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "iron/electrolysis"));
        // Lead
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.ORES.get(OreType.LEAD), 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.LEAD).getFluidStack(1_000)
        ).build(writer, rl(basePath + "lead/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.LEAD), 3),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.LEAD).getFluidStack(1_000)
        ).build(writer, rl(basePath + "lead/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.LEAD), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.LEAD).getFluidStack(15)
        ).build(writer, rl(basePath + "lead/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.LEAD), 15),
                gas().from(MekanismGases.SULFURIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.LEAD).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "lead/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.LEAD), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.LEAD).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "lead/electrolysis"));
        // Netherite
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.ORES_NETHERITE_SCRAP, 1),
                gas().from(MSGases.NITRIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(MiscResource.NETHERITE).getFluidStack(1_000)
        ).build(writer, rl(basePath + "netherite/leachate_from_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(MiscResource.NETHERITE), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(MiscResource.NETHERITE).getFluidStack(15)
        ).build(writer, rl(basePath + "netherite/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(MiscResource.NETHERITE), 15),
                gas().from(MSGases.NITRIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(MiscResource.NETHERITE).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "netherite/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(MiscResource.NETHERITE), 10),
                MekanismItems.NETHERITE_DUST.getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "netherite/electrolysis"));
        // Osmium
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.ORES.get(OreType.OSMIUM), 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.OSMIUM).getFluidStack(1_000)
        ).build(writer, rl(basePath + "osmium/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.OSMIUM), 3),
                gas().from(MSGases.POTASSIUM_NITRATE, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.OSMIUM).getFluidStack(1_000)
        ).build(writer, rl(basePath + "osmium/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.OSMIUM), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.OSMIUM).getFluidStack(15)
        ).build(writer, rl(basePath + "osmium/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.OSMIUM), 15),
                gas().from(MSGases.POTASSIUM_NITRATE, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.OSMIUM).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "osmium/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.OSMIUM), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.OSMIUM).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "osmium/electrolysis"));
        // Redstone
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(Tags.Items.ORES_REDSTONE, 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(MiscResource.REDSTONE).getFluidStack(1_000)
        ).build(writer, rl(basePath + "redstone/leachate_from_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(MiscResource.REDSTONE), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(MiscResource.REDSTONE).getFluidStack(15)
        ).build(writer, rl(basePath + "redstone/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(MiscResource.REDSTONE), 15),
                gas().from(MekanismGases.SULFURIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(MiscResource.REDSTONE).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "redstone/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(MiscResource.REDSTONE), 10),
                new ItemStack(Items.REDSTONE, 1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "redstone/electrolysis"));
        // Tin
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.ORES.get(OreType.TIN), 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.TIN).getFluidStack(1_000)
        ).build(writer, rl(basePath + "tin/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.TIN), 3),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.TIN).getFluidStack(1_000)
        ).build(writer, rl(basePath + "tin/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.TIN), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.TIN).getFluidStack(15)
        ).build(writer, rl(basePath + "tin/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.TIN), 15),
                gas().from(MekanismGases.SULFURIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.TIN).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "tin/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.TIN), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.TIN).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "tin/electrolysis"));
        // Uranium
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.ORES.get(OreType.URANIUM), 1),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.URANIUM).getFluidStack(1_000)
        ).build(writer, rl(basePath + "uranium/leachate_from_ore"));
        ItemStackChemicalToFluidRecipeBuilder.acidLeaching(
                item().from(MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.URANIUM), 3),
                gas().from(MekanismGases.SULFURIC_ACID, 1_000),
                MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.URANIUM).getFluidStack(1_000)
        ).build(writer, rl(basePath + "uranium/leachate_from_raw_ore"));
        FluidChemicalToFluidRecipeBuilder.extraction(
                fluid().from(MSFluids.PROCESSED_LEACHATE_RESOURCES.get(PrimaryResource.URANIUM), 10),
                gas().from(MSGases.P204, 5),
                MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.URANIUM).getFluidStack(15)
        ).build(writer, rl(basePath + "uranium/extraction"));
        FluidChemicalToFluidChemicalRecipeBuilder.antiExtraction(
                fluid().from(MSFluids.PROCESSED_EXTRACT_RESOURCES.get(PrimaryResource.URANIUM), 15),
                gas().from(MekanismGases.SULFURIC_ACID, 5),
                MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.URANIUM).getFluidStack(5),
                MSGases.P204.getStack(10)
        ).build(writer, rl(basePath + "uranium/anti_extraction"));
        MetalElectrolysisRecipeBuilder.metalElectrolysis(
                fluid().from(MSFluids.PROCESSED_CONCENTRATE_RESOURCES.get(PrimaryResource.URANIUM), 10),
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.URANIUM).getItemStack(1),
                FloatingLong.createConst(250)
        ).build(writer, rl(basePath + "uranium/electrolysis"));
    }

    private void reaction(Consumer<FinishedRecipe> writer) {
        String basePath = "reaction/";
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(MSItems.ALKALINE_SUBSTRATE, 1),
                        fluid().from(FluidTags.WATER, 50),
                        gas().from(MSGases.N_BUTYRALDEHYDE, 100),
                        100,
                        MekanismItems.SUBSTRATE.getItemStack(1),
                        MSGases._2_ETHYL_2_HEXENAL.getStack(50))
                .energyRequired(FloatingLong.createConst(300))
                .build(writer, rl(basePath + "2_ethyl_2_hexenal"));
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(MSItems.BONE_ASHES_WITH_CARBON),
                        fluid().from(FluidStack.EMPTY),
                        gas().from(MekanismGases.CHLORINE, 600),
                        200,
                        MSItems.DUST_CALCIUM_CHLORIDE.getItemStack(3),
                        MSGases.IMPURE_PHOSPHORYL_CHLORIDE.getStack(200))
                .energyRequired(FloatingLong.createConst(750))
                .build(writer, rl(basePath + "impure_phosphoryl_chloride"));
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.LEAD), 1),
                        fluid().from(MekanismTags.Fluids.HYDROFLUORIC_ACID, 1_000),
                        gas().from(MSGases.CALIFORNIUM, 1_000),
                        50,
                        MSItems.REFINED_CALIFORNIUM_INGOT.getItemStack(1),
                        MekanismGases.SPENT_NUCLEAR_WASTE.getStack(1_000))
                .build(writer, rl(basePath + "refined_californium_ingot"));
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(MSItems.ACIDIC_SUBSTRATE, 1),
                        fluid().from(FluidTags.WATER, 50),
                        gas().from(MSGases.BUTYRALDEHYDE_MIXTURE, 50),
                        100,
                        MekanismItems.SUBSTRATE.getItemStack(1),
                        MSGases.ISOBUTYRALDEHYDE.getStack(100))
                .energyRequired(FloatingLong.createConst(400))
                .build(writer, rl(basePath + "isobutyraldehyde"));
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(MekanismItems.YELLOW_CAKE_URANIUM, 2),
                        fluid().from(MSFluids.BERYLLIUM, 50),
                        gas().from(MSGases.CALIFORNIUM, 10),
                        50,
                        MSItems.NEUTRON_SOURCE_PELLET.getItemStack(1))
                .build(writer, rl(basePath + "neutron_source_pellet"));
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(MekanismItems.SUBSTRATE, 1),
                        fluid().from(FluidTags.WATER, 100),
                        gas().from(MSGases.PTFE, 50),
                        100,
                        MSItems.PTFE_PELLET.getItemStack(1))
                .energyRequired(FloatingLong.createConst(1_500))
                .build(writer, rl(basePath + "ptfe_pellet"));
        PressurizedReactionRecipeBuilder.reaction(
                        item().from(ItemTags.COALS, 1),
                        fluid().from(FluidStack.EMPTY),
                        gas().from(MekanismGases.WATER_VAPOR, 100),
                        100,
                        MSGases.WATER_GAS.getStack(100))
                .build(writer, rl(basePath + "water_gas"));
    }

    private void rotary(Consumer<FinishedRecipe> writer) {
        bidirectionalRotary(writer, MSGases._2_BUTENE, MSFluids._2_BUTENE);
        bidirectionalRotary(writer, MSGases._2_ETHYL_2_HEXENAL, MSFluids._2_ETHYL_2_HEXENAL);
        bidirectionalRotary(writer, MSGases.AMMONIA, MSFluids.AMMONIA);
        bidirectionalRotary(writer, MSGases.AQUA_REGIA, MSFluids.AQUA_REGIA);
        bidirectionalRotary(writer, MSGases.BERYLLIUM, MSFluids.BERYLLIUM);
        bidirectionalRotary(writer, MSGases.BROMINE, MSFluids.BROMINE);
        bidirectionalRotary(writer, MSGases.BUTYRALDEHYDE_MIXTURE, MSFluids.BUTYRALDEHYDE_MIXTURE);
        bidirectionalRotary(writer, MSGases.CHLOROMETHANE, MSFluids.CHLOROMETHANE);
        bidirectionalRotary(writer, MSGases.COMPRESSED_AIR, MSFluids.COMPRESSED_AIR);
        bidirectionalRotary(writer, MSGases.CONCENTRATED_SEAWATER, MSFluids.CONCENTRATED_SEAWATER);
        bidirectionalRotary(writer, MSGases.ETHANOL, MSFluids.ETHANOL);
        bidirectionalRotary(writer, MSGases.HELIUM, MSFluids.HELIUM);
        bidirectionalRotary(writer, MSGases.HYDRAZINE, MSFluids.HYDRAZINE);
        bidirectionalRotary(writer, MSGases.IMPURE_PHOSPHORYL_CHLORIDE, MSFluids.IMPURE_PHOSPHORYL_CHLORIDE);
        bidirectionalRotary(writer, MSGases.IODINE, MSFluids.IODINE);
        bidirectionalRotary(writer, MSGases.ISOBUTYRALDEHYDE, MSFluids.ISOBUTYRALDEHYDE);
        bidirectionalRotary(writer, MSGases.ISOOCTANOL, MSFluids.ISOOCTANOL);
        bidirectionalRotary(writer, MSGases.LACTOSE, MSFluids.LACTOSE);
        bidirectionalRotary(writer, MSGases.METHANOL, MSFluids.METHANOL);
        bidirectionalRotary(writer, MSGases.METHYLAMINE, MSFluids.METHYLAMINE);
        bidirectionalRotary(writer, MSGases.N_BUTYRALDEHYDE, MSFluids.N_BUTYRALDEHYDE);
        bidirectionalRotary(writer, MSGases.NITRIC_ACID, MSFluids.NITRIC_ACID);
        bidirectionalRotary(writer, MSGases.NITRIC_OXIDE, MSFluids.NITRIC_OXIDE);
        bidirectionalRotary(writer, MSGases.NITROGEN, MSFluids.NITROGEN);
        bidirectionalRotary(writer, MSGases.NITROGEN_DIOXIDE, MSFluids.NITROGEN_DIOXIDE);
        bidirectionalRotary(writer, MSGases.P204, MSFluids.P204);
        bidirectionalRotary(writer, MSGases.PHOSPHORYL_CHLORIDE, MSFluids.PHOSPHORYL_CHLORIDE);
        bidirectionalRotary(writer, MSGases.POTASSIUM_NITRATE, MSFluids.POTASSIUM_NITRATE);
        bidirectionalRotary(writer, MSGases.PROPYLENE, MSFluids.PROPYLENE);
        bidirectionalRotary(writer, MSGases.PTFE, MSFluids.PTFE);
        bidirectionalRotary(writer, MSGases.SEAWATER, MSFluids.SEAWATER);
        bidirectionalRotary(writer, MSGases.SODIUM_HYDROXIDE, MSFluids.SODIUM_HYDROXIDE);
        bidirectionalRotary(writer, MSGases.STRONTIUM, MSFluids.STRONTIUM);
        bidirectionalRotary(writer, MSGases.SUPERHEATED_HELIUM, MSFluids.SUPERHEATED_HELIUM);
        bidirectionalRotary(writer, MSGases.TETRAFLUOROETHYLENE, MSFluids.TETRAFLUOROETHYLENE);
        bidirectionalRotary(writer, MSGases.WHEY, MSFluids.WHEY);
        bidirectionalRotary(writer, MSGases.YTTRIUM, MSFluids.YTTRIUM);
        RotaryRecipeBuilder.rotary(
                fluid().from(Tags.Fluids.MILK, 2),
                MSGases.WHEY.getStack(1)
        ).build(writer, rl("rotary/whey_from_milk"));
    }

    private void separating(Consumer<FinishedRecipe> writer) {
        String basePath = "separating/";
        ElectrolysisRecipeBuilder.separating(
                fluid().from(MSFluids.SEAWATER, 2),
                MSGases.SODIUM_HYDROXIDE.getStack(1),
                MekanismGases.CHLORINE.getStack(1)
        ).build(writer, rl(basePath + "seawater"));
    }

    private void smelting(Consumer<FinishedRecipe> writer) {
        String basePath = "smelting/";
        ItemStackToItemStackRecipeBuilder.smelting(
                item().from(Tags.Items.BONES),
                MSItems.BONE_ASHES.getItemStack(4)
        ).build(writer, rl(basePath + "bone_ashes"));
    }

    private void bidirectionalRotary(Consumer<FinishedRecipe> writer, GasRegistryObject<Gas> gas, FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> fluid) {
        String basePath = "rotary";
        RotaryRecipeBuilder.rotary(
                fluid().from(fluid, 1),
                gas().from(gas, 1),
                gas.getStack(1),
                fluid.getFluidStack(1)
        ).build(writer, rl(basePath + gas.getName()));
    }
}
