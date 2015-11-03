package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import net.camtech.fopmremastered.worlds.FOPMR_WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name="adminworld", description="Teleport to and from adminworld!", usage="/adminworld", rank=Rank.ADMIN)
public class Command_adminworld
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be ingame to use this command.");
            return true;
        }
        if (((Player) sender).getWorld() == FOPMR_WorldManager.getAdminWorld())
        {
            ((Player) sender).teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            sender.sendMessage(ChatColor.GREEN + "Welcome to the main world.");
            return true;
        }
        try
        {
            ((Player) sender).teleport(FOPMR_WorldManager.getAdminWorld().getSpawnLocation());
            sender.sendMessage(ChatColor.GREEN +"Welcome to adminworld.");
            return true;
        } catch (Exception ex)
        {
            sender.sendMessage("The adminworld cannot be found. Please contact Camzie99.");
        }
        return true;
    }
}
