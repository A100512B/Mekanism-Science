package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
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
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class MSTileEntityTypes {

    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismScience.MODID);

    public static final TileEntityTypeRegistryObject<TileEntitySeawaterPump> SEAWATER_PUMP = TILE_ENTITY_TYPES.register(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityPressurizedPolymerizingChamber> PRESSURIZED_POLYMERIZING_CHAMBER = TILE_ENTITY_TYPES.register(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER, TileEntityPressurizedPolymerizingChamber::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityExtractingPlantCasing> EXTRACTING_PLANT_CASING = TILE_ENTITY_TYPES.register(MSBlocks.EXTRACTING_PLANT_CASING, TileEntityExtractingPlantCasing::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityExtractingPlantExtractingPillar> EXTRACTING_PILLAR = TILE_ENTITY_TYPES.register(MSBlocks.EXTRACTING_PILLAR, TileEntityExtractingPlantExtractingPillar::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityExtractingPlantPort> EXTRACTING_PLANT_PORT = TILE_ENTITY_TYPES.register(MSBlocks.EXTRACTING_PLANT_PORT, TileEntityExtractingPlantPort::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityAntiExtractingPlantCasing> ANTI_EXTRACTING_PLANT_CASING = TILE_ENTITY_TYPES.register(MSBlocks.ANTI_EXTRACTING_PLANT_CASING, TileEntityAntiExtractingPlantCasing::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityAntiExtractingPlantAntiExtractingPillar> ANTI_EXTRACTING_PILLAR = TILE_ENTITY_TYPES.register(MSBlocks.ANTI_EXTRACTING_PILLAR, TileEntityAntiExtractingPlantAntiExtractingPillar::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityAntiExtractingPlantPort> ANTI_EXTRACTING_PLANT_PORT = TILE_ENTITY_TYPES.register(MSBlocks.ANTI_EXTRACTING_PLANT_PORT, TileEntityAntiExtractingPlantPort::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityMetalElectrolysisChamberCasing> METAL_ELECTROLYSIS_CHAMBER_CASING = TILE_ENTITY_TYPES.register(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING, TileEntityMetalElectrolysisChamberCasing::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityMetalElectrolysisChamberPort> METAL_ELECTROLYSIS_CHAMBER_PORT = TILE_ENTITY_TYPES.register(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT, TileEntityMetalElectrolysisChamberPort::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityMetalElectrolysisChamberLaserAcceptor> METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR = TILE_ENTITY_TYPES.register(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR, TileEntityMetalElectrolysisChamberLaserAcceptor::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityMetalElectrolyzingRod> METAL_ELECTROLYZING_ROD = TILE_ENTITY_TYPES.register(MSBlocks.METAL_ELECTROLYZING_ROD, TileEntityMetalElectrolyzingRod::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityAcidLeacher> ACID_LEACHER = TILE_ENTITY_TYPES.register(MSBlocks.ACID_LEACHER, TileEntityAcidLeacher::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityAirCompressor> AIR_COMPRESSOR = TILE_ENTITY_TYPES.register(MSBlocks.AIR_COMPRESSOR, TileEntityAirCompressor::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityAdsorptionSeparator> ADSORPTION_SEPARATOR = TILE_ENTITY_TYPES.register(MSBlocks.ADSORPTION_SEPARATOR, TileEntityAdsorptionSeparator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityIrradiator> RADIATION_IRRADIATOR = TILE_ENTITY_TYPES.register(MSBlocks.IRRADIATOR, TileEntityIrradiator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);

    private MSTileEntityTypes(){
    }
}
