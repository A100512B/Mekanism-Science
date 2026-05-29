package com.fxd927.mekanismscience.common.mixin;

import mekanism.common.block.attribute.AttributeStateActive;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AttributeStateActive.class, remap = false)
public interface AttributeStateActiveAccessor {

    @Accessor("activeProperty")
    static BooleanProperty getActiveProperty() {
        throw new AssertionError();
    }
}
