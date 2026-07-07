package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.MekanismScience;
import lombok.Getter;
import mekanism.api.chemical.attribute.ChemicalAttribute;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.EnumColor;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;
import net.minecraft.network.chat.Component;

import java.util.List;

public class MSGases {

    public static final GasDeferredRegister GASES = new GasDeferredRegister(MekanismScience.MODID);

    public static final GasRegistryObject<Gas> AMERICIUM = GASES.register("americium", 0xD56060, new GasAttributes.Radiation(0.05));
    public static final GasRegistryObject<Gas> AMMONIA = GASES.register(MSChemicalConstants.AMMONIA, new GasAttributes.Fuel(25, FloatingLong.createConst(20_000)));
    public static final GasRegistryObject<Gas> BROMINE = GASES.register(MSChemicalConstants.BROMINE);
    public static final GasRegistryObject<Gas> BERYLLIUM = GASES.register(MSChemicalConstants.BERYLLIUM);
    public static final GasRegistryObject<Gas> CALIFORNIUM = GASES.register("californium", 0xFFF08B00, new GasAttributes.Radiation(0.1));
    public static final GasRegistryObject<Gas> CHLOROMETHANE = GASES.register(MSChemicalConstants.CHLOROMETHANE);
    public static final GasRegistryObject<Gas> CONCENTRATED_SEAWATER = GASES.register(MSChemicalConstants.CONCENTRATED_SEAWATER);
    public static final GasRegistryObject<Gas> ETHANOL = GASES.register(MSChemicalConstants.ETHANOL);
    public static final GasRegistryObject<Gas> HELIUM = GASES.register(MSChemicalConstants.HELIUM);
    public static final GasRegistryObject<Gas> SUPERHEATED_HELIUM = GASES.register(MSChemicalConstants.SUPERHEATED_HELIUM);
    public static final GasRegistryObject<Gas> IODINE = GASES.register(MSChemicalConstants.IODINE);
    public static final GasRegistryObject<Gas> LACTOSE = GASES.register(MSChemicalConstants.LACTOSE);
    public static final GasRegistryObject<Gas> METHANOL = GASES.register(MSChemicalConstants.METHANOL);
    public static final GasRegistryObject<Gas> METHYLAMINE = GASES.register(MSChemicalConstants.METHYLAMINE);
    public static final GasRegistryObject<Gas> SEAWATER = GASES.register(MSChemicalConstants.SEAWATER);
    public static final GasRegistryObject<Gas> STRONTIUM = GASES.register(MSChemicalConstants.STRONTIUM);
    public static final GasRegistryObject<Gas> WHEY = GASES.register(MSChemicalConstants.WHEY);
    public static final GasRegistryObject<Gas> YTTRIUM = GASES.register(MSChemicalConstants.YTTRIUM);
    public static final GasRegistryObject<Gas> P204 = GASES.register("p204", 0xFF9A6414, new Extractant(0.8));
    public static final GasRegistryObject<Gas> PHOSPHORYL_CHLORIDE = GASES.register(MSChemicalConstants.PHOSPHORYL_CHLORIDE);
    public static final GasRegistryObject<Gas> IMPURE_PHOSPHORYL_CHLORIDE = GASES.register("impure_phosphoryl_chloride", 0xFF46F675);
    public static final GasRegistryObject<Gas> ISOOCTANOL = GASES.register(MSChemicalConstants.ISOOCTANOL);
    public static final GasRegistryObject<Gas> _2_ETHYL_2_HEXENAL = GASES.register(MSChemicalConstants._2_ETHYL_2_HEXENAL);
    public static final GasRegistryObject<Gas> N_BUTYRALDEHYDE = GASES.register(MSChemicalConstants.N_BUTYRALDEHYDE, new GasAttributes.Fuel(32, FloatingLong.createConst(32_000)));
    public static final GasRegistryObject<Gas> ISOBUTYRALDEHYDE = GASES.register(MSChemicalConstants.ISOBUTYRALDEHYDE, new GasAttributes.Fuel(32, FloatingLong.createConst(32_000)));
    public static final GasRegistryObject<Gas> BUTYRALDEHYDE_MIXTURE = GASES.register("butyraldehyde_mixture", 0xFFEC724D, new GasAttributes.Fuel(40, FloatingLong.createConst(28_000)));
    public static final GasRegistryObject<Gas> PROPYLENE = GASES.register(MSChemicalConstants.PROPYLENE);
    public static final GasRegistryObject<Gas> _2_BUTENE = GASES.register(MSChemicalConstants._2_BUTENE);
    public static final GasRegistryObject<Gas> HYDRAZINE = GASES.register(MSChemicalConstants.HYDRAZINE, new GasAttributes.Fuel(12, FloatingLong.createConst(37_500)));
    public static final GasRegistryObject<Gas> WATER_GAS = GASES.register("water_gas", 0xFF002E57, new GasAttributes.Fuel(10, FloatingLong.createConst(1_500)));
    public static final GasRegistryObject<Gas> SODIUM_HYDROXIDE = GASES.register(MSChemicalConstants.SODIUM_HYDROXIDE);
    public static final GasRegistryObject<Gas> COMPRESSED_AIR = GASES.register(MSChemicalConstants.COMPRESSED_AIR);
    public static final GasRegistryObject<Gas> NITROGEN = GASES.register(MSChemicalConstants.NITROGEN);
    public static final GasRegistryObject<Gas> NITRIC_OXIDE = GASES.register(MSChemicalConstants.NITRIC_OXIDE);
    public static final GasRegistryObject<Gas> NITROGEN_DIOXIDE = GASES.register(MSChemicalConstants.NITROGEN_DIOXIDE);
    public static final GasRegistryObject<Gas> NITRIC_ACID = GASES.register(MSChemicalConstants.NITRIC_ACID);
    public static final GasRegistryObject<Gas> AQUA_REGIA = GASES.register("aqua_regia", 0xFFEB5414);
    public static final GasRegistryObject<Gas> POTASSIUM_NITRATE = GASES.register(MSChemicalConstants.POTASSIUM_NITRATE);
    public static final GasRegistryObject<Gas> TETRAFLUOROETHYLENE = GASES.register(MSChemicalConstants.TETRAFLUOROETHYLENE);
    public static final GasRegistryObject<Gas> PTFE = GASES.register("ptfe", 0xFF86239F);

    @Getter
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
    }

    private MSGases() {
    }
}