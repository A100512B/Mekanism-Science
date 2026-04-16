package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.api.chemical.attribute.ChemicalAttribute;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.text.EnumColor;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;
import net.minecraft.network.chat.Component;

import java.util.List;

public class MSGases {

    public static final GasDeferredRegister GASES = new GasDeferredRegister(MekanismScience.MODID);

    public static final GasRegistryObject<Gas> AMERICIUM;
    public static final GasRegistryObject<Gas> AMMONIA;
    public static final GasRegistryObject<Gas> BENZODIAZEPINE;
    public static final GasRegistryObject<Gas> BROMINE;
    public static final GasRegistryObject<Gas> BERYLLIUM;
    public static final GasRegistryObject<Gas> CALIFORNIUM;
    public static final GasRegistryObject<Gas> CHLOROMETHANE;
    public static final GasRegistryObject<Gas> CONCENTRATED_SEAWATER;
    public static final GasRegistryObject<Gas> ETHANOL;
    public static final GasRegistryObject<Gas> HELIUM;
    public static final GasRegistryObject<Gas> SUPERHEATED_HELIUM;
    public static final GasRegistryObject<Gas> IODINE;
    public static final GasRegistryObject<Gas> LACTOSE;
    public static final GasRegistryObject<Gas> METHANOL;
    public static final GasRegistryObject<Gas> METHYLAMINE;
    public static final GasRegistryObject<Gas> METHYLAMMONIUM_LEAD_IODIDE;
    public static final GasRegistryObject<Gas> SEAWATER;
    public static final GasRegistryObject<Gas> STRONTIUM;
    public static final GasRegistryObject<Gas> TETRODOTOXIN;
    public static final GasRegistryObject<Gas> WHEY;
    public static final GasRegistryObject<Gas> YTTRIUM;
    public static final GasRegistryObject<Gas> P204;
    public static final GasRegistryObject<Gas> PHOSPHORYL_CHLORIDE;
    public static final GasRegistryObject<Gas> PHOSPHORUS_TRICHLORIDE;
    public static final GasRegistryObject<Gas> ISOOCTANOL;
    public static final GasRegistryObject<Gas> _2_ETHYL_2_HEXENAL;
    public static final GasRegistryObject<Gas> N_BUTYRALDEHYDE;
    public static final GasRegistryObject<Gas> PROPYLENE;
    public static final GasRegistryObject<Gas> _2_BUTENE;

    static {
        AMERICIUM = GASES.register("americium", 0xD56060, new GasAttributes.Radiation(0.05));
        AMMONIA = GASES.register(MSChemicalConstants.AMMONIA);
        BENZODIAZEPINE = GASES.register(MSChemicalConstants.BENZODIAZEPINE);
        BROMINE = GASES.register(MSChemicalConstants.BROMINE);
        BERYLLIUM = GASES.register(MSChemicalConstants.BERYLLIUM);
        CALIFORNIUM = GASES.register("californium", 0xFFF08B00, new GasAttributes.Radiation(0.1));
        CHLOROMETHANE = GASES.register(MSChemicalConstants.CHLOROMETHANE);
        CONCENTRATED_SEAWATER = GASES.register(MSChemicalConstants.CONCENTRATED_SEAWATER);
        ETHANOL = GASES.register(MSChemicalConstants.ETHANOL);
        HELIUM = GASES.register(MSChemicalConstants.HELIUM);
        SUPERHEATED_HELIUM = GASES.register(MSChemicalConstants.SUPERHEATED_HELIUM);
        IODINE = GASES.register(MSChemicalConstants.IODINE);
        LACTOSE = GASES.register(MSChemicalConstants.LACTOSE);
        METHANOL = GASES.register(MSChemicalConstants.METHANOL);
        METHYLAMINE = GASES.register(MSChemicalConstants.METHYLAMINE);
        METHYLAMMONIUM_LEAD_IODIDE = GASES.register(MSChemicalConstants.METHYLAMMONIUM_LEAD_IODIDE);
        SEAWATER = GASES.register(MSChemicalConstants.SEAWATER);
        STRONTIUM = GASES.register(MSChemicalConstants.STRONTIUM);
        TETRODOTOXIN = GASES.register(MSChemicalConstants.TETRODOTOXIN);
        WHEY = GASES.register(MSChemicalConstants.WHEY);
        YTTRIUM = GASES.register(MSChemicalConstants.YTTRIUM);
        P204 = GASES.register("p204", 0xFF9A6414, new Extractant(0.8));
        PHOSPHORYL_CHLORIDE = GASES.register(MSChemicalConstants.PHOSPHORYL_CHLORIDE);
        PHOSPHORUS_TRICHLORIDE = GASES.register(MSChemicalConstants.PHOSPHORUS_TRICHLORIDE);
        ISOOCTANOL = GASES.register(MSChemicalConstants.ISOOCTANOL);
        _2_ETHYL_2_HEXENAL = GASES.register(MSChemicalConstants._2_ETHYL_2_HEXENAL);
        N_BUTYRALDEHYDE = GASES.register(MSChemicalConstants.N_BUTYRALDEHYDE);
        PROPYLENE = GASES.register(MSChemicalConstants.PROPYLENE);
        _2_BUTENE = GASES.register(MSChemicalConstants._2_BUTENE);
    }

    public static class Extractant extends ChemicalAttribute {

        private final double extractionEfficiency;

        public Extractant(double extractionEfficiency) {
            this.extractionEfficiency = extractionEfficiency;
        }

        @Override
        public List<Component> addTooltipText(List<Component> list) {
            super.addTooltipText(list);
            list.add(MSLang.CHEMICAL_ATTRIBUTE_EXTRACTION_EFFICIENCY.translateColored(EnumColor.GRAY, EnumColor.PINK, extractionEfficiency));
            return super.addTooltipText(list);
        }

        public double getExtractionEfficiency() {
            return extractionEfficiency;
        }
    }

    private MSGases() {
    }
}