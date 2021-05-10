package me.trqhxrd.grapesrpg.game.inventory;

import me.trqhxrd.color.Colors;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.skill.Skills;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import me.trqhxrd.menus.Menu;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * This menu is used for displaying all data about every Skill in a good visible and understandable way to the player.
 *
 * @author Trqhxrd
 */
public class SkillsMenu extends Menu implements Owneable<Skills> {

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
        super(Colors.translateColors("&#524df0Skills:"), "menu_skills", 5 * 9, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), false);
        this.skills = skills;
        this.setupMenu(this.getContent());
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
     *
     * @param content This map contains the content of the inventory and can be changed to set the contents of the inventory. The inventory will be updated to the map-contents as soon as {@link Menu#updateInventory()} was called.
     */
    @Override
    public void setupMenu(Map<Integer, ItemStack> content) {
        this.getContent().clear();
        for (int i = 0; i < this.getSize(); i++) content.put(i, this.getBackground());
        this.skills.forEach((key, skill) -> content.put(skill.getSlot(), skill.getDisplayItem()));
    }

    /**
     * This method opens the inventory for the player specified in the method arguments.
     *
     * @param entity The HumanEntity which should open the inventory.
     */
    @Override
    public void open(HumanEntity entity) {
        this.setupMenu(this.getContent());
        super.open(entity);
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