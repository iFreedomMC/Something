package net.camtech.fopmremastered.commands;

import java.util.Arrays;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_forcechat extends FOPMR_Command
{
    public Command_forcechat()
    {
        super("forcechat", "/forcechat [player] [message]", "Force a player to send a chat message.", Arrays.asList("fc", "fchat", "forcec"), Rank.SENIOR);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length < 2)
        {
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(ChatColor.RED + "The player you attempted to select is not online.");
            return true;
        }
        String chat = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        if (FOPMR_Rank.isEqualOrHigher(FOPMR_Rank.getRank(player), FOPMR_Rank.getRank(sender)))
        {
            sender.sendMessage(ChatColor.RED + "You cannot force someone of an equal or higher rank than yourself to chat.");
            return true;
        }
        sender.sendMessage(ChatColor.BLUE + "Sending " + chat + " as " + player.getName() + ".");
        player.chat(chat);
        return true;
    }

}
