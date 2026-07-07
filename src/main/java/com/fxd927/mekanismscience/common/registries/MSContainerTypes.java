package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.tile.machine.*;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MSContainerTypes {

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismScience.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntitySeawaterPump>> SEAWATER_PUMP = CONTAINER_TYPES.register(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityPressurizedPolymerizingChamber>> PRESSURIZED_POLYMERIZING_CHAMBER = CONTAINER_TYPES.register(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER, TileEntityPressurizedPolymerizingChamber.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityExtractingPlantCasing>> EXTRACTING_PLANT = CONTAINER_TYPES.register("extracting_plant", TileEntityExtractingPlantCasing.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAntiExtractingPlantCasing>> ANTI_EXTRACTING_PLANT = CONTAINER_TYPES.register("anti_extracting_plant", TileEntityAntiExtractingPlantCasing.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAcidLeacher>> ACID_LEACHER = CONTAINER_TYPES.custom("acid_leacher", TileEntityAcidLeacher.class).offset(0, 4).build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAirCompressor>> AIR_COMPRESSOR = CONTAINER_TYPES.register("air_compressor", TileEntityAirCompressor.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdsorptionSeparator>> ADSORPTION_SEPARATOR = CONTAINER_TYPES.register(MSBlocks.ADSORPTION_SEPARATOR, TileEntityAdsorptionSeparator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityIrradiator>> IRRADIATOR = CONTAINER_TYPES.register(MSBlocks.IRRADIATOR, TileEntityIrradiator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityMetalElectrolysisChamber>> METAL_ELECTROLYSIS_CHAMBER = CONTAINER_TYPES.custom("metal_electrolysis_chamber", TileEntityMetalElectrolysisChamber.class).offset(0, 4).build();

    private MSContainerTypes(){
    }
}
