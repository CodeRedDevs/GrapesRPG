package me.trqhxrd.grapesrpg.api.skill;

import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.attribute.key.StringKeyable;
import me.trqhxrd.grapesrpg.api.objects.item.InventoryDisplayable;
import me.trqhxrd.grapesrpg.api.utils.group.Group2;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * This class represents any skill, that you want to create.
 * Just create a new class, that extends this one and create a constructor, that takes only takes the owner.
 * IMPORTANT: THE CONSTRUCTOR MUST ONLY CONTAIN ONE {@link Skills}-OBJECT AS A PARAMETER OTHERWISE YOU WILL GET AN ERROR! (Reflection sucks)
 *
 * @author Trqhxrd
 */
public abstract class Skill implements InventoryDisplayable, StringKeyable, Owneable<Skills> {
    /**
     * This field stores the multiplier, how much xp is needed for the next level.
     */
    public static final double MULTIPLIER = 1.10;
    /**
     * This field contains the amount of xp, that is needed for the first level.
     */
    public static final int START_XP = 100;
    /**
     * This is the text, that will be used for the progress-bar.
     */
    public static final String PROGRESS_TEXT = "■";
    /**
     * This field contains the owning set of skills.
     */
    private transient final Skills owner;
    /**
     * This field contains the item, that will show the skills data and its slot.
     */
    private final Group2<ItemStack, Integer> displayPrototype;
    /**
     * This field contains the level of this skill.
     */
    private int level;
    /**
     * This field contains the xp of this skill.
     */
    private int xp;

    /**
     * This constructor will create a new skill with an owner, a level, an amount of xp and a display-item.
     *
     * @param owner         The owner of this skill
     * @param level         The level of this skill.
     * @param xp            The level of this skill.
     * @param displaySlot   The slot in the inventory of this skills icon.
     * @param displayType   The material of the skills icon.
     * @param displayName   The name of the skills-icon
     * @param displayAmount The amount of the skills icon.
     */
    public Skill(Skills owner, int level, int xp, int displaySlot, Material displayType, String displayName, int displayAmount) {
        this(owner, level, xp, displaySlot, new ItemBuilder(displayType)
                .setName(displayName)
                .setAmount(displayAmount)
                .build());
    }

    /**
     * This constructor will create a new skill with an owner, level 1, 0 xp and a display-item.
     *
     * @param owner       The owner of this skill
     * @param displaySlot The slot in the inventory of this skills icon.
     * @param displayItem The item, that will be shown in the Skills-Inventory.
     */
    public Skill(Skills owner, int displaySlot, ItemStack displayItem) {
        this(owner, 1, 0, displaySlot, displayItem);
    }

    /**
     * This constructor will create a new skill.
     *
     * @param owner       The owning collection of skills.
     * @param level       The initial level of the skill.
     * @param xp          The initial amount of xp of the skill.
     * @param displaySlot The slot of the display-icon in the inventory.
     * @param displayItem The actual item in the menu.
     */
    public Skill(Skills owner, int level, int xp, int displaySlot, ItemStack displayItem) {
        this.owner = owner;
        this.level = level;
        this.xp = xp;
        this.displayPrototype = new Group2<>(displayItem, displaySlot);

    }

    /**
     * This method levels the skill up if possible.
     *
     * @return true, if the leveling is possible.
     */
    public boolean levelUp() {
        if (this.xp >= this.getXPRequired()) {
            this.level += 1;
            this.xp -= this.getXPRequired();
            this.levelUp();
            return true;
        }
        return false;
    }

    /**
     * This method adds a certain amount of xp to this skill.
     *
     * @param xp the amount of xp, that was added.
     */
    public void addXP(int xp) {
        this.setXP(this.getXP() + xp);
        this.levelUp();
    }

    /**
     * This method returns the amount of xp, that is required to increase the level by one.
     * Even if you have already gotten some xp, this number wil always be the absolute amount of xp, that is needed.
     *
     * @return The amount of xp, that is required for the next level.
     */
    public int getXPRequired() {
        int xpRequired = START_XP;
        for (int i = 0; i < level; i++) xpRequired *= MULTIPLIER;
        return xpRequired;
    }

    /**
     * Getter for the level of this skill.
     *
     * @return The level of this skill.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Setter for the skills level.
     *
     * @param level The new level of this skill.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Getter for the xp of this skill.
     *
     * @return The xp of this skill.
     */
    public int getXP() {
        return xp;
    }

    /**
     * Setter for this skills xp.
     *
     * @param xp The skills new xp-amount.
     */
    public void setXP(int xp) {
        this.xp = xp;
    }

    /**
     * Getter for the skills display-slot in the skills-menu.
     *
     * @return The skills slot.
     */
    public int getSlot() {
        return displayPrototype.getY();
    }

    /**
     * This method returns the icon for the item.
     *
     * @return The icon for the item.
     */
    @Override
    public ItemStack getDisplayItem() {
        double xpPercent = ((double) this.getXP()) / ((double) this.getXPRequired()) * 100.;
        int part = (int) Math.round(xpPercent / 10.);
        String[] lore = new String[]{
                "&#ba03fcLevel: &e" + this.level,
                "&#ba03fcXP: &c" + this.xp,
                "",
                "&" + Objects.requireNonNull(this.displayPrototype.getX().getItemMeta()).getDisplayName()
                        .toCharArray()[Objects.requireNonNull(this.displayPrototype.getX().getItemMeta()).getDisplayName().indexOf('\u00a7') + 1] + "Progress:",
                "&8[&#03fcb5" + PROGRESS_TEXT.repeat(part) + "&7" + PROGRESS_TEXT.repeat(10 - part) + "&8]"
        };

        return new ItemBuilder(displayPrototype.getX()).setLore(lore).applyFlags(ItemFlag.HIDE_ENCHANTS).build();
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public Skills getOwner() {
        return owner;
    }
}
