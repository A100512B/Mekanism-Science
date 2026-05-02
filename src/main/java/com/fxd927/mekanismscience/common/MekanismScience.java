package com.fxd927.mekanismscience.common;

import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.content.anti_extraction.AntiExtractingPlantValidator;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberMultiblockData;
import com.fxd927.mekanismscience.common.content.electrolysis.MetalElectrolysisChamberValidator;
import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantMultiblockData;
import com.fxd927.mekanismscience.common.content.extraction.ExtractingPlantValidator;
import com.fxd927.mekanismscience.common.registries.*;
import com.mojang.logging.LogUtils;
import mekanism.common.lib.multiblock.MultiblockCache;
import mekanism.common.lib.multiblock.MultiblockManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MekanismScience.MODID)
public class MekanismScience {

    public static final String MODID = "mekanismscience";
    public static final String MOD_NAME = "Mekanism: Science";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MultiblockManager<ExtractingPlantMultiblockData> extractingPlantManager = new MultiblockManager<>("extractingPlant", MultiblockCache::new, ExtractingPlantValidator::new);
    public static final MultiblockManager<AntiExtractingPlantMultiblockData> antiExtractingPlantManager = new MultiblockManager<>("antiExtractingPlant", MultiblockCache::new, AntiExtractingPlantValidator::new);
    public static final MultiblockManager<MetalElectrolysisChamberMultiblockData> metalElectrolysisChamberManager = new MultiblockManager<>("metalElectrolysisChamber", MultiblockCache::new, MetalElectrolysisChamberValidator::new);

    @SuppressWarnings("removal")
    public MekanismScience() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MSConfig.registerConfigs(ModLoadingContext.get());
        MSCreativeTabs.CREATIVE_TABS.register(modEventBus);
        MSBlocks.BLOCKS.register(modEventBus);
        MSContainerTypes.CONTAINER_TYPES.register(modEventBus);
        MSEffects.MOB_EFFECTS.register(modEventBus);
        MSFluids.FLUIDS.register(modEventBus);
        MSGases.GASES.register(modEventBus);
        MSItems.ITEMS.register(modEventBus);
        MSRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MSSounds.SOUND_EVENTS.register(modEventBus);
        MSTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("removal")
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MekanismScience.MODID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
