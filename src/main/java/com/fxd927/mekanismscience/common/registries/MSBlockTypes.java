package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateExtractingPortMode;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.blocktype.MSMachine;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityAdsorptionTypeSeawaterMetalExtractor;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityOrganicLiquidExtractor;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityPressurizedPolymerizingChamber;
import com.fxd927.mekanismscience.common.tile.machine.TileEntitySeawaterPump;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantController;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantPort;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.generators.common.content.blocktype.BlockShapes;

import java.util.EnumSet;

public class MSBlockTypes {

    private MSBlockTypes() {}

    public static final MSMachine<TileEntityAdsorptionTypeSeawaterMetalExtractor> ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, MSLang.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR)
            .withGui(() -> MSContainerTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR)
            .withEnergyConfig(MSConfig.usageConfig.adsorptionTypeSeawaterMetalExtractor, MSConfig.storageConfig.adsorptionTypeSeawaterMetalExtractor)
            .withComputerSupport("adsorptionTypeSeawaterMetalExtractor")
            .replace(Attributes.ACTIVE)
            .build();

    public static final MSMachine<TileEntityOrganicLiquidExtractor> ORGANIC_LIQUID_EXTRACTOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.ORGANIC_LIQUID_EXTRACTOR, MSLang.DESCRIPTION_ORGANIC_LIQUID_EXTRACTOR)
            .withGui(() -> MSContainerTypes.ORGANIC_LIQUID_EXTRACTOR)
            .withEnergyConfig(MSConfig.usageConfig.organicLiquidExtractor, MSConfig.storageConfig.organicLiquidExtractor)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY))
            .withComputerSupport("organicLiquidExtractor")
            .replace(Attributes.ACTIVE)
            .build();

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
            .withEnergyConfig(MSConfig.usageConfig.pressurizedPolymerizingChamber, MSConfig.storageConfig.pressurizedPolymerizingChamber)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY))
            .withComputerSupport("pressurizedPolymerizingChamber")
            .replace(Attributes.ACTIVE)
            .build();

    public static final BlockTypeTile<TileEntityExtractingPlantCasing> EXTRACTING_PLANT_CASING = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.EXTRACTING_PLANT_CASING, MSLang.DESCRIPTION_EXTRACTING_PLANT_CASING)
            .withSound(MSSounds.EXTRACTING_PLANT)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityExtractingPlantController> EXTRACTING_PLANT_CONTROLLER = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.EXTRACTING_PLANT_CONTROLLER, MSLang.DESCRIPTION_EXTRACTING_PLANT_CONTROLLER)
            .withSound(MSSounds.EXTRACTING_PLANT)
            .withGui(() -> MSContainerTypes.EXTRACTING_PLANT)
            .with(Attributes.ACTIVE, Attributes.ACTIVE_MELT_LIGHT, new AttributeStateFacing())
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
            .withSound(MSSounds.EXTRACTING_PLANT)
            .externalMultiblock()
            .build();
}
