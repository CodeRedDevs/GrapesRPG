package me.trqhxrd.grapesrpg.api.utils;

import java.util.Arrays;
import java.util.Collection;

/**
 * An interface, which represents a value for an NBT-Tag.
 *
 * @param <V> The Type of the Value, which you want to store.
 * @author Trqhxrd
 */
public abstract class NBTValue<V> {

    /**
     * The Value, which you want to store in the NBT-Tag.
     */
    private final V value;

    /**
     * A basic constructor for setting the value, which is stored in this wrapper-object.
     *
     * @param value The value, which you want to store.
     */
    public NBTValue(V value) {
        this.value = value;
    }

    /**
     * A simple method for getting the stored value.
     *
     * @return The value stored in the object of this class.
     */
    public V getValue() {
        return value;
    }

    public static class Integer extends NBTValue<java.lang.Integer> {

        /**
         * A basic constructor for setting the value, which is stored in this wrapper-object.
         *
         * @param value The value, which you want to store.
         */
        public Integer(java.lang.Integer value) {
            super(value);
        }
    }

    public static class Double extends NBTValue<java.lang.Double> {

        /**
         * A basic constructor for setting the value, which is stored in this wrapper-object.
         *
         * @param value The value, which you want to store.
         */
        public Double(java.lang.Double value) {
            super(value);
        }
    }

    public static class String extends NBTValue<java.lang.String> {

        /**
         * A basic constructor for setting the value, which is stored in this wrapper-object.
         *
         * @param value The value, which you want to store.
         */
        public String(java.lang.String value) {
            super(value);
        }
    }

    public static class IntegerArray extends NBTValue<Collection<java.lang.Integer>> {

        /**
         * A basic constructor for setting the value, which is stored in this wrapper-object.
         *
         * @param value The value, which you want to store.
         */
        public IntegerArray(Collection<java.lang.Integer> value) {
            super(value);
        }

        /**
         * A basic constructor for setting the value, which is stored in this wrapper-object.
         *
         * @param value The value, which you want to store.
         */
        public IntegerArray(java.lang.Integer... value) {
            super(Arrays.asList(value));
        }
    }
}
