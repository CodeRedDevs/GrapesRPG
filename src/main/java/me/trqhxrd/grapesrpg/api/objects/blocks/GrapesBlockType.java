package me.trqhxrd.grapesrpg.api.objects.blocks;

import me.trqhxrd.grapesrpg.game.objects.block.AscensionShrineBlock;
import org.bukkit.Material;

/**
 * This enum contains all the different types of blocks that are available.
 *
 * @author Trqhxrd
 */
public enum GrapesBlockType {

    /**
     * This type will be applied, whenever a block is new and undefined.
     */
    UNDEFINED(-1, null, GrapesBlockState.class),
    /**
     * This is an AscensionShrine.
     */
    ASCENSION_SHRINE(35, Material.BEACON, AscensionShrineBlock.class);

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
     * @return The type's bukkit material.
     */
    public Material getBukkitMaterial() {
        return bukkitMaterial;
    }

    /**
     * Getter for the type's BlockState-Class.
     * @return The type's BlockState-Class.
     */
    public Class<? extends GrapesBlockState> getBlockStateType() {
        return blockStateType;
    }

    /**
     * Getter for the type's ID.
     * @return The type's id.
     */
    public int getId() {
        return id;
    }
}
