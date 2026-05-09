package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateAntiExtractingPortMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateElectrolyzingRodMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateExtractingPortMode;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.blocktype.MSBlockShapes;
import com.fxd927.mekanismscience.common.content.blocktype.MSMachine;
import com.fxd927.mekanismscience.common.tile.machine.*;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantAntiExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantPort;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberLaserAcceptor;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberPort;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolyzingRod;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantPort;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.generators.common.content.blocktype.BlockShapes;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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

    public static final BlockTypeTile<TileEntityMetalElectrolysisChamberCasing> METAL_ELECTROLYSIS_CHAMBER_CASING = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER_CASING, MSLang.DESCRIPTION_METAL_ELECTROLYSIS_CHAMBER_CASING)
            .withGui(() -> MSContainerTypes.METAL_ELECTROLYSIS_CHAMBER, MSLang.METAL_ELECTROLYSIS_CHAMBER)
            .withSound(MSSounds.METAL_ELECTROLYSIS_CHAMBER)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityMetalElectrolysisChamberPort> METAL_ELECTROLYSIS_CHAMBER_PORT = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER_PORT, MSLang.DESCRIPTION_METAL_ELECTROLYSIS_CHAMBER_PORT)
            .with(Attributes.ACTIVE)
            .withSound(MSSounds.METAL_ELECTROLYSIS_CHAMBER)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityMetalElectrolyzingRod> METAL_ELECTROLYZING_ROD = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.METAL_ELECTROLYZING_ROD, MSLang.DESCRIPTION_METAL_ELECTROLYSIS_ROD)
            .with(new AttributeStateElectrolyzingRodMode(), new AttributeStateFacing(BlockStateProperties.HORIZONTAL_FACING))
            .withCustomShape(MSBlockShapes.METAL_ELECTROLYSIS_ROD)
            .withSound(MSSounds.METAL_ELECTROLYSIS_CHAMBER)
            .internalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityMetalElectrolysisChamberLaserAcceptor> METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR, MSLang.DESCRIPTION_METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR)
            .withSound(MSSounds.METAL_ELECTROLYSIS_CHAMBER)
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
            .build();
}
