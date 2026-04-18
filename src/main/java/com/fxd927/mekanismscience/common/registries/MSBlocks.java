package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.content.blocktype.MSMachine;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityAdsorptionTypeSeawaterMetalExtractor;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityOrganicLiquidExtractor;
import com.fxd927.mekanismscience.common.tile.machine.TileEntityPressurizedPolymerizingChamber;
import com.fxd927.mekanismscience.common.tile.machine.TileEntitySeawaterPump;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantAntiExtractingPillar;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantController;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantPort;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantController;
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

    public static final BlockRegistryObject<BlockTileModel<TileEntityAdsorptionTypeSeawaterMetalExtractor, MSMachine<TileEntityAdsorptionTypeSeawaterMetalExtractor>>, ItemBlockMachine> ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR = BLOCKS.register("adsorption_type_seawater_metal_extractor", () -> new BlockTileModel<>(MSBlockTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityOrganicLiquidExtractor, MSMachine<TileEntityOrganicLiquidExtractor>>, ItemBlockMachine> ORGANIC_LIQUID_EXTRACTOR = BLOCKS.register("organic_liquid_extractor", () -> new BlockTileModel<>(MSBlockTypes.ORGANIC_LIQUID_EXTRACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntitySeawaterPump, MSMachine<TileEntitySeawaterPump>>, ItemBlockMachine> SEAWATER_PUMP = BLOCKS.register("seawater_pump", () -> new BlockTileModel<>(MSBlockTypes.SEAWATER_PUMP, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityPressurizedPolymerizingChamber, MSMachine<TileEntityPressurizedPolymerizingChamber>>, ItemBlockMachine> PRESSURIZED_POLYMERIZING_CHAMBER = BLOCKS.register("pressurized_polymerizing_chamber", () -> new BlockTileModel<>(MSBlockTypes.PRESSURIZED_POLYMERIZING_CHAMBER, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityExtractingPlantCasing>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityExtractingPlantCasing>>> EXTRACTING_PLANT_CASING = registerTooltipBlock("extracting_plant_casing", () -> new BlockBasicMultiblock<>(MSBlockTypes.EXTRACTING_PLANT_CASING, properties -> properties.mapColor(MapColor.LAPIS)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityExtractingPlantController>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityExtractingPlantController>>> EXTRACTING_PLANT_CONTROLLER = registerTooltipBlock("extracting_plant_controller", () -> new BlockBasicMultiblock<>(MSBlockTypes.EXTRACTING_PLANT_CONTROLLER, properties -> properties.mapColor(MapColor.LAPIS)));
    public static final BlockRegistryObject<BlockTileModel<TileEntityExtractingPlantExtractingPillar, BlockTypeTile<TileEntityExtractingPlantExtractingPillar>>, ItemBlockTooltip<BlockTileModel<TileEntityExtractingPlantExtractingPillar, BlockTypeTile<TileEntityExtractingPlantExtractingPillar>>>> EXTRACTING_PILLAR = registerTooltipBlock("extracting_pillar", () -> new BlockTileModel<>(MSBlockTypes.EXTRACTING_PILLAR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityExtractingPlantPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityExtractingPlantPort>>> EXTRACTING_PLANT_PORT = registerTooltipBlock("extracting_plant_port", () -> new BlockBasicMultiblock<>(MSBlockTypes.EXTRACTING_PLANT_PORT, properties -> properties.mapColor(MapColor.LAPIS)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityAntiExtractingPlantCasing>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityAntiExtractingPlantCasing>>> ANTI_EXTRACTING_PLANT_CASING = registerTooltipBlock("anti_extracting_plant_casing", () -> new BlockBasicMultiblock<>(MSBlockTypes.ANTI_EXTRACTING_PLANT_CASING, properties -> properties.mapColor(MapColor.COLOR_RED)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityAntiExtractingPlantController>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityAntiExtractingPlantController>>> ANTI_EXTRACTING_PLANT_CONTROLLER = registerTooltipBlock("anti_extracting_plant_controller", () -> new BlockBasicMultiblock<>(MSBlockTypes.ANTI_EXTRACTING_PLANT_CONTROLLER, properties -> properties.mapColor(MapColor.COLOR_RED)));
    public static final BlockRegistryObject<BlockTileModel<TileEntityAntiExtractingPlantAntiExtractingPillar, BlockTypeTile<TileEntityAntiExtractingPlantAntiExtractingPillar>>, ItemBlockTooltip<BlockTileModel<TileEntityAntiExtractingPlantAntiExtractingPillar, BlockTypeTile<TileEntityAntiExtractingPlantAntiExtractingPillar>>>> ANTI_EXTRACTING_PILLAR = registerTooltipBlock("anti_extracting_pillar", () -> new BlockTileModel<>(MSBlockTypes.ANTI_EXTRACTING_PILLAR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityAntiExtractingPlantPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityAntiExtractingPlantPort>>> ANTI_EXTRACTING_PLANT_PORT = registerTooltipBlock("anti_extracting_plant_port", () -> new BlockBasicMultiblock<>(MSBlockTypes.ANTI_EXTRACTING_PLANT_PORT, properties -> properties.mapColor(MapColor.COLOR_RED)));

    private MSBlocks() {
    }

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }
}
