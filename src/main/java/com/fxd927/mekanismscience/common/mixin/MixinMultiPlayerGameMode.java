package com.fxd927.mekanismscience.common.mixin;

import com.fxd927.mekanismscience.common.util.MSBoundingHitResultUtils;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {

    @ModifyVariable(method = "useItemOn", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private BlockHitResult mekanismScience$useActualBoundingBlock(BlockHitResult result, LocalPlayer player, InteractionHand hand) {
        Level level = player.level();
        return MSBoundingHitResultUtils.normalize(level, result);
    }
}
