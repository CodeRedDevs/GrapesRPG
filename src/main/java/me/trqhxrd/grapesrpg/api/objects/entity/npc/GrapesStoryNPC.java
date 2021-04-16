package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.objects.entity.npc.actions.NPCClickActionTalk;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.talking.NPCMessageEngine;
import me.trqhxrd.grapesrpg.game.config.NPCLocationsConfig;
import net.citizensnpcs.trait.Gravity;
import org.bukkit.Location;

/**
 * This is a NPC, which sends a message once it got clicked.
 * You can also send multiple lines or different messages by different chances.
 *
 * @author Trqhxrd
 */
public class GrapesStoryNPC extends GrapesNPC {

    /**
     * The Message Engine sends and manages messages.
     */
    private final NPCMessageEngine messageEngine;

    /**
     * This constructor creates a new NPC.
     *
     * @param name        The name of the NPC. ColorCodes are supported.
     * @param locationKey The locationKey for the NPCs Location.
     * @param skin        The Skin of the npc.
     */
    public GrapesStoryNPC(String name, String locationKey, Skin skin) {
        this(name, NPCLocationsConfig.getLocation(locationKey), skin);
    }

    /**
     * This constructor creates a new NPC.
     *
     * @param name  The name of the NPC. ColorCodes are supported.
     * @param spawn The location, where the NPC should be spawned.
     * @param skin  The Skin of the npc.
     */
    public GrapesStoryNPC(String name, Location spawn, Skin skin) {
        super(name, spawn, skin);
        this.messageEngine = new NPCMessageEngine(this);
        this.setClickAction(new NPCClickActionTalk());
        this.getWrappedObject().getOrAddTrait(Gravity.class).setEnabled(false);
        this.spawn();
    }

    /**
     * Getter for the NPCs Message Engine.
     *
     * @return The Message Engine of the NPC.
     */
    public NPCMessageEngine getMessageEngine() {
        return messageEngine;
    }
}
