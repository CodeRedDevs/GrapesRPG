package me.trqhxrd.grapesrpg.api.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used on a class-definition to make the server register it as a {@link org.bukkit.event.Listener}, {@link org.bukkit.command.CommandExecutor} or {@link org.bukkit.command.TabCompleter}.
 *
 * @author Trqhxrd
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {
    /**
     * This field only needs to be filled, if you want to register a command.
     *
     * @return The name of the command.
     */
    String command() default "";
}
