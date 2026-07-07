package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateAntiExtractingPortMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateExtractingPortMode;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.blocktype.MSBlockShapes;
import com.fxd927.mekanismscience.common.content.blocktype.MSMachine;
import com.fxd927.mekanismscience.common.tile.machine.*;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantAntiExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantPort;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantPort;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.generators.common.content.blocktype.BlockShapes;
import mekanism.generators.common.registries.GeneratorsSounds;

import java.util.EnumSet;

public class MSBlockTypes {

    private MSBlockTypes() {}

    public static final MSMachine<TileEntitySeawaterPump> SEAWATER_PUMP = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.SEAWATER_PUMP, MSLang.DESCRIPTION_SEAWATER_PUMP)
            .withGui(() -> MSContainerTypes.SEAWATER_PUMP)
            .withEnergyConfig(MSConfig.usageConfig.seawaterPump, MSConfig.storageConfig.seawaterPump)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY))
            .withComputerSupport("seawaterPump")
            .replace(Attributes.ACTIVE)
            .build();

    public static final MSMachine<TileEntityPressurizedPolymerizingChamber> PRESSURIZED_POLYMERIZING_CHAMBER = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.PRESSURIZED_POLYMERIZING_CHAMBER, MSLang.DESCRIPTION_PRESSURIZED_POLYMERIZING_CHAMBER)
            .withGui(() -> MSContainerTypes.PRESSURIZED_POLYMERIZING_CHAMBER)
            .withSound(MSSounds.PRESSURIZED_POLYMERIZING_CHAMBER)
            .withEnergyConfig(MSConfig.usageConfig.pressurizedPolymerizingChamber, MSConfig.storageConfig.pressurizedPolymerizingChamber)
            .withCustomShape(mekanism.common.content.blocktype.BlockShapes.PRESSURIZED_REACTION_CHAMBER)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.ANCHOR, Upgrade.MUFFLING))
            .withComputerSupport("pressurizedPolymerizingChamber")
            .replace(Attributes.ACTIVE)
            .build();

    public static final BlockTypeTile<TileEntityExtractingPlantCasing> EXTRACTING_PLANT_CASING = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.EXTRACTING_PLANT_CASING, MSLang.DESCRIPTION_EXTRACTING_PLANT_CASING)
            .withGui(() -> MSContainerTypes.EXTRACTING_PLANT, MSLang.EXTRACTING_PLANT)
            .withSound(MSSounds.EXTRACTING_PLANT)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityExtractingPlantExtractingPillar> EXTRACTING_PILLAR = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.EXTRACTING_PILLAR, MSLang.DESCRIPTION_EXTRACTING_PILLAR)
            .withSound(MSSounds.EXTRACTING_PLANT)
            .withCustomShape(BlockShapes.FUEL_ASSEMBLY)
            .internalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityExtractingPlantPort> EXTRACTING_PLANT_PORT = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.EXTRACTING_PLANT_PORT, MSLang.DESCRIPTION_EXTRACTING_PLANT_PORT)
            .with(new AttributeStateExtractingPortMode())
            .withGui(() -> MSContainerTypes.EXTRACTING_PLANT, MSLang.EXTRACTING_PLANT)
            .withSound(MSSounds.EXTRACTING_PLANT)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityAntiExtractingPlantCasing> ANTI_EXTRACTING_PLANT_CASING = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ANTI_EXTRACTING_PLANT_CASING, MSLang.DESCRIPTION_ANTI_EXTRACTING_PLANT_CASING)
            .withGui(() -> MSContainerTypes.ANTI_EXTRACTING_PLANT, MSLang.ANTI_EXTRACTING_PLANT)
            .withSound(MSSounds.ANTI_EXTRACTING_PLANT)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityAntiExtractingPlantAntiExtractingPillar> ANTI_EXTRACTING_PILLAR = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ANTI_EXTRACTING_PILLAR, MSLang.DESCRIPTION_ANTI_EXTRACTING_PILLAR)
            .withSound(MSSounds.ANTI_EXTRACTING_PLANT)
            .withCustomShape(BlockShapes.FUEL_ASSEMBLY)
            .internalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityAntiExtractingPlantPort> ANTI_EXTRACTING_PLANT_PORT = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ANTI_EXTRACTING_PLANT_PORT, MSLang.DESCRIPTION_ANTI_EXTRACTING_PLANT_PORT)
            .with(new AttributeStateAntiExtractingPortMode())
            .withGui(() -> MSContainerTypes.ANTI_EXTRACTING_PLANT, MSLang.ANTI_EXTRACTING_PLANT)
            .withSound(MSSounds.ANTI_EXTRACTING_PLANT)
            .externalMultiblock()
            .build();

    public static final MSMachine<TileEntityAcidLeacher> ACID_LEACHER = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.ACID_LEACHER, MSLang.DESCRIPTION_ACID_LEACHER)
            .withGui(() -> MSContainerTypes.ACID_LEACHER)
            .withEnergyConfig(MSConfig.usageConfig.acidLeacher, MSConfig.storageConfig.acidLeacher)
            .withSupportedUpgrades(EnumSet.of(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.GAS, Upgrade.ANCHOR, Upgrade.MUFFLING))
            .withCustomShape(MSBlockShapes.ACID_LEACHER)
            .withBounding((pos, state, builder) -> {
                for (int x = -1; x <= 1; x++) {
                    for (int y = 0; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            if (x != 0 || y != 0 || z != 0) {
                                builder.add(pos.offset(x, y, z));
                            }
                        }
                    }
                }
            })
            .replace(Attributes.ACTIVE)
            .build();

    public static final MSMachine<TileEntityAirCompressor> AIR_COMPRESSOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.AIR_COMPRESSOR, MSLang.DESCRIPTION_AIR_COMPRESSOR)
            .withGui(() -> MSContainerTypes.AIR_COMPRESSOR)
            .withSound(MSSounds.AIR_COMPRESSOR)
            .withEnergyConfig(MSConfig.usageConfig.airCompressor, MSConfig.storageConfig.airCompressor)
            .withSupportedUpgrades(EnumSet.of(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.ANCHOR, Upgrade.MUFFLING))
            .replace(Attributes.ACTIVE)
            .build();

    public static final MSMachine<TileEntityAdsorptionSeparator> ADSORPTION_SEPARATOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.ADSORPTION_SEPARATOR, MSLang.DESCRIPTION_ADSORPTION_SEPARATOR)
            .withGui(() -> MSContainerTypes.ADSORPTION_SEPARATOR)
            .withSound(MSSounds.AIR_COMPRESSOR)
            .withEnergyConfig(MSConfig.usageConfig.adsorptionSeparator, MSConfig.storageConfig.adsorptionSeparator)
            .withCustomShape(MSBlockShapes.ADSORPTION_SEPARATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.ANCHOR, Upgrade.MUFFLING))
            .withComputerSupport("adsorptionSeparator")
            .replace(Attributes.ACTIVE_LIGHT)
            .build();

    public static final MSMachine<TileEntityIrradiator> IRRADIATOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.RADIATION_IRRADIATOR, MSLang.DESCRIPTION_IRRADIATOR)
            .withGui(() -> MSContainerTypes.IRRADIATOR)
            .withSound(GeneratorsSounds.FISSION_REACTOR)
            .withEnergyConfig(MSConfig.usageConfig.irradiator, MSConfig.storageConfig.irradiator)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.ANCHOR, Upgrade.MUFFLING))
            .withComputerSupport("irradiator")
            .replace(Attributes.ACTIVE_FULL_LIGHT)
            .build();

    public static final MSMachine<TileEntityMetalElectrolysisChamber> METAL_ELECTROLYSIS_CHAMBER = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER, MSLang.DESCRIPTION_METAL_ELECTROLYSIS_CHAMBER)
            .withGui(() -> MSContainerTypes.METAL_ELECTROLYSIS_CHAMBER)
            .withSound(MSSounds.METAL_ELECTROLYSIS_CHAMBER)
            .withEnergyConfig(MSConfig.usageConfig.metalElectrolysisChamber, MSConfig.storageConfig.metalElectrolysisChamber)
            .withCustomShape(MSBlockShapes.METAL_ELECTROLYSIS_CHAMBER)
            .withBounding((pos, state, builder) -> {
                for (int x = -1; x <= 1; x++) {
                    for (int y = 0; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            if (x != 0 || y != 0 || z != 0) {
                                builder.add(pos.offset(x, y, z));
                            }
                        }
                    }
                }
            })
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.ANCHOR, Upgrade.MUFFLING))
            .replace(Attributes.ACTIVE_FULL_LIGHT)
            .build();
}
