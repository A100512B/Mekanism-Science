package com.fxd927.mekanismscience.common;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum MSLang implements ILangEntry {

    ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR("description", "adsorption_type_seawater_metal_extractor"),

    CHEMICAL_ATTRIBUTE_EXTRACTION_EFFICIENCY("chemical", "attribute.extraction_efficiency"),

    DESCRIPTION_ORGANIC_LIQUID_EXTRACTOR("description","organic_liquid_extractor"),
    DESCRIPTION_SEAWATER_PUMP("description", "seawater_pump"),
    DESCRIPTION_PRESSURIZED_POLYMERIZING_CHAMBER("description", "pressurized_polymerizing_chamber"),

    MEKANISM_SCIENCE("constants","mod_name");

    private final String key;

    MSLang(String type,String path){
        this(Util.makeDescriptionId(type, MekanismScience.rl(path)));
    }

    MSLang(String key){
        this.key = key;
    }

    @Override
    @NotNull
    public String getTranslationKey(){
        return key;
    }
}
