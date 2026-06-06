package com.fxd927.mekanismscience.datagen;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.datagen.client.lang.MSEnUsLangProvider;
import com.fxd927.mekanismscience.datagen.client.lang.MSZhCnLangProvider;
import com.fxd927.mekanismscience.datagen.client.model.MSItemModelProvider;
import com.fxd927.mekanismscience.datagen.client.sound.MSSoundProvider;
import com.fxd927.mekanismscience.datagen.client.state.MSBlockStateProvider;
import com.fxd927.mekanismscience.datagen.common.loot.MSLootTableProvider;
import com.fxd927.mekanismscience.datagen.common.recipe.MSRecipeProvider;
import com.fxd927.mekanismscience.datagen.common.recipe.compat.ATMRecipeProvider;
import com.fxd927.mekanismscience.datagen.common.recipe.compat.ForgeRecipeProvider;
import com.fxd927.mekanismscience.datagen.common.tag.MSTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = MekanismScience.MODID, bus = Bus.MOD)
public class MSDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        bootstrapConfigs(MekanismScience.MODID);
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new MSEnUsLangProvider(output));
        generator.addProvider(event.includeClient(), new MSZhCnLangProvider(output));
        generator.addProvider(event.includeClient(), new MSSoundProvider(output, helper));
        generator.addProvider(event.includeClient(), new MSBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new MSItemModelProvider(output, helper));
        generator.addProvider(event.includeServer(), new MSLootTableProvider(output));
        generator.addProvider(event.includeServer(), new MSRecipeProvider(output));
        if (ModList.get().isLoaded("allthemodium"))
            generator.addProvider(event.includeServer(), new ATMRecipeProvider(output));
        if (ModList.get().isLoaded("alltheores"))
            generator.addProvider(event.includeServer(), new ForgeRecipeProvider(output));
        generator.addProvider(event.includeServer(), new MSTagProvider(output, event.getLookupProvider(), helper));
    }

    public static void bootstrapConfigs(String modid) {
        ConfigTracker.INSTANCE.configSets().forEach((type, configs) -> {
            for (ModConfig config : configs) {
                if (config.getModId().equals(modid)) {
                    //Similar to how ConfigTracker#loadDefaultServerConfigs works for loading default server configs on the client
                    // except we don't bother firing an event as it is private, and we are already at defaults if we had called earlier,
                    // and we also don't fully initialize the mod config as the spec is what we care about, and we can do so without having
                    // to reflect into package private methods
                    CommentedConfig commentedConfig = CommentedConfig.inMemory();
                    config.getSpec().correct(commentedConfig);
                    config.getSpec().acceptConfig(commentedConfig);
                }
            }
        });
    }
}
