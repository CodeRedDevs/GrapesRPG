package me.trqhxrd.grapesrpg.game.inventory;

import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.inventory.GrapesInventory;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * This menu is used for displaying all data about every Skill in a good visible and understandable way to the player.
 *
 * @author Trqhxrd
 */
public class SkillsMenu extends GrapesInventory implements Owneable<Skills> {

    /**
     * The skill-set, that is displayed.
     */
    private final Skills skills;

    /**
     * This constructor creates a new inventory and sets it up.
     *
     * @param skills The collection of skills, that should be displayed.
     */
    public SkillsMenu(Skills skills) {
        super("&#524df0Skills:", MenuSize.NINE_FIVE, false, true);
        this.skills = skills;
        this.setupMenu();
    }

    /**
     * This method gets called everytime the inventory gets closed.
     *
     * @param event The Event with all information about the closing.
     */
    @Override
    public void handleMenuClose(InventoryCloseEvent event) {
    }

    /**
     * This method handles all Player interaction with the inventory.
     *
     * @param event The Event, which you want to handle.
     */
    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    /**
     * This method sets all the default-items in the inventory.
     */
    @Override
    public void setupMenu() {
        this.skills.forEach((key, skill) -> {
            int slot = skill.getSlot();
            this.getInventory().setItem(slot, skill.getDisplayItem());
        });
    }

    /**
     * This method opens the inventory for the player specified in the method arguments.
     *
     * @param player The HumanEntity which should open the inventory.
     */
    @Override
    public void openInventory(HumanEntity player) {
        this.setupMenu();
        super.openInventory(player);
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public Skills getOwner() {
        return skills;
    }
}
