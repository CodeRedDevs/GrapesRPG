package me.trqhxrd.grapesrpg.api.attribute;

/**
 * Allows a class to be stored in a String.
 * The serialization and deserialization protocol must be developed by you.
 * @param <T> The Class you want to serialize or deserialize.
 */
public interface Serializable<T> {

    /**
     * This method serializes an Object (t) into a String.
     * @param t The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    String serialize(T t);

    /**
     * This method serializes the Object, from which it will be executed.
     * @return The serialized object.
     */
    String serialize();

    /**
     * This method is able to create an object from a serialized String.
     * @param s The String you want to deserialize.
     * @return The Object.
     */
    T deserialize(String s);
}
