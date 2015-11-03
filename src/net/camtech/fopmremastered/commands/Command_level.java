package net.camtech.fopmremastered.commands;

import net.camtech.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Command_level extends FOPMR_Command
{

    public Command_level()
    {
        super("level", "/level", "See what clearance level you have.", "You aren't allowed to use this command.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        sender.sendMessage(ChatColor.GREEN + "You have level " + ChatColor.BLUE + Integer.toString(FOPMR_Rank.getRank(sender).level) + ChatColor.GREEN + " clearance as " + CUtils_Methods.aOrAn(FOPMR_Rank.getRank(sender).name) + " " + FOPMR_Rank.getRank(sender).name + ".");
        return true;
    }

}
