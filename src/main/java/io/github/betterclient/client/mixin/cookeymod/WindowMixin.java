package io.github.betterclient.client.mixin.cookeymod;

import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.*;

@Mixin(Window.class)
public abstract class WindowMixin implements AutoCloseable {
    @Shadow @Final private long handle;

    @Shadow public abstract boolean isFullscreen();

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwWindowHint(II)V", ordinal = 4, remap = false))
    public void disableAutoIconify(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        glfwWindowHint(GLFW_AUTO_ICONIFY, GLFW_FALSE);
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void iconifyIfBlocking(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        glfwSetWindowFocusCallback(this.handle, (window, focussed) -> {
            if (this.isFullscreen() && !focussed && glfwGetWindowAttrib(window, GLFW_HOVERED) == 1) {
                glfwIconifyWindow(window);
            }
        });
    }
}
