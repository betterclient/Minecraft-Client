package io.github.betterclient.version.mixin.client;

import io.github.betterclient.client.BallSack;
import io.github.betterclient.client.bridge.IBridge;
import io.github.betterclient.version.Version;
import io.github.betterclient.client.event.impl.HitEntityEvent;
import io.github.betterclient.fabric.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.navigation.GuiNavigationType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Nullable
    public HitResult crosshairTarget;

    @Inject(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"))
    public void attackSwing(CallbackInfoReturnable<Boolean> cir) {
        BallSack.getInstance().bus.call(new HitEntityEvent((IBridge.PlayerEntity) this.player, (IBridge.Entity) ((EntityHitResult) this.crosshairTarget).getEntity()));
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;<init>(Lnet/minecraft/client/MinecraftClient;)V"))
    public MinecraftClient red(MinecraftClient client) {
        new BallSack();

        return client;
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    public Thread hi() {
        try {
            FabricLoader.getInstance().callClientMain();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Thread.currentThread();
    }

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/navigation/GuiNavigationType;NONE:Lnet/minecraft/client/gui/navigation/GuiNavigationType;"))
    public GuiNavigationType initBridge() {
        try {
            Version.setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return GuiNavigationType.NONE;
    }
}
