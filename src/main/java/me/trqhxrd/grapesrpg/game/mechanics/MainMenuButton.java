package me.trqhxrd.grapesrpg.game.mechanics;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import me.trqhxrd.grapesrpg.game.inventory.PlayerMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * This class sets up a clickable button in the player's inventory.
 * It also adds an item, which keeps a slot free for backpacks.
 *
 * @author Trqhxrd
 */
@Register
public class MainMenuButton implements Owneable<GrapesPlayer>, Listener {

    /**
     * This constant defines the button, which should be added.
     * Maybe this will get a config entry later.
     */
    public static final ItemStack BUTTON = new ItemBuilder(Material.CHEST_MINECART).setName("&bEquipment").build();
    /**
     * This field stores the owner of this instance.
     */
    private final GrapesPlayer owner;

    /**
     * This constructor should not be used, because it can't be used for setting up a button in a player's inventory.
     * This constructor is only used for registering the button-listener at the start of the game.
     *
     * @deprecated Please use {@link MainMenuButton#MainMenuButton(GrapesPlayer)}.
     */
    @Deprecated
    public MainMenuButton() {
        this.owner = null;
    }

    /**
     * This method creates a new instance of this class and sets it's owner.
     * @param owner The player, who should receive the button.
     */
    public MainMenuButton(GrapesPlayer owner) {
        this.owner = owner;
        this.setup();
    }

    /**
     * This method sets the button in the inventory of the player.
     */
    public void setup() {
        if (this.owner != null) {
            this.owner.getWrappedObject().getInventory().setItem(34, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&cNo Backpack available").setLore("&7Backpacks are still work in progress...").build());
            this.owner.getWrappedObject().getInventory().setItem(35, BUTTON);
        }
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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory() instanceof PlayerInventory) {
                if (e.getSlot() == 34) Grapes.getGrapes().getUtils().sendMessage(e.getWhoClicked(), "&cBackpacks are currently not available!");
                else if (e.getSlot() == 35) new PlayerMenu(GrapesPlayer.getByPlayer((Player) e.getWhoClicked())).open(e.getWhoClicked());
                e.setCancelled(e.getSlot() == 34 || e.getSlot() == 35);
            }
        }
    }
}
