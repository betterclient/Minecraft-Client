package io.github.betterclient.client.mod;

import io.github.betterclient.client.bridge.IBridge;
import io.github.betterclient.client.event.EventTarget;
import io.github.betterclient.client.event.impl.RenderEvent;
import io.github.betterclient.client.mod.setting.BooleanSetting;
import io.github.betterclient.client.mod.setting.ColorSetting;

import java.awt.*;

public abstract class HUDModule extends Module {
    public Renderable renderable;

    public ColorSetting backgroundColor = new ColorSetting("Background Color", new Color(0,0,0,84));
    public BooleanSetting backGround = new BooleanSetting("Render Background", false);
    public ColorSetting textColor = new ColorSetting("Text Color", Color.WHITE);
    public BooleanSetting forceVanillaFont = new BooleanSetting("Force vanilla font", false);

    public HUDModule(String name, int x, int y, IBridge.Identifier icon) {
        super(name, Category.HUD, icon);
        this.renderable = new Renderable(x, y, this);
        this.addSetting(backGround);
        this.addSetting(backgroundColor);
        this.addSetting(textColor);
        this.addSetting(forceVanillaFont);
    }

    public abstract void render(Renderable renderable);

    @EventTarget
    public void render(RenderEvent event) {
        renderable.renderBackground = backGround.isValue();
        renderable.backgroundColor = backgroundColor.getColor();

        if(this.forceVanillaFont.value) {
            this.renderable.textRenderer = IBridge.MinecraftClient.getInstance().getMCRenderer();
        } else {
            this.renderable.textRenderer = IBridge.MinecraftClient.getInstance().getTextRenderer();
        }

        this.renderable.reset();
        this.render(this.renderable);
        this.renderable.render();
    }

    public static HUDModule cast(Module module) {
        return (HUDModule) module;
    }
}
