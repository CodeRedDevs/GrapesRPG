package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the skill-command.
 *
 * @author Trqhxrd
 */
@Register(command = "skill")
public class SkillsCommand implements CommandExecutor {

    /**
     * This is the handler method.
     *
     * @param sender The person / the console, that sent the command.
     * @param cmd    The command itself.
     * @param label  The label of the command
     * @param args   The commands arguments.
     * @return true if the command was executed successfully.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GrapesPlayer p = GrapesPlayer.getByUniqueId(player.getUniqueId());
            if (args.length == 0) {
                p.getSkills().getMenu().openInventory(player);
                p.sendMessage("&aHere you go!");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage(Utils.translateColorCodes("/skill -> Menu"));
                    p.sendMessage(Utils.translateColorCodes("/skill <name> -> Menu for player"));
                    p.sendMessage(Utils.translateColorCodes("/skill <name> set <xp|level> skill <amount>"));
                    p.sendMessage(Utils.translateColorCodes("/skill <name> add <xp|level> skill <amount>"));
                    p.sendMessage(Utils.translateColorCodes("/skill <name> remove <xp|level> <skill> <amount>"));
                    return true;
                }
            }
            if (args.length >= 1) {
                GrapesPlayer target = GrapesPlayer.getByName(args[0]);
                if (target != null) {
                    if (args.length == 1) {
                        target.getSkills().getMenu().openInventory(player);
                        p.sendMessage("&aHere you go!");
                    } else if (args.length == 5) {
                        try {
                            int amount = Integer.parseInt(args[4]);
                            String skill = args[3];
                            if (target.getSkills().getSkill(skill) != null) {
                                if (args[1].equalsIgnoreCase("set")) {
                                    if (args[2].equalsIgnoreCase("xp")) {
                                        target.getSkills().getSkill(skill).setXP(amount);
                                        target.getSkills().levelUp();
                                    } else if (args[2].equalsIgnoreCase("level")) {
                                        target.getSkills().getSkill(skill).setLevel(amount);
                                        target.getSkills().levelUp();
                                    } else p.sendMessage("&cPlease use /skill help");
                                } else if (args[1].equalsIgnoreCase("add")) {
                                    if (args[2].equalsIgnoreCase("xp")) {
                                        target.getSkills().getSkill(skill).setXP(target.getSkills().getSkill(skill).getXP() + amount);
                                        target.getSkills().levelUp();
                                    } else if (args[2].equalsIgnoreCase("level")) {
                                        target.getSkills().getSkill(skill).setLevel(target.getSkills().getSkill(skill).getLevel() + amount);
                                        target.getSkills().levelUp();
                                    } else p.sendMessage("&cPlease use /skill help");
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (args[2].equalsIgnoreCase("xp")) {
                                        target.getSkills().getSkill(skill).setXP(target.getSkills().getSkill(skill).getXP() - amount);
                                        target.getSkills().levelUp();
                                    } else if (args[2].equalsIgnoreCase("level")) {
                                        target.getSkills().getSkill(skill).setLevel(target.getSkills().getSkill(skill).getLevel() - amount);
                                        target.getSkills().levelUp();
                                    } else p.sendMessage("&cPlease use /skill help");
                                } else p.sendMessage("&cPlease use /skill help");
                            } else p.sendMessage("&cThe Skill-Name \"" + skill + "\" is invalid!");
                        } catch (NumberFormatException ex) {
                            p.sendMessage("&cPlease enter a valid number!");
                        }
                    } else p.sendMessage("&cPlease use /skill help");
                } else p.sendMessage("There is no player with the name " + args[0] + "!");
            }
        } else sender.sendMessage(Utils.translateColorCodes("&cYou have to be a Player to execute this command!"));
        return true;
    }
}
