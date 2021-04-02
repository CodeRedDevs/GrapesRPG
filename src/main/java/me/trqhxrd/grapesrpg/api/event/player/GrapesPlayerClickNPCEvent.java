package me.trqhxrd.grapesrpg.api.event.player;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.NPC;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import org.bukkit.event.HandlerList;

/**
 * This Event gets called, if a Player clicks a NPC.
 *
 * @author Trqhxrd
 */
public class GrapesPlayerClickNPCEvent extends GrapesPlayerEvent {

    /**
     * Required by Bukkit.
     */
    private static final HandlerList handlerList = new HandlerList();

    /**
     * The type of the click.
     * This can either be left- or right-click.
     */
    private final ClickType clickType;
    /**
     * The NPC, that got clicked.
     */
    private final NPC npc;

    /**
     * This constructor creates a new GrapesEvent with the Plugins instance and the Player.
     *
     * @param player    The player for which the event got called.
     * @param clickType The type of the click. Can be left click or right click.
     * @param npc       The clicked npc.
     * @see GrapesPlayer
     */
    public GrapesPlayerClickNPCEvent(GrapesPlayer player, NPC npc, ClickType clickType) {
        super(Grapes.getGrapes(), player);
        this.npc = npc;
        this.clickType = clickType;
    }

    /**
     * Required by Bukkit.
     *
     * @return A HandlerList.
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    /**
     * Getter for the type of click.
     *
     * @return The type of click.
     * @see ClickType
     */
    public ClickType getClickType() {
        return clickType;
    }

    /**
     * Getter for the clicked NPC.
     *
     * @return The clicked NPC.
     */
    public NPC getNpc() {
        return npc;
    }

    /**
     * Required by Bukkit.
     */
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
