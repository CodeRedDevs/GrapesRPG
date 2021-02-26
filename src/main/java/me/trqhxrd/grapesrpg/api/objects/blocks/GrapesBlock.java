package me.trqhxrd.grapesrpg.api.objects.blocks;

import me.trqhxrd.grapesrpg.game.config.BlockData;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class represents a Block.
 * It can store a Block-Type({@link GrapesBlockType}), a {@link Location} and a {@link GrapesBlockState}.
 * The type gives information about the type of BlockState, that should be used.
 * The Location is final since GrapesBlocks aren't movable at the moments.
 * The GrapesBlockState contains all information about the Block like inventories, items and other data.
 *
 * @author Trqhxrd
 */
public final class GrapesBlock {
    /**
     * This HashMap stores all blocks, that are currently loaded from the config.
     */
    private static final Map<Location, GrapesBlock> cachedBlocks = new HashMap<>();
    /**
     * This is the location of the block.
     */
    private final Location location;
    /**
     * The BlockState stores all information, that the block can hold.
     * If you want to store custom data just make a new class and let it extend {@link GrapesBlockState}.
     */
    private GrapesBlockState blockState;
    /**
     * The type of block, that we have.
     */
    private GrapesBlockType type;

    /**
     * This constructor can create a new block. it should not be called from outside this class.
     * Also you should only use it if you know, what you're doing as this block is able to create whole new blocks.
     * If you want to create a block without any risk use the {@link GrapesBlock#getBlock(Location)}-Method and set the values, once you got your Block.
     *
     * @param location   The location of the block.
     * @param blockState The BlockState of the block.
     * @param type       The type of the block.
     */
    private GrapesBlock(Location location, GrapesBlockState blockState, GrapesBlockType type) {
        this.location = location;
        this.blockState = blockState;
        this.type = type;
    }

    /**
     * This method returns a cached block, that is located at the location given.
     * If there is no cached block, a new block will be loaded from the config.
     * If that block is also null, a new block will be created with unset data values.
     *
     * @param loc The location of which you want to get the block.
     * @return The Block, which is located at the location given.
     */
    public static GrapesBlock getBlock(Location loc) {
        try {
            if (!cachedBlocks.containsKey(loc)) {
                if (!BlockData.getInstance().contains(loc.getBlockX() + "." + loc.getBlockY() + "." + loc.getBlockZ() + ".id"))
                    cachedBlocks.put(loc, new GrapesBlock(loc, GrapesBlockType.UNDEFINED.getBlockStateType().getConstructor().newInstance(), GrapesBlockType.UNDEFINED));
                else cachedBlocks.put(loc, GrapesBlock.load(loc));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return cachedBlocks.get(loc);
    }

    /**
     * This method saves all blocks from the {@link GrapesBlock#cachedBlocks}-Map to the config and clears it afterwards.
     */
    public static void save() {
        cachedBlocks.forEach((loc, b) -> {
            if (b.getType() != GrapesBlockType.UNDEFINED) {
                String s = loc.getBlockX() + "." + loc.getBlockY() + "." + loc.getBlockZ();
                BlockData.getInstance().set(s + ".id", b.getType().getId());
                b.getState().save(s, false);
            }
        });
        BlockData.getInstance().save();
        cachedBlocks.clear();
    }

    /**
     * This method loads a block from the config.
     * It returns null, if the block is not stored in the config.
     *
     * @param location The location of which you want to get the block.
     * @return The block that is located at your location. Returns null if not available.
     */
    private static GrapesBlock load(Location location) {
        try {
            String s = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            int id = BlockData.getInstance().getInt(s + ".id");
            GrapesBlockType type = GrapesBlockType.fromID(id);
            GrapesBlockState state = type.getBlockStateType().getConstructor().newInstance();
            state.load(s);
            return new GrapesBlock(location, state, type);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getter for the blocks location,
     *
     * @return The blocks location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * This method returns the bukkit block, which is at the location of this GrapesBlock.
     *
     * @return The BukkitBlock at this GrapesBlock's location.
     */
    public Block getBukkitBlock() {
        return location.getBlock();
    }

    /**
     * This method transfers data from the GrapesBlock to the Bukkit Block.
     */
    public void update() {
        if (this.type.getBukkitMaterial() != null) this.getBukkitBlock().setType(this.type.getBukkitMaterial());
        if (this.blockState != null) this.blockState.update(this.getLocation());
    }

    /**
     * Getter for the block's GrapesBlockState.
     *
     * @return The block's GrapesBlockState.
     */
    public GrapesBlockState getState() {
        return blockState;
    }

    /**
     * Setter for the block's GrapesBlockState.
     *
     * @param blockState The new GrapesBlockState of this Block.
     */
    public void setState(GrapesBlockState blockState) {
        this.blockState = blockState;
    }

    /**
     * Getter for the block's custom type.
     *
     * @return The block's custom type.
     */
    public GrapesBlockType getType() {
        return type;
    }

    /**
     * Setter for the block's custom type.
     * This method will update the bukkit-block.
     * This will also overwrite the BlockState.
     *
     * @param type The block's new type.
     * @return If there was an old {@link GrapesBlockState} available, it will be returned here so you can get some data if required.
     */
    public GrapesBlockState setType(GrapesBlockType type) {
        this.type = type;
        GrapesBlockState state = this.blockState;
        try {
            this.blockState = type.getBlockStateType().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.update();
        return state;
    }
}
