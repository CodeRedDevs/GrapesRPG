package me.trqhxrd.grapesrpg.api.attribute;

import me.trqhxrd.grapesrpg.api.utils.block.Rotation;

/**
 * This interface represents anything that has a rotation.
 *
 * @author Trqhxrd
 */
public interface Rotatable {
    /**
     * This method returns the rotation of the implementing object.
     *
     * @return The rotation of the implementing object,
     */
    Rotation getRotation();

    /**
     * This method sets the rotation of the implementing object.
     *
     * @param rotation The new rotation for the implementing object.
     */
    void setRotation(Rotation rotation);
}
