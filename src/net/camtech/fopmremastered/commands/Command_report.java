package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import static net.camtech.fopmremastered.FOPMR_Rank.isAdmin;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name = "report", usage = "/report ([player] [reason]) | [view] | ([delete] [player])", description = "Manage reports.")
public class Command_report
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length == 0)
        {
            return false;
        }
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("view"))
            {
                if(!isAdmin(sender))
                {
                    sender.sendMessage(ChatColor.RED + "You must be an admin to view reports.");
                    return true;
                }
                for(String reported : FOPMR_Configs.getReports().getConfig().getKeys(false))
                {
                    sender.sendMessage(ChatColor.RED + reported + ChatColor.GOLD + " was reported by " + ChatColor.GREEN + FOPMR_Configs.getReports().getConfig().getString(reported + ".reporter") + " for " + ChatColor.AQUA + FOPMR_Configs.getReports().getConfig().getString(reported + ".reason"));
                }
                return true;
            }
            return false;
        }
        if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("delete"))
            {
                if(!isAdmin(sender))
                {
                    sender.sendMessage(ChatColor.RED + "You must be an admin to delete reports.");
                    return true;
                }
                String name = args[1];
                if(Bukkit.getPlayer(name) != null)
                {
                    name = Bukkit.getPlayer(name).getName();
                }
                for(String reported : FOPMR_Configs.getReports().getConfig().getKeys(false))
                {
                    if(reported.equalsIgnoreCase(name))
                    {
                        FOPMR_Configs.getReports().getConfig().set(name, null);
                    }
                }
                return true;
            }
        }
        String reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length));
        String name = args[0];
        if(Bukkit.getPlayer(name) != null)
        {
            name = Bukkit.getPlayer(name).getName();
        }
        sender.sendMessage(ChatColor.RED + "You have reported " + name + " for \"" + reason + "\"");
        FOPMR_Configs.getReports().getConfig().set(name + ".reporter", sender.getName());
        FOPMR_Configs.getReports().getConfig().set(name + ".reason", reason);
        FOPMR_Configs.getReports().saveConfig();
        return true;
    }
}
