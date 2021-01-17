package me.trqhxrd.grapesrpg.api.utils;

/**
 * This interface represents a builder for any type of class.
 *
 * @param <T> The type of the builders result.
 */
public interface Builder<T> {
    /**
     * This method can build an object of the type T.
     * You have to create your own Class, which implements the Builder.
     *
     * @return The built object, which was created by the builder.
     */
    T build();
}
