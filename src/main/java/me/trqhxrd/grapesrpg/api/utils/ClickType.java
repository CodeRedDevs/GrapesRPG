package me.trqhxrd.grapesrpg.api.utils;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.packet.PacketReader;

/**
 * Everytime a NPC gets clicked by a {@link GrapesPlayer}, the GrapesPlayers {@link PacketReader}
 * will run a task, which executes all the NPCs tasks.
 * While running the NPCs tasks, the type of the click transmitted using this Enum.
 *
 * @author Trqhxrd
 */
public enum ClickType {
    /**
     * This is the state, which will be used, if the type of click was a left-click.
     */
    LEFT,
    /**
     * This is the state, which will be used, if the type of click was a right-click.
     */
    RIGHT
}
