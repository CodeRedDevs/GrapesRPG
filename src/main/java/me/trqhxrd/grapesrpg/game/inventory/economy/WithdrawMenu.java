package me.trqhxrd.grapesrpg.game.inventory.economy;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import me.trqhxrd.menus.Menu;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This meu will be shown everytime the player clicks the "withdraw"-button in the {@link FinanceMenu}.
 *
 * @author Trqhxrd
 */
public class WithdrawMenu extends Menu implements Owneable<GrapesPlayer> {

    /**
     * This list contains all the different amounts of coins, that can be withdrawn.
     */
    public static final List<Integer> WITHDRAW_OPTIONS = Arrays.asList(1, 10, 100, 1000, 10000, 100000, -1);
    /**
     * This field contains the owner of this menu.
     * In this case the owner is the player from which the account should be changed.
     */
    private final GrapesPlayer owner;

    /**
     * This constructor creates a new WithdrawMenu.
     *
     * @param owner The Player from which the finances should be changed.
     */
    public WithdrawMenu(GrapesPlayer owner) {
        super("§cSelect the amount:", "menu_finance_withdraw_" + owner.getName(), 3 * 9, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), false);
        this.owner = owner;
        this.setupMenu(this.getContent());
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

    @Override
    public void setupMenu(Map<Integer, ItemStack> map) {
        int i = 10;
        for (int option : WITHDRAW_OPTIONS) {
            if (option != -1) {
                boolean possible = this.owner.getEcoSet().getBank() < option;
                char c = (possible ? 'c' : 'a');
                map.put(i, new ItemBuilder(Material.FURNACE_MINECART).setName("&" + c + "Withdraw: &e" + option).setLore("&7Click to withdraw &e" + option + " Coin" + (option != 1 ? "s" : "") + " &7from your bank account.").build());
            } else
                map.put(i, new ItemBuilder(Material.FURNACE_MINECART).setName("&aWithdraw: &eeverything").setLore("&7Click to withdraw &eall your Coins &7from your bank account.").build());
            i++;
        }
    }

    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        for (int i = 0; i < WITHDRAW_OPTIONS.size(); i++) {
            int option = WITHDRAW_OPTIONS.get(i);
            int slot = i + 10;
            if (e.getSlot() == slot) {
                if (this.owner.getEcoSet().getBank() > 0)
                    this.owner.getWrappedObject().playNote(this.owner.getWrappedObject().getEyeLocation(), Instrument.BELL, Note.natural(1, Note.Tone.E));
                else this.owner.getWrappedObject().playSound(this.owner.getWrappedObject().getEyeLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1, 1);

                if (option != -1 && this.owner.getEcoSet().getBank() >= option) this.owner.getEcoSet().withdraw(option);
                else this.owner.getEcoSet().withdraw(this.owner.getEcoSet().getBank());
                break;
            }
        }
        this.updateInventory();
        this.open(e.getWhoClicked());
        e.setCancelled(true);
    }

    @Override
    public void handleMenuClose(InventoryCloseEvent e) {

    }
}
