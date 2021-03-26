package me.trqhxrd.grapesrpg.api.objects.block;

import me.trqhxrd.grapesrpg.game.objects.block.AscensionShrineBlock;
import me.trqhxrd.grapesrpg.game.objects.block.CraftingTableBlock;
import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import me.trqhxrd.grapesrpg.game.objects.block.EnderChestBlock;
import org.bukkit.Material;

import java.lang.reflect.InvocationTargetException;

/**
 * This enum contains all the different types of blocks that are available.
 *
 * @author Trqhxrd
 */
public enum GrapesBlockType {

    UNDEFINED(-1, Material.AIR, GrapesBlockState.class),
    CRAFTING_TABLE(1, Material.CRAFTING_TABLE, CraftingTableBlock.class),
    STONE(2, Material.STONE, GrapesBlockState.class),
    COAL_ORE(3, Material.COAL_ORE, GrapesBlockState.class),
    IRON_ORE(4, Material.IRON_ORE, GrapesBlockState.class),
    GOLD_ORE(5, Material.GOLD_ORE, GrapesBlockState.class),
    LAPIS_LAZULI_ORE(5, Material.GOLD_ORE, GrapesBlockState.class),
    REDSTONE_ORE(5, Material.REDSTONE_ORE, GrapesBlockState.class),
    DIAMOND_ORE(5, Material.DIAMOND_ORE, GrapesBlockState.class),
    EMERALD_ORE(5, Material.EMERALD_ORE, GrapesBlockState.class),
    ASCENSION_SHRINE(35, Material.BEACON, AscensionShrineBlock.class),
    TEST_BLOCK(36, Material.CHEST, GrapesBlockState.class),
    CRUCIBLE(37, Material.CAULDRON, CrucibleBlock.class),
    ENDER_CHEST(38, Material.ENDER_CHEST, EnderChestBlock.class);

    /**
     * This id of the type.
     * This is used to save disk-space while storing types to a file because numbers are smaller than text.
     */
    private final int id;
    /**
     * This is the material of the block, that should be placed.
     */
    private final Material bukkitMaterial;
    /**
     * This the class, that should be instantiated if a block gets this type set.
     */
    private final Class<? extends GrapesBlockState> blockStateType;

    /**
     * Constructor, which sets final variables.
     *
     * @param id             The id of the type
     * @param bukkitMaterial The material of the type
     * @param blockStateType The state of the type.
     */
    GrapesBlockType(int id, Material bukkitMaterial, Class<? extends GrapesBlockState> blockStateType) {
        this.id = id;
        this.bukkitMaterial = bukkitMaterial;
        this.blockStateType = blockStateType;
    }

    /**
     * This returns a certain type with a certain id.
     *
     * @param id The id of the type you want to get.
     * @return The type with the id given. If the id is not available it will return null.
     */
    public static GrapesBlockType fromID(int id) {
        for (GrapesBlockType value : values()) if (value.getId() == id) return value;
        return null;
    }

    /**
     * Getter for the type's bukkit material.
     *
     * @return The type's bukkit material.
     */
    public Material getBukkitMaterial() {
        return bukkitMaterial;
    }

    /**
     * Getter for the type's BlockState-Class.
     *
     * @return The type's BlockState-Class.
     */
    public Class<? extends GrapesBlockState> getBlockStateType() {
        return blockStateType;
    }

    /**
     * Getter for the type's ID.
     *
     * @return The type's id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns a new instance of the types {@link GrapesBlockState}.
     * You can use this, if you don't want to add a try-catch-block for every method, where you need a new instance of a BlockState.
     *
     * @return A new instance of the {@link GrapesBlockState} for this type.
     */
    public GrapesBlockState getNewState() {
        try {
            return this.getBlockStateType().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
