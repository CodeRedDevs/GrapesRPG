package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.ClickType;

/**
 * This interface represents an action, that can be executed, if you click on the NPC.
 *
 * @author Trqhxrd
 */
public interface NPCClickAction {

    /**
     * This field contains the default-action, that will be executed, if the NPC gets clicked.
     */
    NPCClickAction DEFAULT = (player, npc, type) -> {
    };

    /**
     * This method will be executed, if a player clicks on a NPC.
     *
     * @param player The Player, who clicked the NPC.
     * @param npc    The NPC, which got clicked.
     * @param type   The type of the click. This can either be LEFT or RIGHT.
     */
    void onClick(GrapesPlayer player, GrapesNPC npc, ClickType type);
}
