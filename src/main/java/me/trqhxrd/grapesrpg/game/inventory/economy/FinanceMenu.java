package me.trqhxrd.grapesrpg.game.inventory.economy;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import me.trqhxrd.menus.Menu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class FinanceMenu extends Menu implements Owneable<GrapesPlayer> {

    private final GrapesPlayer owner;

    public FinanceMenu(GrapesPlayer owner) {
        super("§cYour finances:", "", 3 * 9, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), false);
        this.owner = owner;
        this.setupMenu(this.getContent());
    }

    @Override
    public void setupMenu(Map<Integer, ItemStack> map) {
        map.put(11, new ItemBuilder(Material.HOPPER).setName("&cDeposit").setLore("&7Click this item to deposit a certain amount of coins onto your bank account.").build());
        map.put(15, new ItemBuilder(Material.CHEST_MINECART).setName("&bWithdraw").setLore("&7Click this item to withdraw a certain amount of coins from your bank account.").build());
    }

    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        if (e.getSlot() == 11) new DepositMenu(this.owner).open(this.owner.getWrappedObject());
        else if (e.getSlot() == 15) new WithdrawMenu(this.owner).open(this.owner.getWrappedObject());
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
