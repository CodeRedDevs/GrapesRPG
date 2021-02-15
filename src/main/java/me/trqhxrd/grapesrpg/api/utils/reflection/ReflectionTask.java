package me.trqhxrd.grapesrpg.api.utils.reflection;

/**
 * This interface is while using reflections.
 * For example if you want to run something for every class, that is annotated with a certain annotation.
 *
 * @author Trqhxrd
 */
public interface ReflectionTask {
    /**
     * This method will be run by the Reflection Class.
     *
     * @param clazz The Class, for which you want to run this code.
     */
    void execute(Class<?> clazz);
}
