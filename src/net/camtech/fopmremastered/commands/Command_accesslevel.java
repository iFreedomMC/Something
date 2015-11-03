package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "accesslevel", usage = "/accesslevel [level]", description = "Change server access level.", aliases = "al, alevel, accessl, ld, ldown, lockdown, lockd", rank = FOPMR_Rank.Rank.ADMIN)
public class Command_accesslevel
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length < 1)
        {
            return false;
        }
        int level;
        try
        {
            level = Integer.parseInt(args[0]);
        } catch (Exception ex)
        {
            level = FOPMR_Rank.getFromName(StringUtils.join(ArrayUtils.subarray(args, 0, args.length), " ")).level;
        }
        if (FOPMR_Rank.getRank(sender).level < level)
        {
            sender.sendMessage(ChatColor.RED + "You can only set the access level to your rank or lower.");
            return true;
        }
        FOPMR_Configs.getMainConfig().getConfig().set("general.accessLevel", level);
        FOPMR_Configs.getMainConfig().saveConfig();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (FOPMR_Rank.getRank(player).level < level)
            {
                player.kickPlayer("Server has been put into lockdown mode.");
            }
        }
        Bukkit.broadcastMessage(ChatColor.AQUA + sender.getName() + " - Locking server down to clearance level " + level + " (" + FOPMR_Rank.getFromLevel(level).name + ").");
        return true;
    }

}
