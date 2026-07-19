package com.fxd927.mekanismscience.common.mixin;

import com.fxd927.mekanismscience.common.util.MSBoundingHitResultUtils;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl {

    @Shadow
    public ServerPlayer player;

    @Redirect(method = "handleUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ServerboundUseItemOnPacket;getHitResult()Lnet/minecraft/world/phys/BlockHitResult;"))
    private BlockHitResult mekanismScience$useActualBoundingBlock(ServerboundUseItemOnPacket packet) {
        return MSBoundingHitResultUtils.normalize(player.serverLevel(), packet.getHitResult());
    }
}
