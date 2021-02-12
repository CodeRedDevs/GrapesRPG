package me.trqhxrd.grapesrpg.api.objects.item;

import org.bukkit.inventory.ItemStack;

/**
 * This enum contains all the item types.
 * If the type is set to {@link ItemType#MELEE}, the items damage-values will be used if you hit an entity with the item.
 * If the type is set to {@link ItemType#ARMOR}, the items protection-values will be used, if you got hit to reduce the damage.
 * If the type is set to {@link ItemType#RANGED}, the items damage-values will be applied to the entity, which got hit by the projectile.
 */
public enum ItemType {
    /**
     * This State makes the plugin apply damage to the entity, which got hit.
     */
    MELEE("&cMELEE"),
    /**
     * This State makes the plugin apply damage to the entity, which got hit by the projectile.
     */
    RANGED("&aRANGED"),
    /**
     * This State makes the plugin reduce the damage, which you take, when you get hit by another entity.
     */
    ARMOR("&cARMOR");

    /**
     * This is the default-type, which will be applied to the item ({@link GrapesItem#fromItemStack(ItemStack)}), if there is not state in the NBT-Values.
     */
    public static final ItemType DEFAULT_TYPE = ItemType.MELEE;
    /**
     * This field contains the name of the state, which will be displayed in the items lore.
     */
    private final String loreEntry;

    /**
     * This constructor creates a new state with a predefined loreEntry.
     *
     * @param loreEntry The "rendered" name of the item, which will be displayed in the lore.
     */
    ItemType(String loreEntry) {
        this.loreEntry = loreEntry;
    }

    /**
     * Getter for the ItemTypes loreEntry.
     *
     * @return The ItemTypes lore-entry
     */
    public String getLoreEntry() {
        return loreEntry;
    }
}
