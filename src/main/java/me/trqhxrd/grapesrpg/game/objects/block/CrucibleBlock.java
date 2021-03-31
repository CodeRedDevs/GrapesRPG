package me.trqhxrd.grapesrpg.game.objects.block;

import com.google.gson.reflect.TypeToken;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockState;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import me.trqhxrd.grapesrpg.game.config.BlockData;
import me.trqhxrd.grapesrpg.game.objects.item.util.alchemy.ClearItem;
import me.trqhxrd.grapesrpg.game.objects.item.util.alchemy.WaterItem;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CrucibleBlock extends GrapesBlockState {

    public static final NamespacedKey CRUCIBLE_ITEM_KEY = new NamespacedKey(Grapes.getGrapes(), "crucible");
    private final List<Item> displayedItems;
    private int step = 0;
    private List<ItemStack> items;
    private int waterLevel;

    public CrucibleBlock() {
        this.items = new ArrayList<>();
        this.displayedItems = new ArrayList<>();
    }

    public static boolean isCrucibleItem(Item item) {
        return item.getPersistentDataContainer().has(CRUCIBLE_ITEM_KEY, PersistentDataType.STRING);
    }

    public void addItem(Item i) {
        ItemStack is = i.getItemStack();
        int amount = is.getAmount();
        is.setAmount(1);
        for (int j = 0; j < amount; j++) items.add(is);
        i.remove();
    }

    public void removeDisplayedItems() {
        this.displayedItems.forEach(Item::remove);
        this.displayedItems.clear();
    }

    private void displayItems(Location center) {
        synchronized (this.displayedItems) {
            // CLEAR NULL SPOTS
            List<ItemStack> itemsNew = new ArrayList<>();
            this.items.forEach(i -> {
                if (i != null) itemsNew.add(i);
            });
            this.items = itemsNew;

            this.items.stream().filter(new Predicate<>() {
                int i = 0;
                @Override
                public boolean test(ItemStack itemStack) {
                    return i++ <= 64;
                }
            }).forEach(item -> {
                Item i = Objects.requireNonNull(center.getWorld()).dropItem(center, item);

                i.setVelocity(new Vector(0., 0., 0.));
                i.setPickupDelay(Short.MAX_VALUE);
                i.setGravity(false);
                i.getPersistentDataContainer().set(CRUCIBLE_ITEM_KEY, PersistentDataType.STRING, "true");

                displayedItems.add(i);
            });
        }
    }

    public void updateItems(Location crucible) {
        synchronized (this.displayedItems) {
            Location center = crucible.clone().add(.5, 1., .5);

            final double radius = Math.max(.5, Math.min(3, (double) displayedItems.size() / 15));
            final double spinTime = 400 * radius;
            final double angleStep = (Math.PI * 2) / spinTime;
            final double angleOffset = (Math.PI * 2) / displayedItems.size();
            final Location itemLoc = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());

            double angle = (double) step * angleStep;

            for (Item i : displayedItems) {
                itemLoc.setX(center.getX() + (Math.cos(angle) * radius));
                itemLoc.setZ(center.getZ() + (Math.sin(angle) * radius));

                Vector velocity = itemLoc.clone().subtract(i.getLocation()).toVector();
                i.setVelocity(velocity);

                angle += angleOffset;
            }

            if (step < spinTime) step++;
            else step = 0;
        }
    }

    /**
     * This will write the values in the state to the original Bukkit-Block ate the location of our GrapesBlock.
     *
     * @param location The location of the block.
     */
    @Override
    public void update(Location location) {
        Block cauldron = location.getBlock();
        Levelled data = (Levelled) cauldron.getBlockData();
        data.setLevel(this.waterLevel);
        cauldron.setBlockData(data);

        Location displayLocation = new Location(
                location.getWorld(),
                location.getBlockX() + 0.5,
                location.getBlockY() + 1.5,
                location.getBlockZ() + 0.5
        );

        //RETURN IF WORLD IS NULL
        if (location.getWorld() == null) return;
        this.removeDisplayedItems();
        this.displayItems(displayLocation);
    }

    /**
     * This method will be executed, if the block gets clicked.
     *
     * @param player The Player, who clicked the block.
     * @param block  The block, which got clicked. (This Block stores this GrapesBlockState)
     * @param type   The Type of the click. (If it was left or right-click)
     * @param face   The clicked BlockFace.
     * @return If you return true, the default-action of the block will be cancelled. (example: If you click a chest and return true, the chest won't open)
     */
    @Override
    public boolean onClick(GrapesPlayer player, GrapesBlock block, BlockFace face, ClickType type) {
        if (type == ClickType.RIGHT) {
            GrapesItem item = GrapesItem.fromItemStack(player.getWrappedObject().getInventory().getItemInMainHand());
            if (item != null) {
                if (item.getID() == new WaterItem().getID()) return false;
                else return item.getID() != new ClearItem().getID();
            }
            return true;
        }
        return false;
    }

    /**
     * This method saves the block-state to a config-file.
     *
     * @param s          The path to the configuration-section of this state.
     * @param saveToFile If set to true, the config needs to be written to a file. If you want to store large amounts of blocks at the same time it might be a good idea to set this to false and save the file after you wrote every entry to save resources.
     */
    @Override
    public void save(String s, boolean saveToFile) {
        this.removeDisplayedItems();
        BlockData.getInstance().set(s + ".items", Grapes.GSON_NO_PRETTY_PRINT.toJson(this.items));
        BlockData.getInstance().set(s + ".waterLevel", this.waterLevel);

        if (saveToFile) BlockData.getInstance().save();
    }

    /**
     * Here you can load values from the config.
     * This method will be run after a new object of a GrapesBlockState is created.
     *
     * @param s The path to the configuration-section. It is build like this: "{@literal x.y.z}".
     */
    @Override
    public void load(String s) {
        this.waterLevel = BlockData.getInstance().getInt(s + ".waterLevel");
        this.items = Grapes.GSON_NO_PRETTY_PRINT.fromJson(BlockData.getInstance().getString(s + ".items"), new TypeToken<List<ItemStack>>() {
        }.getType());
    }

    /**
     * This method gets called as soon as the block, which holds this BlockState.
     * Here you should add stuff like dropping block-loot or deleting config entries.
     *
     * @param location The location of the Block, that got destroyed.
     */
    @Override
    public void destroy(Location location) {
        synchronized (this.displayedItems) {
            this.displayedItems.forEach(Entity::remove);
            this.displayedItems.clear();
            this.items.clear();
            this.waterLevel = 0;
        }
    }

    /**
     * Getter for the list of items, which are in the crucible.
     *
     * @return The list of items, that are currently in the crucible.
     */
    public List<ItemStack> getItems() {
        return items;
    }

    /**
     * Setter for the content of the crucible.
     *
     * @param items The new contents of the crucible.
     */
    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    /**
     * Getter for the crucibles water level.
     *
     * @return The cauldrons water level.
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * Setter for the cauldrons water level.
     *
     * @param waterLevel The cauldrons new water level.
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
}
