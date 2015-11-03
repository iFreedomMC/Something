package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Command_djump extends FOPMR_Command
{
    public Command_djump()
    {
        super("djump", "/djump", "Toggle your double jumping ability.", Arrays.asList("doublejump"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("This can only be used in game.");
            return true;
        }
        Player player = (Player) sender;
        sender.sendMessage(ChatColor.GREEN + "Toggled double jump mode.");
        FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".djump", !FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".djump"));
        FOPMR_Configs.getAdmins().saveConfig();
        return true;
    }

}
