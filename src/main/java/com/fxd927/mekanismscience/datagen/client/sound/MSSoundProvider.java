package com.fxd927.mekanismscience.datagen.client.sound;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.registries.MSSounds;
import mekanism.common.registration.impl.SoundEventRegistryObject;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class MSSoundProvider extends SoundDefinitionsProvider {

    public MSSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MekanismScience.MODID, helper);
    }

    @Override
    public void registerSounds() {
        addTiles();
    }

    private void addTiles() {
        String basePath = "tile/";
        addSoundEventWithSubtitle(MSSounds.ACID_LEACHER, basePath + "acid_leacher");
        addSoundEventWithSubtitle(MSSounds.AIR_COMPRESSOR, basePath + "air_compressor");
        addSoundEventWithSubtitle(MSSounds.ANTI_EXTRACTING_PLANT, basePath + "anti_extracting_plant");
        addSoundEventWithSubtitle(MSSounds.EXTRACTING_PLANT, basePath + "extracting_plant");
        addSoundEventWithSubtitle(MSSounds.METAL_ELECTROLYSIS_CHAMBER, basePath + "metal_electrolysis_chamber");
        addSoundEventWithSubtitle(MSSounds.PRESSURIZED_POLYMERIZING_CHAMBER, basePath + "pressurized_polymerizing_chamber");
    }

    private void addSoundEventWithSubtitle(SoundEventRegistryObject<?> soundEventRO, String path) {
        add(soundEventRO.get(), definition().subtitle(soundEventRO.getTranslationKey()).with(sound(ResourceLocation.fromNamespaceAndPath(MekanismScience.MODID, path))));
    }
}
