import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.Event

public abstract class Listener<T extends Event> implements Listener {

    @EventHandler
    publich abstract void onEvent(T event);
}
