package me.trqhxrd.grapesrpg.commands;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Register;
import me.trqhxrd.grapesrpg.game.skills.SkillTypes;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles the skill-command.
 *
 * @author Trqhxrd
 */
@Register(command = "skill")
public class SkillsCommand implements CommandExecutor, TabCompleter {

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
                                if (amount >= 0) {
                                    if (args[1].equalsIgnoreCase("set")) {
                                        if (args[2].equalsIgnoreCase("xp")) {
                                            target.getSkills().getSkill(skill).setXP(amount);
                                            target.getSkills().levelUp();
                                            p.sendMessage("&aSet &b" + target.getName() + "'s &aXP successfully!");
                                        } else if (args[2].equalsIgnoreCase("level")) {
                                            target.getSkills().getSkill(skill).setLevel(amount);
                                            target.getSkills().levelUp();
                                            p.sendMessage("&aSet &b" + target.getName() + "'s &aLevel successfully!");
                                        } else p.sendMessage("&cPlease use /skill help");
                                    } else if (args[1].equalsIgnoreCase("add")) {
                                        if (args[2].equalsIgnoreCase("xp")) {
                                            target.getSkills().getSkill(skill).setXP(target.getSkills().getSkill(skill).getXP() + amount);
                                            target.getSkills().levelUp();
                                            p.sendMessage("&aAdded XP successfully to &b" + target.getName() + "'s &adata-file!");
                                        } else if (args[2].equalsIgnoreCase("level")) {
                                            target.getSkills().getSkill(skill).setLevel(target.getSkills().getSkill(skill).getLevel() + amount);
                                            target.getSkills().levelUp();
                                            p.sendMessage("&aAdded Level successfully to &b" + target.getName() + "'s &adata-file!");
                                        } else p.sendMessage("&cPlease use /skill help");
                                    } else if (args[1].equalsIgnoreCase("remove")) {
                                        if (args[2].equalsIgnoreCase("xp")) {
                                            target.getSkills().getSkill(skill).setXP(target.getSkills().getSkill(skill).getXP() - amount);
                                            target.getSkills().levelUp();
                                            p.sendMessage("&aRemoved XP successfully from &b" + target.getName() + "'s &adata-file!");
                                        } else if (args[2].equalsIgnoreCase("level")) {
                                            target.getSkills().getSkill(skill).setLevel(target.getSkills().getSkill(skill).getLevel() - amount);
                                            target.getSkills().levelUp();
                                            p.sendMessage("&aRemoved Level successfully from &b" + target.getName() + "'s &adata-file!");
                                        } else p.sendMessage("&cPlease use /skill help");
                                    } else p.sendMessage("&cPlease use /skill help");
                                } else p.sendMessage("&cThe number entered must at least be 0!");
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

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                GrapesPlayer.forEach(p -> list.add(p.getName()));
                break;
            case 2:
                list.addAll(Arrays.asList("set", "add", "remove"));
                break;
            case 3:
                if (args[1].equalsIgnoreCase("set") ||
                        args[1].equalsIgnoreCase("add") ||
                        args[1].equalsIgnoreCase("remove")) list.addAll(Arrays.asList("xp", "level"));
                break;
            case 4:
                if ((args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) &&
                        (args[2].equalsIgnoreCase("xp") || args[2].equalsIgnoreCase("level")))
                    for (SkillTypes value : SkillTypes.values()) list.add(value.getKey());
                break;
            case 5:
                if ((args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) &&
                        (args[2].equalsIgnoreCase("xp") || args[2].equalsIgnoreCase("level"))) {
                    for (SkillTypes value : SkillTypes.values()) {
                        if (args[3].equalsIgnoreCase(value.getKey())) {
                            list.addAll(Arrays.asList("1", "10", "100"));
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return list;
    }
}
