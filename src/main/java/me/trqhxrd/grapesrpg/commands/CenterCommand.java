package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This command centers you on your current block.
 * This can be used for positioning NPCs correctly.
 * @author Trqhxrd
 */
@Register(command = "center")
public class CenterCommand implements CommandExecutor {
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
        if (!(sender instanceof Player)) {
            Grapes.getGrapes().getUtils().sendMessage(sender, "&cYou have to be a player to execute this command!");
            return true;
        }
        if (!sender.hasPermission("grapes.command.center")) {
            Grapes.getGrapes().getUtils().noPermission(sender);
            return true;
        }

        GrapesPlayer p = GrapesPlayer.getByPlayer((Player) sender);

        int yaw = (int) (Math.round(p.getLocation().getYaw() / 22.5) * 22.5);
        int pitch = (int) (Math.round(p.getLocation().getPitch() / 22.5) * 22.5);

        p.getWrappedObject().teleport(new Location(
                p.getLocation().getWorld(),
                p.getLocation().getBlockX() + .5,
                p.getLocation().getY(),
                p.getLocation().getBlockZ() + .5,
                yaw,
                pitch));

        return true;
    }
}
