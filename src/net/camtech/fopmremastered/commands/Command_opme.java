package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_opme extends FOPMR_Command
{
    public Command_opme()
    {
        super("opme", "/opme", "Op yourself.", Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("This can only be executed in-game.");
            return true;
        }
        sender.setOp(true);
        sender.sendMessage(ChatColor.AQUA + "You are now opped.");
        return true;
    }

}
