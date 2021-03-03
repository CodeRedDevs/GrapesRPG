package me.trqhxrd.grapesrpg.api.utils.items;

import com.google.common.base.Preconditions;
import me.trqhxrd.grapesrpg.api.utils.Builder;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * @param amount The new amount. Cannot {@literal be < 1}.
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

    public ItemBuilder setLore(List<String> displayLore) {
        List<String> colored = new ArrayList<>();
        displayLore.forEach(s -> colored.add(Utils.translateColorCodes(s)));
        ItemMeta meta = is.getItemMeta();
        if (meta != null) meta.setLore(colored);
        is.setItemMeta(meta);
        return this;
    }

    /**
     * This method sets the lore of the ItemStack.
     *
     * @param displayLore An Array of Strings. Every entry in this array is one line.
     * @return The ItemBuilder. Used for creating command-chains.
     */
    public ItemBuilder setLore(String... displayLore) {
        return this.setLore(Arrays.asList(displayLore));
    }

    /**
     * This method clears the lore of the {@link ItemStack}.
     *
     * @return The ItemBuilder. Used for creating command-chains.
     */
    public ItemBuilder clearLore() {
        return this.setLore();
    }

    /**
     * This method overwrites a single line of the lore.
     *
     * @param line The line, that should be overwritten.
     * @param text The text, that should be inserted.
     * @return The ItemBuilder. Used for creating command-chains.
     */
    public ItemBuilder setLoreLine(int line, String text) {
        ItemMeta meta = is.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                lore.set(line, text);
                return this.setLore(lore);
            }
        }
        return this;
    }
}
