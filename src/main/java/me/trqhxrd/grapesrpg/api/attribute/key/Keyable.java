package me.trqhxrd.grapesrpg.api.attribute.key;

/**
 * Any class that implements this interface can be locked with a key.
 *
 * @param <T> The type of the key.
 * @author Trqhxrd
 */
public interface Keyable<T> {
    /**
     * This method returns the key.
     *
     * @return The key of this value.
     */
    T getKey();
}

