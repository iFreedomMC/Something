package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.worlds.FOPMR_WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="flatlands", description="Teleport to and from the flatlands.", usage="/flatlands")
public class Command_flatlands
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be ingame to use this command.");
            return true;
        }
        if (((Player) sender).getWorld() == FOPMR_WorldManager.getFlatlands())
        {
            ((Player) sender).teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            sender.sendMessage(ChatColor.GREEN +"Welcome to main world.");
            return true;
        }
        try
        {
            ((Player) sender).teleport(FOPMR_WorldManager.getFlatlands().getSpawnLocation());
            sender.sendMessage(ChatColor.GREEN +"Welcome to the flatlands.");
        } catch (Exception ex)
        {
            sender.sendMessage("The flatlands cannot be found, they are likely in the middle of a wipe, please try again in a few seconds.");
        }
        return true;
    }

}
