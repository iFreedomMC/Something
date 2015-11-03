package net.camtech.fopmremastered.commands;

import java.util.Arrays;
import net.camtech.fopmremastered.FOPMR_Commons;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_fr extends FOPMR_Command
{

    public Command_fr()
    {
        super("fr", "/fr <player>", "Freeze a player.", Arrays.asList("freeze", "halt"), Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0)
        {
            for (Player player : Bukkit.getOnlinePlayers())
            {
                FOPMR_Commons.globalFreeze = !FOPMR_Commons.globalFreeze;
                FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".freeze", FOPMR_Commons.globalFreeze);
                FOPMR_Configs.getAdmins().saveConfig();
                player.sendMessage((FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".freeze") ? (ChatColor.RED + "You are now frozen.") : (ChatColor.AQUA + "You are now unfrozen.")));
            }
        }
        if (args.length == 1)
        {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null)
            {
                sender.sendMessage("Player is not online.");
                return true;
            }
            Bukkit.broadcastMessage(ChatColor.AQUA + sender.getName() + " - Toggling freeze over " + player.getName() + ".");
            FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".freeze", !FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".freeze"));
            FOPMR_Configs.getAdmins().saveConfig();
            player.sendMessage((FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".freeze") ? (ChatColor.RED + "You are now frozen.") : (ChatColor.AQUA + "You are now unfrozen.")));
        }
        return false;
    }

}
