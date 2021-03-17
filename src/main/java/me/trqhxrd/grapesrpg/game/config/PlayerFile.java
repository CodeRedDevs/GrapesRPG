package me.trqhxrd.grapesrpg.game.config;

import com.google.gson.reflect.TypeToken;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.utils.config.Config;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This config stores all data about a player.
 *
 * @author Trqhxrd
 */
public class PlayerFile extends Config implements Owneable<GrapesPlayer> {
    /**
     * This field stores the owner of this Config-File.
     */
    private final GrapesPlayer owner;

    /**
     * This creates a new player-file for the player given in the arguments.
     *
     * @param player The player, who owns this file.
     */
    public PlayerFile(GrapesPlayer player) {
        super(new File(Grapes.getGrapes().getDataFolder(), "player/" + player.getUniqueId().toString() + ".yml"));
        this.owner = player;
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public GrapesPlayer getOwner() {
        return owner;
    }

    public Group2<Map<Integer, ItemStack>, Integer> getEnderChest() {
        Group2<Map<Integer, ItemStack>, Integer> data = new Group2<>();
        Map<Integer, ItemStack> content = new HashMap<>();
        if (this.contains("enderchest.content")) content = Grapes.GSON.fromJson(this.getString("enderchest.content"), new TypeToken<Map<Integer, ItemStack>>() {
        }.getType());

        int size = 0;
        if (this.contains("enderchest.size")) size = this.getInt("enderchest.size");

        data.setX(content);
        data.setY(size);

        return data;
    }
}
