package com.fxd927.mekanismscience.client;

import com.fxd927.mekanismscience.client.gui.machine.*;
import com.fxd927.mekanismscience.client.gui.multiblock.GuiAntiExtractingPlant;
import com.fxd927.mekanismscience.client.gui.multiblock.GuiExtractingPlant;
import com.fxd927.mekanismscience.client.gui.multiblock.GuiMetalElectrolysisChamber;
import com.fxd927.mekanismscience.client.model.baked.AcidLeacherModel;
import com.fxd927.mekanismscience.client.render.tileentity.RenderAntiExtractingPlant;
import com.fxd927.mekanismscience.client.render.tileentity.RenderExtractingPlant;
import com.fxd927.mekanismscience.client.render.tileentity.RenderMetalElectrolysisChamber;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSContainerTypes;
import com.fxd927.mekanismscience.common.registries.MSTileEntityTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mekanism.client.ClientRegistration.addCustomModel;

@Mod.EventBusSubscriber(modid = MekanismScience.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MSClientRegistration {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        addCustomModel(MSBlocks.ACID_LEACHER, (orig, evt) -> new AcidLeacherModel(orig));
    }

    @SubscribeEvent
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MSContainerTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, GuiAdsorptionTypeSeawaterMetalExtractor::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.ORGANIC_LIQUID_EXTRACTOR, GuiOrganicLiquidExtractor::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.SEAWATER_PUMP, GuiSeawaterPump::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.PRESSURIZED_POLYMERIZING_CHAMBER, GuiPressurizedPolymerizerChamber::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.EXTRACTING_PLANT, GuiExtractingPlant::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.ANTI_EXTRACTING_PLANT, GuiAntiExtractingPlant::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.METAL_ELECTROLYSIS_CHAMBER, GuiMetalElectrolysisChamber::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.ACID_LEACHER, GuiAcidLeacher::new);
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderExtractingPlant::new, MSTileEntityTypes.EXTRACTING_PLANT_CASING, MSTileEntityTypes.EXTRACTING_PLANT_PORT);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderAntiExtractingPlant::new, MSTileEntityTypes.ANTI_EXTRACTING_PLANT_CASING, MSTileEntityTypes.ANTI_EXTRACTING_PLANT_PORT);
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderMetalElectrolysisChamber::new, MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER_CASING, MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER_PORT,
                MSTileEntityTypes.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR);
    }

    private MSClientRegistration(){
    }
}
