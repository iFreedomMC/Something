package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import net.camtech.fopmremastered.worlds.FOPMR_WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Command_wipemainworld extends FOPMR_Command
{

    public Command_wipemainworld()
    {
        super("wipemainworld", "/wipemainwold", "Wipe the main world.", Rank.EXECUTIVE);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        FOPMR_WorldManager.wipeMainWorld();
        Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Wiping Main World.");
        return true;
    }

}
