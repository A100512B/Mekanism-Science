package com.fxd927.mekanismscience.common.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MSStorageConfig extends BaseMekanismConfig {

    private final ForgeConfigSpec configSpec;

    public final CachedFloatingLongValue adsorptionTypeSeawaterMetalExtractor;
    public final CachedFloatingLongValue seawaterPump;
    public final CachedFloatingLongValue organicLiquidExtractor;
    public final CachedFloatingLongValue pressurizedPolymerizingChamber;
    public final CachedFloatingLongValue metalElectrolysisChamberPort;
    public final CachedFloatingLongValue metalElectrolysisChamberLaserAcceptor;
    public final CachedFloatingLongValue acidLeacher;

    MSStorageConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Science Energy Storage Config. This config is synced from server to client.").push("storage");

        adsorptionTypeSeawaterMetalExtractor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "adsorptionTypeSeawaterMetalExtractor",
                FloatingLong.createConst(40_000));
        organicLiquidExtractor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "organicLiquidExtractor",
                FloatingLong.createConst(40_000));
        seawaterPump = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "seawaterPump",
                FloatingLong.createConst(40_000));
        pressurizedPolymerizingChamber = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "pressurizedPolymerizingChamber",
                FloatingLong.createConst(100_000));
        metalElectrolysisChamberPort = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "metalElectrolysisChamberPort",
                FloatingLong.createConst(2_500_000));
        metalElectrolysisChamberLaserAcceptor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "metalElectrolysisChamberLserAcceptor",
                FloatingLong.createConst(10_000_000));
        acidLeacher = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "acidLeacher",
                FloatingLong.createConst(25_000_000));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "science-storage";
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
