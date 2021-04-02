package me.trqhxrd.grapesrpg.game.config;

import com.google.gson.reflect.TypeToken;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.Artifact;
import me.trqhxrd.grapesrpg.api.objects.item.artifact.ArtifactState;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

/**
 * This config stores all artifacts and their current states as soon as the server gets stopped.
 * It also loads them as soon as the server starts.
 * The file, in which this stuff is stored is "plugins/GrapesRPG/artifacts.json" in your server root-folder.
 *
 * @author Trqhxrd
 */
public class ArtifactConfig {

    /**
     * This field contains the file, which stores all the artifacts.
     */
    private static final File file = new File(Grapes.getGrapes().getDataFolder(), "artifacts.json");

    /**
     * This method initializes the file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init() {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method loads all the artifacts from the config and puts them into the Artifact-Map.
     */
    public static void loadArtifacts() {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder s = new StringBuilder();
            while (scanner.hasNextLine()) s.append(scanner.nextLine());

            Map<Integer, Artifact> map = Grapes.GSON.fromJson(s.toString(), new TypeToken<Map<Integer, Artifact>>() {
            }.getType());

            if (map != null) {
                Artifact.getArtifacts().clear();
                Artifact.getArtifacts().putAll(map);
                Artifact.getArtifacts().forEach((i, a) -> {
                    if (a.getState() == ArtifactState.SPAWNED) a.spawn(a.getLocation(), true);
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves the artifacts back into the file.
     *
     * @param deleteItemOnSave If this is set to true, all the items on the ground will be deleted. This is useful if you want to prevent some duping glitches. It should only be used on server-stop tho.
     */
    public static void saveArtifacts(boolean deleteItemOnSave) {
        Artifact.updateLocations();
        if (deleteItemOnSave) {
            Artifact.getArtifacts().forEach((id, a) -> {
                if (a.getEntity() != null) {
                    a.setLocation(a.getEntity().getLocation());
                    a.getEntity().remove();
                    a.setEntity(null);
                }
            });
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(Grapes.GSON.toJson(Artifact.getArtifacts()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
