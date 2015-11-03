package net.camtech.fopmremastered.commands;

import net.camtech.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "ifm", usage = "/fopm <reload>", description = "Check info about the plugin or reload the configuration files.")
public class Command_ifm
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!FOPMR_Rank.isAdmin(sender))
                {
                    sender.sendMessage("Only admins can reload the iFreedomManagement configuration files.");
                    return true;
                }
                FOPMR_Configs.getAdmins().reloadConfig();
                FOPMR_Configs.getBans().reloadConfig();
                FOPMR_Configs.getCommands().reloadConfig();
                FOPMR_Configs.getMainConfig().reloadConfig();
                FOPMR_Configs.getReports().reloadConfig();
                FOPMR_Configs.getAnnouncements().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "iFreedomManagement files reloaded!");
                FOPMR_CommandRegistry.registerCommands();
                return true;
            }
            return false;
        }
        else
        {
            sender.sendMessage(ChatColor.GREEN + "This is the iFreedomManagement, the core of our server!");
            sender.sendMessage(CUtils_Methods.randomChatColour() + "an all new form of All-Op management.");
            sender.sendMessage(CUtils_Methods.colour("&-Created in the likes of the TotalFreedomMod but with more " + CUtils_Methods.randomChatColour() + "flexibility&- by " + CUtils_Methods.randomChatColour() + "Chaosiity&-!"));
        }
        return true;
    }

}
