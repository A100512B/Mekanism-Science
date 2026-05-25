package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.block.BlockMetalElectrolysisRod;
import com.fxd927.mekanismscience.common.content.blocktype.MSMachine;
import com.fxd927.mekanismscience.common.tile.machine.*;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantAntiExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantPort;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberLaserAcceptor;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberPort;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantPort;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class MSBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismScience.MODID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityOrganicLiquidExtractor, MSMachine<TileEntityOrganicLiquidExtractor>>, ItemBlockMachine> ORGANIC_LIQUID_EXTRACTOR = BLOCKS.register("organic_liquid_extractor", () -> new BlockTileModel<>(MSBlockTypes.ORGANIC_LIQUID_EXTRACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntitySeawaterPump, MSMachine<TileEntitySeawaterPump>>, ItemBlockMachine> SEAWATER_PUMP = BLOCKS.register("seawater_pump", () -> new BlockTileModel<>(MSBlockTypes.SEAWATER_PUMP, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityPressurizedPolymerizingChamber, MSMachine<TileEntityPressurizedPolymerizingChamber>>, ItemBlockMachine> PRESSURIZED_POLYMERIZING_CHAMBER = BLOCKS.register("pressurized_polymerizing_chamber", () -> new BlockTileModel<>(MSBlockTypes.PRESSURIZED_POLYMERIZING_CHAMBER, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityExtractingPlantCasing>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityExtractingPlantCasing>>> EXTRACTING_PLANT_CASING = registerTooltipBlock("extracting_plant_casing", () -> new BlockBasicMultiblock<>(MSBlockTypes.EXTRACTING_PLANT_CASING, properties -> properties.mapColor(MapColor.LAPIS)));
    public static final BlockRegistryObject<BlockTileModel<TileEntityExtractingPlantExtractingPillar, BlockTypeTile<TileEntityExtractingPlantExtractingPillar>>, ItemBlockTooltip<BlockTileModel<TileEntityExtractingPlantExtractingPillar, BlockTypeTile<TileEntityExtractingPlantExtractingPillar>>>> EXTRACTING_PILLAR = registerTooltipBlock("extracting_pillar", () -> new BlockTileModel<>(MSBlockTypes.EXTRACTING_PILLAR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityExtractingPlantPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityExtractingPlantPort>>> EXTRACTING_PLANT_PORT = registerTooltipBlock("extracting_plant_port", () -> new BlockBasicMultiblock<>(MSBlockTypes.EXTRACTING_PLANT_PORT, properties -> properties.mapColor(MapColor.LAPIS)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityAntiExtractingPlantCasing>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityAntiExtractingPlantCasing>>> ANTI_EXTRACTING_PLANT_CASING = registerTooltipBlock("anti_extracting_plant_casing", () -> new BlockBasicMultiblock<>(MSBlockTypes.ANTI_EXTRACTING_PLANT_CASING, properties -> properties.mapColor(MapColor.COLOR_RED)));
    public static final BlockRegistryObject<BlockTileModel<TileEntityAntiExtractingPlantAntiExtractingPillar, BlockTypeTile<TileEntityAntiExtractingPlantAntiExtractingPillar>>, ItemBlockTooltip<BlockTileModel<TileEntityAntiExtractingPlantAntiExtractingPillar, BlockTypeTile<TileEntityAntiExtractingPlantAntiExtractingPillar>>>> ANTI_EXTRACTING_PILLAR = registerTooltipBlock("anti_extracting_pillar", () -> new BlockTileModel<>(MSBlockTypes.ANTI_EXTRACTING_PILLAR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityAntiExtractingPlantPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityAntiExtractingPlantPort>>> ANTI_EXTRACTING_PLANT_PORT = registerTooltipBlock("anti_extracting_plant_port", () -> new BlockBasicMultiblock<>(MSBlockTypes.ANTI_EXTRACTING_PLANT_PORT, properties -> properties.mapColor(MapColor.COLOR_RED)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityMetalElectrolysisChamberCasing>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityMetalElectrolysisChamberCasing>>> METAL_ELECTROLYSIS_CHAMBER_CASING = registerTooltipBlock("metal_electrolysis_chamber_casing", () -> new BlockBasicMultiblock<>(MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER_CASING, properties -> properties.mapColor(MapColor.METAL)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityMetalElectrolysisChamberPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityMetalElectrolysisChamberPort>>> METAL_ELECTROLYSIS_CHAMBER_PORT = registerTooltipBlock("metal_electrolysis_chamber_port", () -> new BlockBasicMultiblock<>(MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER_PORT, properties -> properties.mapColor(MapColor.METAL)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityMetalElectrolysisChamberLaserAcceptor>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityMetalElectrolysisChamberLaserAcceptor>>> METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR = registerTooltipBlock("metal_electrolysis_chamber_laser_acceptor", () -> new BlockBasicMultiblock<>(MSBlockTypes.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR, properties -> properties.mapColor(MapColor.NONE)));
    public static final BlockRegistryObject<BlockMetalElectrolysisRod, ItemBlockTooltip<BlockMetalElectrolysisRod>> METAL_ELECTROLYZING_ROD = registerTooltipBlock("metal_electrolyzing_rod", BlockMetalElectrolysisRod::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityAcidLeacher, MSMachine<TileEntityAcidLeacher>>, ItemBlockMachine> ACID_LEACHER = BLOCKS.register("acid_leacher", () -> new BlockTileModel<>(MSBlockTypes.ACID_LEACHER, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityAirCompressor, MSMachine<TileEntityAirCompressor>>, ItemBlockMachine> AIR_COMPRESSOR = BLOCKS.register("air_compressor", () -> new BlockTileModel<>(MSBlockTypes.AIR_COMPRESSOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityAdsorptionSeparator, MSMachine<TileEntityAdsorptionSeparator>>, ItemBlockMachine> ADSORPTION_SEPARATOR = BLOCKS.register("adsorption_separator", () -> new BlockTile.BlockTileModel<>(MSBlockTypes.ADSORPTION_SEPARATOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityIrradiator, MSMachine<TileEntityIrradiator>>, ItemBlockMachine> IRRADIATOR = BLOCKS.register("irradiator", () -> new BlockTile.BlockTileModel<>(MSBlockTypes.IRRADIATOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);

    private MSBlocks() {
    }

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }
}
