package com.fxd927.mekanismscience.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class SensoryParalysis extends MobEffect {

    public SensoryParalysis(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.setInvulnerable(true);
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

        if (entity instanceof Player player) {
            if (player.isInvulnerable()) {
                player.setInvulnerable(false);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
