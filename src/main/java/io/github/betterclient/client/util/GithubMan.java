package io.github.betterclient.client.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.github.betterclient.client.bridge.IBridge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * github man
 */
public class GithubMan {
    public String commitId = "";
    public String branch = "";

    public GithubMan() {
        try {
            List<String> text = Files.readAllLines(toPath(GithubMan.class.getResourceAsStream("/ballsack/github/github.txt")));

            for(String line : text) {
                if(line.startsWith("git.branch=")) {
                    branch = line.replace("git.branch=", "");
                }

                if(line.startsWith("git.commit.id.abbrev=")) {
                    commitId = line.replace("git.commit.id.abbrev=", "");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Path toPath(InputStream is) {
        try {
            File gitFile = File.createTempFile("git", ".txt");
            FileOutputStream fos = new FileOutputStream(gitFile);

            fos.write(is.readAllBytes());

            fos.close();
            is.close();

            return gitFile.toPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUpdate() {
        try {
            URL url = new URL("https://api.github.com/repos/betterclient/Minecraft-Client/commits");
            InputStream is = url.openStream();
            byte[] bites = is.readAllBytes();
            is.close();

            JsonArray array = new JsonParser().parse(new String(bites)).getAsJsonArray();
            String version = array.get(0).getAsJsonObject().get("sha").getAsString();
            return !version.startsWith(this.commitId);
        } catch (IOException ex) {
            IBridge.getPreLaunch().error(ex.toString());}

        return false;
    }
}
