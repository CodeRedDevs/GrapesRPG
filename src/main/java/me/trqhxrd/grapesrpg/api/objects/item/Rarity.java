package me.trqhxrd.grapesrpg.api.objects.item;

/**
 * This enum defines an items displayed rarity.
 *
 * @author Trqhxrd
 */
public enum Rarity {
    /**
     * ID: 0
     * COLOR: WHITE
     */
    COMMON(0, 'f', "&lCOMMON"),
    /**
     * ID: 1
     * COLOR: WHITE
     */
    UNCOMMON(1, 'a', "&lUNCOMMON"),
    /**
     * ID: 2
     * COLOR: GREEN
     */
    RARE(2, 'b', "&lRARE"),
    /**
     * ID: 3
     * COLOR: AQUA
     */
    EPIC(3, 'd', "&lEPIC"),
    /**
     * ID: 4
     * COLOR: PURPLE
     */
    LEGENDARY(4, 'e', "&lLEGENDARY"),
    /**
     * ID: 5
     * COLOR: YELLOW
     */
    MYTHIC(5, '4', "&lMYTHIC"),
    /**
     * ID: 6
     * COLOR: DARK RED
     */
    HEAVENLY(6, '1', "&lHEAVENLY"),
    /**
     * ID: 7
     * COLOR: DARK BLUE
     */
    GODLY(7, '6', "&lGODLY"),
    /**
     * ID: 8
     * COLOR: DARK PURPLE
     */
    DIVINE(8, '5', "&lDIVINE"),
    /**
     * ID: 9
     * COLOR: RED
     */
    RELIC(9, 'c', "&lRELIC");

    /**
     * The id of the rarity.
     * This is the value, which is stored in the NBT-Values of an item.
     */
    private final int id;

    /**
     * The color-char for the items name.
     */
    private final char color;

    /**
     * The actual name of the rarity.
     */
    private final String formattedName;

    /**
     * This constructor creates a new instance of rarity.
     *
     * @param id            The id of the new instance.
     * @param color         The color char of the rarities name.
     * @param formattedName The actual name of the rarity.
     */
    Rarity(int id, char color, String formattedName) {
        this.id = id;
        this.color = color;
        this.formattedName = formattedName;
    }

    /**
     * This method get the rarity with the id, that you gave in the method arguments.
     *
     * @param id The id of the rarity, which you want to get.
     * @return The rarity, of which the id is equal to the id given in the method arguments.
     * @throws IllegalArgumentException if the id is not in the range of ids.
     */
    public static Rarity getById(int id) {
        for (Rarity r : values()) if (r.getId() == id) return r;
        throw new IllegalArgumentException("Index " + id + " is bigger than " + (values().length - 1));
    }

    /**
     * Getter for the rarities color.
     *
     * @return The char for the {@link net.md_5.bungee.api.ChatColor} of the rarity.
     */
    public char getColor() {
        return color;
    }

    /**
     * getter for the rarities id.
     * The id is used to store the rarity in the NBT-values.
     *
     * @return The id of the rarity.
     */
    public int getId() {
        return id;
    }

    /**
     * This method returns the name of the rarity.
     * With {@link net.md_5.bungee.api.ChatColor}s and everything.
     *
     * @return The name of the rarity.
     */
    public String getFormattedName() {
        return "&" + this.color + formattedName;
    }

    /**
     * Basic toString method.
     *
     * @return The serialized object.
     */
    @Override
    public String toString() {
        return "Rarity{" +
                "id=" + id +
                ", formattedName='" + formattedName + '\'' +
                '}';
    }
}
