package me.trqhxrd.grapesrpg.game.config;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.utils.config.Config;

import java.io.File;

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
}
