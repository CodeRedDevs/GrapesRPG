package me.trqhxrd.grapesrpg.api.attribute;

/**
 * Allows a class to be stored in a String.
 * The serialization and deserialization protocol must be developed by you.
 * NOTE: Every implementation of this class needs to contain this Method: {@code public static T deserialize(String serializedObject)}.
 * This method is required for deserializing the Object.
 *
 * @param <T> The Class you want to serialize or deserialize.
 */
public interface Serializable<T> {

    /**
     * This method serializes an Object (t) into a String.
     *
     * @param t The Object, which you want to serialize.
     * @return The String containing all data about the object.
     */
    String serialize(T t);

    /**
     * This method serializes the Object, from which it will be executed.
     *
     * @return The serialized object.
     */
    String serialize();
}
