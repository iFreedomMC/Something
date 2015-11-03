package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Command_blockcommand extends FOPMR_Command
{
    public Command_blockcommand()
    {
        super("blockcommand", "/blockcommand [player]", "Block a player's commands.", Arrays.asList("blockcmd"), Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length != 1)
        {
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage("Player is not online.");
            return true;
        }
        if (FOPMR_Rank.isEqualOrHigher(FOPMR_Rank.getRank(player), FOPMR_Rank.getRank(sender)) && !FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".cmdblock"))
        {
            sender.sendMessage("You can only block the commands of a player with lower clearance than yourself.");
            return true;
        }
        Bukkit.broadcastMessage(ChatColor.AQUA + sender.getName() + " - toggling command blockage for " + player.getName() + ".");
        FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".cmdblock", !FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".cmdblock"));
        FOPMR_Configs.getAdmins().saveConfig();
        return true;
    }

}
