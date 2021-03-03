package me.trqhxrd.grapesrpg.api.skill;

import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.attribute.key.StringKeyable;
import me.trqhxrd.grapesrpg.api.objects.item.InventoryDisplayable;
import me.trqhxrd.grapesrpg.api.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
     * This field contains the owning set of skills.
     */
    private transient final Skills owner;
    /**
     * This integer contains the slot in the skills-menu, where this slots item should displayed.
     */
    private final int displaySlot;
    /**
     * This field contains the material of the display-icon.
     */
    private final Material displayType;
    /**
     * This field contains the display-name of the display-item.
     */
    private final String displayName;
    /**
     * This field contains the display-amount of the display-item.
     */
    private final int displayAmount;
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
        this.owner = owner;
        this.level = level;
        this.xp = xp;
        this.displaySlot = displaySlot;
        this.displayType = displayType;
        this.displayName = displayName;
        this.displayAmount = displayAmount;
    }

    /**
     * This constructor will create a new skill with an owner, level 1, 0 xp and a display-item.
     *
     * @param owner         The owner of this skill
     * @param displaySlot   The slot in the inventory of this skills icon.
     * @param displayType   The material of the skills icon.
     * @param displayName   The name of the skills-icon
     * @param displayAmount The amount of the skills icon.
     */
    public Skill(Skills owner, int displaySlot, Material displayType, String displayName, int displayAmount) {
        this(owner, 1, 0, displaySlot, displayType, displayName, displayAmount);
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
    public int getDisplaySlot() {
        return displaySlot;
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
                "&8[&#03fcb5" + "-".repeat(part) + "&7" + "-".repeat(10 - part) + "&8]"
        };
        return new ItemBuilder(displayType)
                .setName(displayName)
                .setAmount(displayAmount)
                .setLore(lore)
                .build();
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
