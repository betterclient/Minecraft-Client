package io.github.betterclient.version;

import io.github.betterclient.client.Application;
import io.github.betterclient.fabric.FabricLoader;
import io.github.betterclient.quixotic.Side;
import io.github.betterclient.version.transformers.PlayerInteractEntityC2SPacketEditor;
import io.github.betterclient.quixotic.QuixoticApplication;
import io.github.betterclient.quixotic.QuixoticClassLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VersionApplication implements QuixoticApplication {
    @Override
    public String getApplicationName() {
        return "Minecraft";
    }

    @Override
    public String getApplicationVersion() {
        return "1.19.4";
    }

    @Override
    public String getMainClass() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public void loadApplicationManager(QuixoticClassLoader quixoticClassLoader) {
        quixoticClassLoader.addPlainTransformer(new PlayerInteractEntityC2SPacketEditor());

        Application.mcVersionFolder = new File(Application.mcDownloadsFolder, "1.19.4");
        Application.customJarsFolder = new File(Application.customJarsFolder, "1.19.4");
        Application.remappedModsFolder = new File(Application.remappedModsFolder, "1.19.4");
        Application.load(quixoticClassLoader);
    }

    @Override
    public List<String> getMixinConfigurations() {
        ArrayList<String> arrayList = new ArrayList<>(FabricLoader.getInstance().getMixinConfigurations());

        arrayList.add("v1_19.mixins.json");

        return arrayList;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
