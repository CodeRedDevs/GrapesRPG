package me.trqhxrd.grapesrpg.event.other;

import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * This Listener formats the player chat messages.
 *
 * @author Trqhxrd
 */
@Register
public class AsyncPlayerChatListener implements Listener {

    /**
     * The Handler-Method.
     *
     * @param e An AsyncPlayerChatEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {

        String color;
        if (e.getPlayer().isOp()) color = "&#e83333";
        else color = "&#e7ed2f";

        e.setFormat(Utils.toChatMessage(color, e.getPlayer().getName(), e.getMessage()));
    }
}
