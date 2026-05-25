package com.fxd927.mekanismscience.common.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MSUsageConfig extends BaseMekanismConfig {

    public final CachedFloatingLongValue seawaterPump;
    public final CachedFloatingLongValue organicLiquidExtractor;
    public final CachedFloatingLongValue pressurizedPolymerizingChamber;
    public final CachedFloatingLongValue metalElectrolyzingRod;
    public final CachedFloatingLongValue metalElectrolyzingLaserRod;
    public final CachedFloatingLongValue acidLeacher;
    public final CachedFloatingLongValue airCompressor;
    public final CachedFloatingLongValue adsorptionSeparator;
    public final CachedFloatingLongValue irradiator;


    private final ForgeConfigSpec configSpec;

    MSUsageConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("MS Energy Usage Config. This config is synced from server to client.").push("storage");

        organicLiquidExtractor = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "organicLiquidExtractor",
                FloatingLong.createConst(100));
        seawaterPump = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "seawaterPump",
                FloatingLong.createConst(100));
        pressurizedPolymerizingChamber = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "pressurizedPolymerizingChamber",
                FloatingLong.createConst(200));
        metalElectrolyzingRod = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "metalElectrolyzingRod",
                FloatingLong.createConst(2500));
        metalElectrolyzingLaserRod = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "metalElectrolyzingLaserRod",
                FloatingLong.createConst(4000));
        acidLeacher = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "acidLeacher",
                FloatingLong.createConst(3000));
        airCompressor = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "airCompressor",
                FloatingLong.createConst(100));
        adsorptionSeparator = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "adsorptionTypeSeawaterMetalExtractor",
                FloatingLong.createConst(500));
        irradiator = CachedFloatingLongValue.define(this, builder, "Energy per operation tick (Joules).", "radiationIrradiator",
                FloatingLong.createConst(1_000));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "science-usage";
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
