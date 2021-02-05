package me.trqhxrd.grapesrpg.api.utils;

import java.lang.reflect.Field;

/**
 * This Utility-Class can be used to read values from a field, which can't be accessed normally.
 * It can also set the value of an inaccessible field.
 *
 * @author Trqhxrd
 */
public class Reflection {

    /**
     * This method returns the value of a certain field in the object given.
     *
     * @param obj       The Object, which contains the field.
     * @param fieldName The name of the field.
     * @return The value, which was stored in the field.
     */
    public static Object getValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object result = field.get(obj);
            field.setAccessible(false);
            return result;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method sets the value of a certain field in a certain object.
     *
     * @param master    The object, which owns the field.
     * @param fieldName The name of the field, which you want to change.
     * @param value     The new value of the field.
     */
    public static void setValue(Object master, String fieldName, Object value) {
        try {
            Field field = master.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(master, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
