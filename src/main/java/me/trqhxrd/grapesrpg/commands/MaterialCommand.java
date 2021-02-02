package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.Grapes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MaterialCommand implements CommandExecutor {

    public MaterialCommand() {
        Objects.requireNonNull(Grapes.getGrapes().getCommand("material")).setExecutor(this);
    }

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
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender);
            Grapes.getGrapes().getUtils().sendMessage(p, "&aMaterial: " + p.getInventory().getItemInMainHand().getType().name());
        }
        return true;
    }
}
