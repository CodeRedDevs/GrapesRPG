package me.trqhxrd.grapesrpg.api.utils.reflection;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * This method returns all Classes, that are in the package "aPackage" and are annotated with the annotation given.
     *
     * @param aPackage   The Package, which you want to scan for annotated classes.
     * @param annotation The annotation for which you want to scan.
     * @return An iterable of Classes, which only contains classes from the package aPackage and are annotated with the annotation.
     */
    public static Set<Class<?>> getTypesAnnotatedWith(String aPackage, Class<? extends Annotation> annotation) {
        return new Reflections(aPackage).getTypesAnnotatedWith(annotation);
    }

    /**
     * This method runs the task given for every class in the package given, if it(the class) is annotated with the annotation given.
     *
     * @param aPackage   The package, which you want to scan for annotated classes.
     * @param annotation The annotation for which you want to scan.
     * @param task       The task that should be executed if the Class is annotated.
     */
    public static void executeIfClassIsAnnotated(String aPackage, Class<? extends Annotation> annotation, ReflectionTask task) {
        getTypesAnnotatedWith(aPackage, annotation).forEach(task::execute);
    }

    /**
     * Returns all classes in a package, that extends a specific other class given.
     *
     * @param aPackage The package, that you want to scan.
     * @param clazz    The class, that you want to scan for.
     * @return A set of classes, that extend your class.
     */
    public static Set<Class<?>> getSubTypesOf(String aPackage, Class<?> clazz) {
        return new HashSet<>(new Reflections(aPackage).getSubTypesOf(clazz));
    }

    /**
     * This method a specific task on every class in a package, that extends a certain other class.
     *
     * @param aPackage   The package, that you want to scan.
     * @param superClass The class for which you want to scan.
     * @param task       The task, that should be executed.
     */
    public static void executeIfClassExtends(String aPackage, Class<?> superClass, ReflectionTask task) {
        getSubTypesOf(aPackage, superClass).forEach(task::execute);
    }
}
