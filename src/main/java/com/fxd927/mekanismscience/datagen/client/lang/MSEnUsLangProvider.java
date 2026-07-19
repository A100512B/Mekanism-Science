package com.fxd927.mekanismscience.datagen.client.lang;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.registries.*;
import net.minecraft.data.PackOutput;

public class MSEnUsLangProvider extends MSBaseLangProvider {

    public MSEnUsLangProvider(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItems();
        addBlocks();
        addFluids();
        addGases();
        addSubtitles();
        addMisc();
    }

    private void addItems() {
        add(MSItems.ACIDIC_SUBSTRATE, "Acidic Substrate");
        add(MSItems.ALKALINE_SUBSTRATE, "Alkaline Substrate");
        add(MSItems.BONE_ASHES, "Bone Ashes");
        add(MSItems.BONE_ASHES_WITH_CARBON, "Bone Ashes With Carbon");
        add(MSItems.DUST_CALCIUM_CHLORIDE, "Calcium Chloride");
        add(MSItems.DUST_CALCIUM_OXIDE, "Calcium Oxide Dust");
        add(MSItems.EXCIPIENT, "Excipient");
        add(MSItems.HIGH_DENSITY_NEUTRON_SOURCE_PELLET, "High Density Neutron Source Pellet");
        add(MSItems.NEUTRON_SOURCE_PELLET, "Neutron Source Pellet");
        add(MSItems.PTFE_PELLET, "PTFE Pellet");
        add(MSItems.PTFE_SHEET, "PTFE Sheet");
        add(MSItems.REFINED_CALIFORNIUM_INGOT, "Refined Californium Ingot");
        add(MSItems.TABLET_ANESTHETIC, "Anesthetic Tablet");
        add(MSItems.TABLET_FIRE_RESISTANCE, "Fire Resistance Tablet");
        add(MSItems.TABLET_IODINE, "Iodine Tablet");
        add(MSItems.TABLET_MUSCLE_ENHANCEMENT, "Muscle Enhancement Tablet (WIP)");
        add(MSItems.TABLET_POISON, "Tablet Poison (WIP)");
        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO.getBucket(), capitalize(resource.getRegistrySuffix()) + " Concentrate Bucket"));
        MSFluids.PROCESSED_EXTRACT_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO.getBucket(), capitalize(resource.getRegistrySuffix()) + " P204-Extract Bucket"));
        MSFluids.PROCESSED_LEACHATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO.getBucket(), capitalize(resource.getRegistrySuffix()) + " Leachate Bucket"));
    }

    private void addBlocks() {
        add(MSBlocks.ACID_LEACHER, "Acid Leacher");
        add(MSBlocks.ADSORPTION_SEPARATOR, "Adsorption Separator");
        add(MSBlocks.AIR_COMPRESSOR, "Air Compressor");
        add(MSBlocks.ANTI_EXTRACTING_PILLAR, "Anti-Extracting Pillar");
        add(MSBlocks.ANTI_EXTRACTING_PLANT_CASING, "Anti-Extracting Plant Casing");
        add(MSBlocks.ANTI_EXTRACTING_PLANT_PORT, "Anti-Extracting Plant Port");
        add(MSBlocks.EXTRACTING_PILLAR, "Extracting Pillar");
        add(MSBlocks.EXTRACTING_PLANT_CASING, "Extracting Plant Casing");
        add(MSBlocks.EXTRACTING_PLANT_PORT, "Extracting Plant Port");
        add(MSBlocks.IRRADIATOR, "Irradiator");
        add(MSBlocks.METAL_ELECTROLYSIS_CHAMBER, "Metal Electrolysis Chamber");
        add(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER, "Pressurized Polymerizing Chamber");
        add(MSBlocks.SEAWATER_PUMP, "Seawater Pump");
    }

    private void addFluids() {
        addFluid(MSFluids._2_BUTENE, "Liquid 2-Butene");
        addFluid(MSFluids._2_ETHYL_2_HEXENAL, "Liquid 2-Ethyl-2-Hexenal");
        addFluid(MSFluids.AMMONIA, "Liquid Ammonia");
        addFluid(MSFluids.AQUA_REGIA, "Liquid Aqua Regia");
        addFluid(MSFluids.BERYLLIUM, "Liquid Beryllium");
        addFluid(MSFluids.BROMINE, "Liquid Bromine");
        addFluid(MSFluids.BUTYRALDEHYDE_MIXTURE, "Liquid Butyraldehyde Mixture");
        addFluid(MSFluids.CHLOROMETHANE, "Liquid Chloromethane");
        addFluid(MSFluids.COMPRESSED_AIR, "Liquid Compressed Air");
        addFluid(MSFluids.CONCENTRATED_SEAWATER, "Concentrated Seawater");
        addFluid(MSFluids.ETHANOL, "Liquid Ethanol");
        addFluid(MSFluids.HELIUM, "Liquid Helium");
        addFluid(MSFluids.HYDRAZINE, "Liquid Hydrazine");
        addFluid(MSFluids.IMPURE_PHOSPHORYL_CHLORIDE, "Impure Liquid Phosphoryl Chloride");
        addFluid(MSFluids.IODINE, "Liquid Iodine");
        addFluid(MSFluids.ISOBUTYRALDEHYDE, "Liquid Isobutyraldehyde");
        addFluid(MSFluids.ISOOCTANOL, "Liquid Isooctanol");
        addFluid(MSFluids.LACTOSE, "Liquid Lactose");
        addFluid(MSFluids.METHANOL, "Liquid Methanol");
        addFluid(MSFluids.METHYLAMINE, "Liquid Methylamine");
        addFluid(MSFluids.MILK, "Milk");
        addFluid(MSFluids.N_BUTYRALDEHYDE, "Liquid n-Butyraldehyde");
        addFluid(MSFluids.NITRIC_ACID, "Liquid Nitric Acid");
        addFluid(MSFluids.NITRIC_OXIDE, "Liquid Nitric Oxide");
        addFluid(MSFluids.NITROGEN, "Liquid Nitrogen");
        addFluid(MSFluids.NITROGEN_DIOXIDE, "Liquid Nitrogen Dioxide");
        addFluid(MSFluids.P204, "Liquid P204");
        addFluid(MSFluids.PHOSPHORYL_CHLORIDE, "Liquid Phosphoryl Chloride");
        addFluid(MSFluids.PROPYLENE, "Liquid Propylene");
        addFluid(MSFluids.PTFE, "Liquid PTFE");
        addFluid(MSFluids.SEAWATER, "Seawater");
        addFluid(MSFluids.SODIUM_HYDROXIDE, "Liquid Sodium Hydroxide");
        addFluid(MSFluids.STRONTIUM, "Liquid Strontium");
        addFluid(MSFluids.SUPERHEATED_HELIUM, "Liquid Superheated Helium");
        addFluid(MSFluids.TETRAFLUOROETHYLENE, "Liquid Tetrafluoroethylene");
        addFluid(MSFluids.WATER_GAS, "Liquid Water Gas");
        addFluid(MSFluids.WHEY, "Liquid Whey");
        addFluid(MSFluids.YTTRIUM, "Liquid Yttrium");
        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO, capitalize(resource.getRegistrySuffix()) + " Concentrate"));
        MSFluids.PROCESSED_EXTRACT_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO, capitalize(resource.getRegistrySuffix()) + " P204 Extract"));
        MSFluids.PROCESSED_LEACHATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO, capitalize(resource.getRegistrySuffix()) + " Leachate"));
    }

    private void addGases() {
        add(MSGases._2_BUTENE, "2-Butene");
        add(MSGases._2_ETHYL_2_HEXENAL, "2-Ethyl-2-Hexenal");
        add(MSGases.AMERICIUM, "Americium (WIP)");
        add(MSGases.AMMONIA, "Ammonia");
        add(MSGases.AQUA_REGIA, "Aqua Regia");
        add(MSGases.BERYLLIUM, "Beryllium");
        add(MSGases.BROMINE, "Bromine");
        add(MSGases.BUTYRALDEHYDE_MIXTURE, "Butyraldehyde Mixture");
        add(MSGases.CALIFORNIUM, "Californium");
        add(MSGases.CHLOROMETHANE, "Chloromethane");
        add(MSGases.COMPRESSED_AIR, "Compressed Air");
        add(MSGases.CONCENTRATED_SEAWATER, "Concentrated Seawater");
        add(MSGases.ETHANOL, "Ethanol");
        add(MSGases.HELIUM, "Helium");
        add(MSGases.HYDRAZINE, "Hydrazine");
        add(MSGases.IMPURE_PHOSPHORYL_CHLORIDE, "Impure Phosphoryl Chloride");
        add(MSGases.IODINE, "Iodine");
        add(MSGases.ISOBUTYRALDEHYDE, "Isobutyraldehyde");
        add(MSGases.ISOOCTANOL, "Isooctanol");
        add(MSGases.LACTOSE, "Lactose");
        add(MSGases.METHANOL, "Methanol");
        add(MSGases.METHYLAMINE, "Methylamine");
        add(MSGases.N_BUTYRALDEHYDE, "n-Butyraldehyde");
        add(MSGases.NITRIC_ACID, "Nitric Acid");
        add(MSGases.NITRIC_OXIDE, "Nitric Oxide");
        add(MSGases.NITROGEN, "Nitrogen");
        add(MSGases.NITROGEN_DIOXIDE, "Nitrogen Dioxide");
        add(MSGases.P204, "P204");
        add(MSGases.PHOSPHORYL_CHLORIDE, "Phosphoryl Chloride");
        add(MSGases.PROPYLENE, "Propylene");
        add(MSGases.PTFE, "PTFE");
        add(MSGases.SEAWATER, "Seawater");
        add(MSGases.SODIUM_HYDROXIDE, "Sodium Hydroxide");
        add(MSGases.STRONTIUM, "Strontium (WIP)");
        add(MSGases.SUPERHEATED_HELIUM, "Superheated Helium");
        add(MSGases.TETRAFLUOROETHYLENE, "Tetrafluoroethylene");
        add(MSGases.WATER_GAS, "Water Gas");
        add(MSGases.WHEY, "Whey");
        add(MSGases.YTTRIUM, "Yttrium (WIP)");
    }

    private void addSubtitles() {
        add(MSSounds.ACID_LEACHER, "Acid Leacher processes");
        add(MSSounds.AIR_COMPRESSOR, "Air Compressor hums");
        add(MSSounds.ANTI_EXTRACTING_PLANT, "Liquid inside Anti-Extracting Plant churns");
        add(MSSounds.EXTRACTING_PLANT, "Liquid inside Extracting Plant churns");
        add(MSSounds.METAL_ELECTROLYSIS_CHAMBER, "Metal Electrolysis Chamber buzzes");
        add(MSSounds.PRESSURIZED_POLYMERIZING_CHAMBER, "Pressurized Polymerizing Chamber hums");
    }

    public void addMisc() {
        // Mod Name
        add(MSLang.MEKANISM_SCIENCE, "Mekanism: Science");
        // Descriptions
        add(MSLang.DESCRIPTION_ACID_LEACHER, "A giant and chemical-inert machine that can safely contain tons of dangerous acids and use them to leach most metals.");
        add(MSLang.DESCRIPTION_ADSORPTION_SEPARATOR, "A simple machine used to extract certain substances with specific adsorbents.");
        add(MSLang.DESCRIPTION_AIR_COMPRESSOR, "A well-sealed machine that pumps in ambient air and safely compresses it into a high-pressure state.");
        add(MSLang.DESCRIPTION_ANTI_EXTRACTING_PILLAR, "A chemical-inert pillar holding acids to anti-extract metal ions from the extract.");
        add(MSLang.DESCRIPTION_ANTI_EXTRACTING_PLANT_CASING, "A chemical-inert casing used in the structure of Anti-Extracting Plants, securing your base from being eroded by the acids.");
        add(MSLang.DESCRIPTION_ANTI_EXTRACTING_PLANT_PORT, "A chemical-inert valve that can be placed on an Anti-Extracting Plant multiblock to allow fluids and chemicals to flow efficiently.");
        add(MSLang.DESCRIPTION_EXTRACTING_PILLAR, "A chemical-inert pillar holding extractants to extract and enrich metal ions from the leachate.");
        add(MSLang.DESCRIPTION_EXTRACTING_PLANT_CASING, "A chemical-inert casing used in the structure of Extracting Plants, securing your base from being eroded by the acids.");
        add(MSLang.DESCRIPTION_EXTRACTING_PLANT_PORT, "A chemical-inert valve that can be placed on an Extracting Plant multiblock to allow fluids and chemicals to flow efficiently.");
        add(MSLang.DESCRIPTION_IRRADIATOR, "A machine used to efficiently irradiate without any worry about nuclear leaks.");
        add(MSLang.DESCRIPTION_METAL_ELECTROLYSIS_CHAMBER, "A machine used to efficiently electrolyze solutions to get highly pure metals.");
        add(MSLang.DESCRIPTION_PRESSURIZED_POLYMERIZING_CHAMBER, "An advanced machine that polymerizes organic chemicals at a very high pressure.");
        add(MSLang.DESCRIPTION_SEAWATER_PUMP, "A pump specifically made for extracting seawater from Ocean biomes.");
        // Extracting Plant
        add(MSLang.EXTRACTING_PLANT, "Extracting Plant");
        add(MSLang.EXTRACTING_PLANT_INVALID_EVEN_LENGTH, "Couldn't form, width and length of structure must be odd.");
        add(MSLang.EXTRACTING_PLANT_INVALID_MALFORMED_EXTRACTING_PILLARS, "Couldn't form, one of the Extracting Pillars didn't appear in the right place.");
        add(MSLang.EXTRACTING_PLANT_INVALID_NOT_SQUARE, "Couldn't form, width and length must be equal.");
        add(MSLang.EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACTANT, "Input Extractant");
        add(MSLang.EXTRACTING_PLANT_PORT_MODE_INPUT_LEACHATE, "Input Leachate");
        add(MSLang.EXTRACTING_PLANT_PORT_MODE_OUTPUT, "Output");
        // Anti-Extracting Plant
        add(MSLang.ANTI_EXTRACTING_PLANT, "Anti-Extracting Plant");
        add(MSLang.ANTI_EXTRACTING_PLANT_INVALID_EVEN_LENGTH, "Couldn't form, width and length of structure must be odd.");
        add(MSLang.ANTI_EXTRACTING_PLANT_INVALID_MALFORMED_ANTI_EXTRACTING_PILLARS, "Couldn't form, one of the Anti-Extracting Pillars doesn't appear in the right place.");
        add(MSLang.ANTI_EXTRACTING_PLANT_INVALID_NOT_SQUARE, "Couldn't form, width and length must be equal.");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_ANTI_EXTRACTANT, "Input Anti-Extractant");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACT, "Input Extract");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_CONCENTRATE, "Output Concentrate");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_EXTRACTANT, "Output Extractant");
    }
}
