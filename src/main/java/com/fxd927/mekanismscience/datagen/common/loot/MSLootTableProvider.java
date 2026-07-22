package com.fxd927.mekanismscience.datagen.common.loot;

import com.fxd927.mekanismscience.datagen.common.loot.table.MSBlockLootSubProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;

public class MSLootTableProvider extends LootTableProvider {

    public MSLootTableProvider(PackOutput output) {
        super(output, Collections.emptySet(), List.of(
                new SubProviderEntry(MSBlockLootSubProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
