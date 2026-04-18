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
    DESCRIPTION_EXTRACTING_PLANT_CASING("description", "extracting_plant_casing"),
    DESCRIPTION_EXTRACTING_PLANT_CONTROLLER("description", "extracting_plant_controller"),
    DESCRIPTION_EXTRACTING_PILLAR("description", "extracting_pillar"),
    DESCRIPTION_EXTRACTING_PLANT_PORT("description", "extracting_plant_port"),
    DESCRIPTION_ANTI_EXTRACTING_PLANT_CASING("description", "anti_extracting_plant_casing"),
    DESCRIPTION_ANTI_EXTRACTING_PLANT_CONTROLLER("description", "anti_extracting_plant_controller"),
    DESCRIPTION_ANTI_EXTRACTING_PILLAR("description", "anti_extracting_pillar"),
    DESCRIPTION_ANTI_EXTRACTING_PLANT_PORT("description", "anti_extracting_plant_port"),

    EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACTANT("extracting_plant", "port_mode_input_extractant"),
    EXTRACTING_PLANT_PORT_MODE_INPUT_LEACHATE("extracting_plant", "port_mode_input_leachate"),
    EXTRACTING_PLANT_PORT_MODE_OUTPUT("extracting_plant", "port_mode_output"),
    EXTRACTING_PLANT_INVALID_EVEN_LENGTH("extracting_plant", "invalid_even_length"),
    EXTRACTING_PLANT_INVALID_NOT_SQUARE("extracting_plant", "invalid_not_square"),
    EXTRACTING_PLANT_INVALID_MALFORMED_EXTRACTING_PILLARS("extracting_plant", "invalid_malformed_extracting_pillars"),
    ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_ANTI_EXTRACTANT("anti_extracting_plant", "port_mode_input_anti_extractant"),
    ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACT("anti_extracting_plant", "port_mode_input_extract"),
    ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_EXTRACTANT("anti_extracting_plant", "port_mode_output_extractant"),
    ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_CONCENTRATE("anti_extracting_plant", "port_mode_output_concentrate"),
    ANTI_EXTRACTING_PLANT_INVALID_EVEN_LENGTH("anti_extracting_plant", "invalid_even_length"),
    ANTI_EXTRACTING_PLANT_INVALID_NOT_SQUARE("anti_extracting_plant", "invalid_not_square"),
    ANTI_EXTRACTING_PLANT_INVALID_MALFORMED_ANTI_EXTRACTING_PILLARS("anti_extracting_plant", "invalid_malformed_anti_extracting_pillars"),

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
