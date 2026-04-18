package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityAdsorptionTypeSeawaterMetalExtractor;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityOrganicLiquidExtractor;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityPressurizedPolymerizingChamber;
import com.fxd927.mekanismscience.common.tile.machine.TileEntitySeawaterPump;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantController;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantController;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MSContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismScience.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdsorptionTypeSeawaterMetalExtractor>> ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR = CONTAINER_TYPES.register(MSBlocks.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, TileEntityAdsorptionTypeSeawaterMetalExtractor.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityOrganicLiquidExtractor>> ORGANIC_LIQUID_EXTRACTOR = CONTAINER_TYPES.register(MSBlocks.ORGANIC_LIQUID_EXTRACTOR, TileEntityOrganicLiquidExtractor.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntitySeawaterPump>> SEAWATER_PUMP = CONTAINER_TYPES.register(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityPressurizedPolymerizingChamber>> PRESSURIZED_POLYMERIZING_CHAMBER = CONTAINER_TYPES.register(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER, TileEntityPressurizedPolymerizingChamber.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityExtractingPlantController>> EXTRACTING_PLANT = CONTAINER_TYPES.register("extracting_plant", TileEntityExtractingPlantController.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAntiExtractingPlantController>> ANTI_EXTRACTING_PLANT = CONTAINER_TYPES.register("anti_extracting_plant", TileEntityAntiExtractingPlantController.class);

    private MSContainerTypes(){
    }
}
