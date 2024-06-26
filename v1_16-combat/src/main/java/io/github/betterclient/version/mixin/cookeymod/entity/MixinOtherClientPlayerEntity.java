package io.github.betterclient.version.mixin.cookeymod.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OtherClientPlayerEntity.class)
public abstract class MixinOtherClientPlayerEntity extends AbstractClientPlayerEntity {
    public MixinOtherClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void fixLocalPlayerHandling(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
