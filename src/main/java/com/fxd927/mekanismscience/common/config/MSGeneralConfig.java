package com.fxd927.mekanismscience.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MSGeneralConfig extends BaseMekanismConfig {

    private final ForgeConfigSpec configSpec;

    public final CachedLongValue extractionExtractantPerTank;
    public final CachedIntValue extractionLeachatePerTank;

    MSGeneralConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Science General Config. This config is synced from server to client.").push("general");

        builder.comment("Extracting Plant Settings").push("extracting_plant");
        extractionExtractantPerTank = CachedLongValue.wrap(this, builder.comment("Amount of gas (mB) that each block of the extracting plant contributes to the extractant tank capacity. Max = volume * gasPerTank")
                .define("extractantPerTank", 500L));
        extractionLeachatePerTank = CachedIntValue.wrap(this, builder.comment("Amount of fluid (mB) that each block of the extracting plant contributes to the leachate tank capacity. Max = volume * fluidPerTank")
                .define("leachatePerTank", 2000));
        builder.pop();

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "science-general";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    @Override
    public boolean addToContainer() {
        return false;
    }
}
