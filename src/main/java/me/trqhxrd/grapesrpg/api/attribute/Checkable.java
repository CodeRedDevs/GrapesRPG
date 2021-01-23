package me.trqhxrd.grapesrpg.api.attribute;

/**
 * This attribute is can be used for a wide variety of things.
 *
 * @author Trqhxrd
 */
public interface Checkable {

    /**
     * This Method just checks, if the implementing object is valid. (You can define the word "valid").
     *
     * @return true if the object is valid. False, if the object isn't valid.
     */
    boolean check();
}
