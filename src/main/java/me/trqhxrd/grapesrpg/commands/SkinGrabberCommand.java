package me.trqhxrd.grapesrpg.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.utils.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * This command saves the data about a players skin into a file.
 * This can be used for getting the data for a new NPCs skin.
 *
 * @author Trqhxrd
 */
@Register(command = "skin")
public class SkinGrabberCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Config config = new Config(new File(Grapes.getGrapes().getDataFolder(), "skins.yml"));
            Property prop = null;
            try {
                prop = ((GameProfile) p.getClass().getDeclaredMethod("getProfile").invoke(p)).getProperties().get("textures").iterator().next();
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            config.set(p.getUniqueId().toString() + ".name", p.getName());
            config.set(p.getUniqueId().toString() + ".value", prop.getValue());
            config.set(p.getUniqueId().toString() + ".signature", prop.getSignature());
            config.save();
            Grapes.getGrapes().getUtils().sendMessage(sender, "&aThe data about your skin was stored successfully at &eplugins/GrapesRPG/skins.yml");
        }
        return true;
    }
}
