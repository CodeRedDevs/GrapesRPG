package me.trqhxrd.grapesrpg.game.inventory;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import me.trqhxrd.grapesrpg.game.inventory.economy.FinanceMenu;
import me.trqhxrd.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * This menu will be opened if a player clicks the little chest-minecart in his inventory.
 * @author Trqhxrd
 */
public class PlayerMenu extends Menu implements Owneable<GrapesPlayer> {

    /**
     * This field contains the owner of this menu.
     */
    private final GrapesPlayer owner;

    /**
     * This constructor creates a new inventory and adds items to it.
     * @param owner The owner, from who the finance-data and other things will be loaded.
     */
    public PlayerMenu(GrapesPlayer owner) {
        super("§bEquipment", "player_equipment_" + owner.getName(), 5 * 9, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), false);
        this.owner = owner;
        this.setupMenu(this.getContent());
    }

    @Override
    public void setupMenu(Map<Integer, ItemStack> map) {
        for (int i = 0; i < this.getSize(); i++) map.put(i, this.getBackground());
        map.put(11, new ItemBuilder(Material.CHEST).setName("&dAccessories").setLore("&7Click to open the accessories menu.").build());
        map.put(13, new ItemBuilder(Material.FURNACE_MINECART).setName("&eWallet").setLore("&dWallet: &c" + this.owner.getEcoSet().getWallet(), "&dBank: &c" + this.owner.getEcoSet().getBank()).build());
        map.put(15, new ItemBuilder(Material.ENDER_CHEST).setName("&5Ender Chest").setLore("&7Click to open your personal enderchest.").build());
        map.put(29, new ItemBuilder(Material.CRAFTING_TABLE).setName("&cCrafting Table").setLore("&7Click to open a crafting-menu.").build());
        map.put(31, new ItemBuilder(Material.NETHER_STAR).setName("&bSkills").setLore("&7Click to open your skill-menu").build());
        map.put(33, new ItemBuilder(Material.ANVIL).setName("&7Anvil").setLore("&7Click to open an anvil").build());
        map.put(44, new ItemBuilder(Material.BARRIER).setName("&cClose").setLore("&7clos.").build());
    }

    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        switch (e.getSlot()) {
            case 11:
                // TODO: 29.03.2021 Add artifacts 'n' shit
                break;
            case 13:
                new FinanceMenu(this.owner).open(this.owner.getWrappedObject());
                break;
            case 15:
                this.owner.getEnderChest().open(this.owner.getWrappedObject());
                break;
            case 29:
                new CraftingMenu().open(this.owner.getWrappedObject());
                break;
            case 31:
                this.owner.getSkills().getMenu().open(this.owner.getWrappedObject());
                break;
            case 33:
                this.owner.getWrappedObject().openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
                break;
            case 44:
                e.getWhoClicked().closeInventory();
                break;
            default:
                break;
        }
        e.setCancelled(true);
    }

    @Override
    public void handleMenuClose(InventoryCloseEvent e) {
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
