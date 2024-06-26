package io.github.betterclient.version.mixin.cookeymod.entity;

import io.github.betterclient.version.mods.CookeyMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getHandSwingProgress", at = @At(value = "RETURN", shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void changeAttackAnim(float f, CallbackInfoReturnable<Float> cir, float g, float h) {
        if (CookeyMod.get().oldSwing.isValue()) {
            cir.setReturnValue(h);
        }
    }
}
