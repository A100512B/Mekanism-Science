package com.fxd927.mekanismscience.datagen.common.loot;

import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.NBTConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.lib.frequency.IFrequencyHandler;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.interfaces.ISideConfiguration;
import mekanism.common.tile.interfaces.ISustainedData;
import mekanism.common.util.EnumUtils;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MSBlockLootSubProvider extends BlockLootSubProvider {

    public MSBlockLootSubProvider() {
        super(Collections.emptySet(), FeatureFlags.VANILLA_SET);
    }

    @Override
    protected void generate() {
        dropSelf(MSBlocks.ANTI_EXTRACTING_PLANT_CASING);
        dropSelf(MSBlocks.ANTI_EXTRACTING_PILLAR);
        dropSelf(MSBlocks.ANTI_EXTRACTING_PLANT_PORT);
        dropSelf(MSBlocks.EXTRACTING_PLANT_CASING);
        dropSelf(MSBlocks.EXTRACTING_PILLAR);
        dropSelf(MSBlocks.EXTRACTING_PLANT_PORT);
        dropSelf(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING);
        dropSelf(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR);
        dropSelf(MSBlocks.METAL_ELECTROLYZING_ROD);
        dropSelfWithContents(MSBlocks.AIR_COMPRESSOR);
        dropSelfWithContents(MSBlocks.ACID_LEACHER);
        dropSelfWithContents(MSBlocks.ADSORPTION_SEPARATOR);
        dropSelfWithContents(MSBlocks.IRRADIATOR);
        dropSelfWithContents(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT);
        dropSelfWithContents(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER);
        dropSelfWithContents(MSBlocks.SEAWATER_PUMP);
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return MSBlocks.BLOCKS.getAllBlocks().stream().map(IBlockProvider::getBlock).toList();
    }

    private void dropSelf(IBlockProvider block) {
        dropSelf(block.getBlock());
    }

    // Basically a copy of BaseBlockLootTables#dropSelfWithContents
    protected void dropSelfWithContents(IBlockProvider block) {
        CopyNbtFunction.Builder nbtBuilder = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY);
        LootItem.Builder<?> itemLootPool = LootItem.lootTableItem(block);
        DelayedLootItemBuilder delayedPool = new DelayedLootItemBuilder();
        @Nullable BlockEntity tile = null;
        if (block instanceof IHasTileEntity<?> hasTileEntity) {
            tile = hasTileEntity.createDummyBlockEntity();
        }
        if (tile instanceof IFrequencyHandler frequencyHandler && frequencyHandler.getFrequencyComponent().hasCustomFrequencies()) {
            nbtBuilder.copy(NBTConstants.COMPONENT_FREQUENCY, NBTConstants.MEK_DATA + "." + NBTConstants.COMPONENT_FREQUENCY);
        }
        if (Attribute.has(block.getBlock(), Attributes.AttributeSecurity.class)) {
            nbtBuilder.copy(NBTConstants.COMPONENT_SECURITY + "." + NBTConstants.OWNER_UUID, NBTConstants.MEK_DATA + "." + NBTConstants.OWNER_UUID);
            nbtBuilder.copy(NBTConstants.COMPONENT_SECURITY + "." + NBTConstants.SECURITY_MODE, NBTConstants.MEK_DATA + "." + NBTConstants.SECURITY_MODE);
        }
        if (Attribute.has(block.getBlock(), AttributeUpgradeSupport.class)) {
            nbtBuilder.copy(NBTConstants.COMPONENT_UPGRADE, NBTConstants.MEK_DATA + "." + NBTConstants.COMPONENT_UPGRADE);
        }
        if (tile instanceof ISideConfiguration) {
            nbtBuilder.copy(NBTConstants.COMPONENT_CONFIG, NBTConstants.MEK_DATA + "." + NBTConstants.COMPONENT_CONFIG);
            nbtBuilder.copy(NBTConstants.COMPONENT_EJECTOR, NBTConstants.MEK_DATA + "." + NBTConstants.COMPONENT_EJECTOR);
        }
        if (tile instanceof ISustainedData sustainedData) {
            Set<Map.Entry<String, String>> remapEntries = sustainedData.getTileDataRemap().entrySet();
            for (Map.Entry<String, String> remapEntry : remapEntries) {
                nbtBuilder.copy(remapEntry.getKey(), NBTConstants.MEK_DATA + "." + remapEntry.getValue());
            }
        }
        if (Attribute.has(block.getBlock(), Attributes.AttributeRedstone.class)) {
            nbtBuilder.copy(NBTConstants.CONTROL_TYPE, NBTConstants.MEK_DATA + "." + NBTConstants.CONTROL_TYPE);
        }
        if (tile instanceof TileEntityMekanism tileEntity) {
            if (tileEntity.isNameable()) {
                itemLootPool.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY));
            }
            for (SubstanceType type : EnumUtils.SUBSTANCES) {
                if (tileEntity.handles(type) && !type.getContainers(tileEntity).isEmpty()) {
                    nbtBuilder.copy(type.getContainerTag(), NBTConstants.MEK_DATA + "." + type.getContainerTag());
                }
            }
        }
        @SuppressWarnings("unchecked")
        Attributes.AttributeInventory<DelayedLootItemBuilder> attributeInventory = Attribute.get(block, Attributes.AttributeInventory.class);
        if (attributeInventory != null) {
            if (attributeInventory.hasCustomLoot()) {
                attributeInventory.applyLoot(delayedPool, nbtBuilder);
            }
            //If the block has an inventory and no custom loot function, copy the inventory slots,
            // but if it is an IItemHandler, which for most cases of ours it will be,
            // then only copy the slots if we actually have any slots because otherwise maybe something just went wrong
            else if (!(tile instanceof IItemHandler handler) || handler.getSlots() > 0) {
                //If we don't actually handle saving an inventory (such as the quantum entangloporter, don't actually add it as something to copy)
                if (!(tile instanceof TileEntityMekanism tileMek) || tileMek.persistInventory()) {
                    nbtBuilder.copy(NBTConstants.ITEMS, NBTConstants.MEK_DATA + "." + NBTConstants.ITEMS);
                }
            }
        }
        itemLootPool.apply(nbtBuilder);
        //apply the delayed ones last, so that NBT funcs have happened first
        for (LootItemFunction.Builder function : delayedPool.functions) {
            itemLootPool.apply(function);
        }
        for (LootItemCondition.Builder condition : delayedPool.conditions) {
            itemLootPool.when(condition);
        }
        add(block.getBlock(), LootTable.lootTable().withPool(applyExplosionCondition(block.asItem(), LootPool.lootPool()
                .name("main")
                .setRolls(ConstantValue.exactly(1))
                .add(itemLootPool)
        )));
    }

    @NothingNullByDefault
    public static class DelayedLootItemBuilder implements ConditionUserBuilder<DelayedLootItemBuilder>, FunctionUserBuilder<DelayedLootItemBuilder> {
        private final List<LootItemFunction.Builder> functions = new ArrayList<>();
        private final List<LootItemCondition.Builder> conditions = new ArrayList<>();

        @Override
        public DelayedLootItemBuilder apply(LootItemFunction.Builder pFunctionBuilder) {
            functions.add(pFunctionBuilder);
            return this;
        }

        @Override
        public DelayedLootItemBuilder when(LootItemCondition.Builder pConditionBuilder) {
            conditions.add(pConditionBuilder);
            return this;
        }

        @Override
        public DelayedLootItemBuilder unwrap() {
            return this;
        }
    }
}
