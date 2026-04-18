package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.common.registration.impl.SoundEventDeferredRegister;
import mekanism.common.registration.impl.SoundEventRegistryObject;
import net.minecraft.sounds.SoundEvent;

public class MSSounds {

    private MSSounds() {}

    public static final SoundEventDeferredRegister SOUND_EVENTS = new SoundEventDeferredRegister(MekanismScience.MODID);

    public static final SoundEventRegistryObject<SoundEvent> EXTRACTING_PLANT = SOUND_EVENTS.register("tile.machine.extracting_plant");
    public static final SoundEventRegistryObject<SoundEvent> ANTI_EXTRACTING_PLANT = SOUND_EVENTS.register("tile.machine.anti_extracting_plant");
}
