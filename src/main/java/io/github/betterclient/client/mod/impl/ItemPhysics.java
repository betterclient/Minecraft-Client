package io.github.betterclient.client.mod.impl;

import io.github.betterclient.client.BallSack;
import io.github.betterclient.client.mod.Category;
import io.github.betterclient.client.mod.Module;

public class ItemPhysics extends Module {
    public ItemPhysics() {
        super("Item Physics", Category.OTHER);
    }

    public static boolean isEnabled() {
        return BallSack.getInstance().moduleManager.getModuleByName("Item Physics").toggled;
    }
}