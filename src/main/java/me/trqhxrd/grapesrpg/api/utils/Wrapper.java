package me.trqhxrd.grapesrpg.api.utils;

import java.util.Objects;

/**
 * This abstract class is used for creating Wrapper-Classes for native Bukkit Objects.
 *
 * @param <T> The Class of the Object, which you want to wrap.
 */
public abstract class Wrapper<T> {

    /**
     * Every Object of a Wrapper contains an Object of the class, it is wrapped around.
     * This field contains the wrapped object.
     */
    protected final T wrappedObject;

    /**
     * This constructor creates a new Wrapper, with the wrappedObject as it's start-parameter.
     *
     * @param wrappedObject The Object, which you want to wrap this Wrapper around.
     */
    public Wrapper(T wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    /**
     * Getter for the wrapped object.
     *
     * @return The wrapped object.
     */
    public T getWrappedObject() {
        return wrappedObject;
    }

    /**
     * This method checks, if two wrappers are the same.
     *
     * @param o The other object.
     * @return true, if the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wrapper<?> wrapper = (Wrapper<?>) o;
        return Objects.equals(wrappedObject, wrapper.wrappedObject);
    }

    /**
     * This method generates a hash-code from this object.
     *
     * @return A hash-code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(wrappedObject);
    }
}
