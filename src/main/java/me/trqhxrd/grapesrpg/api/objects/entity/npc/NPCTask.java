package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;

/**
 * Classes, which implement this interface, can be added to an NPC.
 * The {@link NPCTask#click(GrapesPlayer, ClickType)}-method will be run, as soon as the NPC, who has this Task added, got clicked by a player.
 * A task can be added using the {@link NPC#addTask(NPCTask...)}-method.
 *
 * @author Trqhxrd
 */
public interface NPCTask {

    /**
     * This method will be run as soon as the NPC, who has this tasks added, gets clicked.
     *
     * @param operator The Player, who clicked the NPC.
     * @param type     The type of the click. (left or right-click)
     */
    void click(GrapesPlayer operator, ClickType type);
}
