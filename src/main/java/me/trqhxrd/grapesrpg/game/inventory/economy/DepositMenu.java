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

public class DepositMenu extends Menu implements Owneable<GrapesPlayer> {

    public static final List<Integer> DEPOSITION_OPTIONS = Arrays.asList(1, 10, 100, 1000, 10000, 100000, -1);
    private final GrapesPlayer owner;

    public DepositMenu(GrapesPlayer owner) {
        super("§cSelect the amount:", "menu_finance_deposit_" + owner.getName(), 3 * 9, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), false);
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
        for (int option : DEPOSITION_OPTIONS) {
            if (option != -1) {
                boolean possible = this.owner.getEcoSet().wouldBeInDeptIfPayed(option);
                char c = (possible ? 'c' : 'a');
                map.put(i, new ItemBuilder(Material.FURNACE_MINECART).setName("&" + c + "Deposit: &e" + option).setLore("&7Click to deposit &e" + option + " Coin" + (option != 1 ? "s" : "") + " &7in your bank account.").build());
            } else
                map.put(i, new ItemBuilder(Material.FURNACE_MINECART).setName("&aDeposit: &eeverything").setLore("&7Click to deposit &eall your Coins &7in your bank account.").build());
            i++;
        }
    }

    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        for (int i = 0; i < DEPOSITION_OPTIONS.size(); i++) {
            int option = DEPOSITION_OPTIONS.get(i);
            int slot = i + 10;
            if (e.getSlot() == slot) {
                if (this.owner.getEcoSet().getWallet() > 0)
                    this.owner.getWrappedObject().playNote(this.owner.getWrappedObject().getEyeLocation(), Instrument.BELL, Note.natural(1, Note.Tone.E));
                else this.owner.getWrappedObject().playSound(this.owner.getWrappedObject().getEyeLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1, 1);

                if (option != -1 && !this.owner.getEcoSet().wouldBeInDeptIfPayed(option)) this.owner.getEcoSet().deposit(option);
                else this.owner.getEcoSet().deposit(this.owner.getEcoSet().getWallet());
                break;
            }
        }
        e.setCancelled(true);
    }

    @Override
    public void handleMenuClose(InventoryCloseEvent e) {

    }
}
