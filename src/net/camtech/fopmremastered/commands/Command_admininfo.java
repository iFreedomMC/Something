package net.camtech.fopmremastered.commands;

import net.camtech.camutils.CUtils_Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name="admininfo", description="Find how to become admin.", usage="/admininfo",aliases="ai")
public class Command_admininfo
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
    {
        sender.sendMessage(CUtils_Methods.randomChatColour() + "Interested in becoming an admin?");
        sender.sendMessage(CUtils_Methods.randomChatColour() + "You can apply here: http://ifreedom.forumized.com/forumdisplay.php?fid=3/ but apps are currently" + ChatColor.BLUE + " 'On Hold'" + ChatColor.RESET + ".");
        return true;
    }
}
