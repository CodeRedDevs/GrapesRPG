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
        StringBuilder builder = new StringBuilder();
        if (e.getPlayer().isOp()) builder.append("&#e83333");
        else builder.append("&#e7ed2f");
        builder.append(e.getPlayer().getName()).append(" &#1ed67a&l>>> &7").append(e.getMessage());
        e.setFormat(Utils.translateColorCodes(builder.toString()));
    }
}
