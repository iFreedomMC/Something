package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import net.camtech.fopmremastered.listeners.FOPMR_ToggleableEventsListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Command_toggle extends FOPMR_Command
{
    public Command_toggle()
    {
        super("toggle", "/toggle [value]", "Toggle a server setting.", Rank.EXECUTIVE);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.AQUA + "Possible toggles:");
            for (String toggle : FOPMR_Configs.getMainConfig().getConfig().getConfigurationSection("toggles").getKeys(false))
            {
                sender.sendMessage(" - " + (FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles." + toggle) ? ChatColor.GREEN : ChatColor.RED) + toggle);
            }
            return true;
        }
        if (args.length == 1)
        {
            for (String toggle : FOPMR_Configs.getMainConfig().getConfig().getConfigurationSection("toggles").getKeys(false))
            {
                if (args[0].equalsIgnoreCase(toggle))
                {
                    FOPMR_Configs.getMainConfig().getConfig().set("toggles." + toggle, !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles." + toggle));
                    FOPMR_Configs.getMainConfig().saveConfig();
                    sender.sendMessage(ChatColor.GOLD + "Toggled " + toggle + (FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles." + toggle) ? " on." : " off."));
                    FOPMR_ToggleableEventsListener.checkTime();
                    return true;
                }
            }
            sender.sendMessage(ChatColor.AQUA + "Possible toggles:");
            for (String toggle : FOPMR_Configs.getMainConfig().getConfig().getConfigurationSection("toggles").getKeys(false))
            {
                sender.sendMessage(" - " + (FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles." + toggle) ? ChatColor.GREEN : ChatColor.RED) + toggle);
            }
            return true;
        }
        return false;
    }

}
