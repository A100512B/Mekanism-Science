package com.fxd927.mekanismscience.datagen.common.loot.table;

import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.providers.IBlockProvider;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MSBlockLootSubProvider extends BaseBlockLootTables {

    @Override
    protected void generate() {
        dropSelfWithContents(MSBlocks.BLOCKS.getAllBlocks());
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return MSBlocks.BLOCKS.getAllBlocks().stream().map(IBlockProvider::getBlock).toList();
    }
}
