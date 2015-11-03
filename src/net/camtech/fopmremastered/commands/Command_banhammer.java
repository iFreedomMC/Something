package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Commons;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_banhammer extends FOPMR_Command
{

    public Command_banhammer()
    {
        super("banhammer", "/banhammer", "Unleash the banhammer...", Rank.SPECIALIST);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player player = (Player) sender;
        if (FOPMR_Configs.getAdmins().getConfig().getBoolean(player.getUniqueId().toString() + ".banHammer"))
        {
            player.getInventory().remove(FOPMR_Commons.getBanHammer());
            FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".banHammer", false);
            Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " has placed the BanHammer back into its sheath");
            FOPMR_Configs.getAdmins().saveConfig();
            return true;
        }
        player.getInventory().addItem(FOPMR_Commons.getBanHammer());
        player.getWorld().strikeLightning(player.getLocation());
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " has unleashed the BanHammer!");
        FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".banHammer", true);
        FOPMR_Configs.getAdmins().saveConfig();
        return true;
    }
}
