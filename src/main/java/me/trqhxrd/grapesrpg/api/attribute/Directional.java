package me.trqhxrd.grapesrpg.api.attribute;

import me.trqhxrd.grapesrpg.api.utils.block.Direction;

/**
 * This interface represents anything that has a direction.
 *
 * @author Trqhxrd
 */
public interface Directional {
    /**
     * This method returns the direction of the implementing object.
     *
     * @return The rotation of the implementing object.
     */
    Direction getDirection();

    /**
     * This method sets the new Direction for the implementing object.
     *
     * @param direction The new direction for the implementing object.
     */
    void setDirection(Direction direction);
}
