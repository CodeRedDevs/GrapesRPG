package me.trqhxrd.grapesrpg.api.utils;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This is just a simple ItemBuilder for {@link ItemStack}s.
 *
 * @author Trqhxrd
 */
public class ItemBuilder implements Builder<ItemStack> {

    /**
     * This field contains the ItemStack, which will be edited after every step of the ItemBuilder.
     */
    private final ItemStack is;

    /**
     * This constructor creates a new ItemBuilder with the ItemStack in the parameters as it's start-stack.
     *
     * @param is The start-value for the ItemBuilder.
     */
    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    /**
     * This constructor creates a new ItemBuilder with an empty ItemStack, which has set only the Material to the Material given in the parameters.
     *
     * @param m The Material of the Start-ItemStack.
     */
    public ItemBuilder(Material m) {
        this.is = new ItemStack(m);
    }

    /**
     * This method sets the name of the item.
     *
     * @param name The new name of the item.
     * @return The ItemBuilder. Used for creating command-chains.
     */
    public ItemBuilder setName(String name) {
        if (name.isBlank()) name = "&c&d&l&a";
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(Utils.translateColorCodes(name));
        is.setItemMeta(meta);

        return this;
    }

    /**
     * This method sets the amount of the ItemStack.
     *
     * @param amount The new amount. Cannot be < 1.
     * @return The ItemBuilder. Used for creating command-chains.
     */
    public ItemBuilder setAmount(int amount) {
        Preconditions.checkArgument(amount > 0);
        is.setAmount(amount);
        return this;
    }

    /**
     * This method can build an object of the type T.
     * You have to create your own Class, which implements the Builder.
     *
     * @return The built object, which was created by the builder.
     */
    @Override
    public ItemStack build() {
        return is;
    }
}
