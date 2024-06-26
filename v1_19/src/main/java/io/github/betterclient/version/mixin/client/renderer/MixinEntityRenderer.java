package io.github.betterclient.version.mixin.client.renderer;

import io.github.betterclient.client.command.impl.MarkCommand;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    public int color = new Color(255, 0, 0).getRGB();
    public int sneakingColor = new Color(255, 0, 0, 120).getRGB();

    @Redirect(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"))
    public int redirectCall(TextRenderer instance, Text text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light) {
        if(((Object) this) instanceof PlayerEntityRenderer) {
            for (String mark : MarkCommand.markedPlayers) {
                if(text.getString().toLowerCase().contains(mark.toLowerCase())) {
                    if(color == 553648127) {
                        color = sneakingColor;
                    } else {
                        color = this.color;
                    }
                }
            }
        }

        return instance.draw(text, x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light);
    }
}