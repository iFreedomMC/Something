package net.camtech.fopmremastered.commands;

import java.util.Arrays;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_gcmd extends FOPMR_Command
{
    public Command_gcmd()
    {
        super("gcmd", "/gcmd [player] [command]", "Run a command as another player", Arrays.asList("sudo"), Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length < 2)
        {
            return false;
        }

        final Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage("The player " + args[0] + " is not online.");
            return true;
        }

        if (FOPMR_Rank.getRank(sender).level <= FOPMR_Rank.getRank(player).level)
        {
            sender.sendMessage(ChatColor.RED + "You cannot gcmd someone of an equal or higher rank than yourself.");
            return true;
        }

        final String outCommand = StringUtils.join(args, " ", 1, args.length);
        try
        {
            sender.sendMessage("Sending command as " + player.getName() + ": " + outCommand);
            player.chat("/" + outCommand);
        } catch (Throwable ex)
        {
            sender.sendMessage("Error sending command: " + ex.getMessage());
        }

        return true;
    }

}
