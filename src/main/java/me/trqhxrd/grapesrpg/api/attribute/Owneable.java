package me.trqhxrd.grapesrpg.api.attribute;

/**
 * Any class, that implements this interface has an owning class, that can be used using the {@link Owneable#getOwner()}-method.
 *
 * @param <T> The type of the owner.
 */
public interface Owneable<T> {

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    T getOwner();
}
