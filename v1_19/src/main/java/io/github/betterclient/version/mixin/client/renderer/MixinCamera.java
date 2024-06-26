package io.github.betterclient.version.mixin.client.renderer;

import io.github.betterclient.client.mod.impl.other.FreeLook;
import io.github.betterclient.version.access.CameraControl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Unique
    boolean firstTime = true;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", ordinal = 0, shift = At.Shift.AFTER))
    public void lockRotation(BlockView focusedBlock, Entity cameraEntity, boolean isThirdPerson, boolean isFrontFacing, float f, CallbackInfo ci) {
        if (!(cameraEntity instanceof ClientPlayerEntity)) return;

        if (FreeLook.get().perspectiveToggled) {
            CameraControl cameraControl = (CameraControl) cameraEntity;
            MinecraftClient client = MinecraftClient.getInstance();

            if (firstTime && client.player != null) {
                cameraControl.setCameraPitch(client.player.getPitch());
                cameraControl.setCameraYaw(client.player.getYaw());
                firstTime = false;
            }

            this.setRotation(cameraControl.getCameraYaw(), cameraControl.getCameraPitch());
        } else {
            firstTime = true;
        }
    }
}
