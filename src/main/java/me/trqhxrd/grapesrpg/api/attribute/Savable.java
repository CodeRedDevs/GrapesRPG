package me.trqhxrd.grapesrpg.api.attribute;

/**
 * Any class implementing this interface can be saved in some way.
 * For example using serialisation.
 *
 * @author Trqhxrd
 */
public interface Savable {
    /**
     * This method will save the current state of this class in some way.
     *
     * @param flush If flush is true, the changes will automatically be written to a file.
     */
    void save(boolean flush);

    /**
     * This method just runs {@code this.save(true);}, which will save the state of this class and automatically write it to the file.
     * This method has to be overwritten tho.
     */
    void save();
}
