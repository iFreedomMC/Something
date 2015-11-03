package net.camtech.fopmremastered.commands;

import me.StevenLawson.BukkitTelnet.BukkitTelnet;
import me.StevenLawson.BukkitTelnet.session.ClientSession;
import net.camtech.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandParameters(name = "csay", usage = "/csay [level] [message]", description = "Send a message from console.", rank = FOPMR_Rank.Rank.SUPER)
public class Command_csay
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            sender.sendMessage(ChatColor.RED + "This can only be used from console.");
            return true;
        }
        if (args.length < 2)
        {
            return false;
        }
        int level = 0;
        try
        {
            level = Integer.parseInt(args[0]);
        }
        catch (Exception ex)
        {
            return false;
        }
        String replaceAll = CUtils_Methods.colour(StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ").replaceAll("&-", ""));
        if (FOPMR_Rank.getRank(sender).level >= level)
        {
            for (Player player2 : Bukkit.getOnlinePlayers())
            {
                if (FOPMR_Rank.getRank(player2).level >= level)
                {
                    ChatColor colour = ChatColor.WHITE;
                    String levelString = "" + level;
                    switch (levelString)
                    {
                        case "0":
                            colour = ChatColor.WHITE;
                            break;
                        case "1":
                            colour = ChatColor.YELLOW;
                            break;
                        case "2":
                            colour = ChatColor.AQUA;
                            break;
                        case "3":
                            colour = ChatColor.LIGHT_PURPLE;
                            break;
                        case "4":
                            colour = ChatColor.GOLD;
                            break;
                        case "5":
                            colour = ChatColor.GREEN;
                            break;
                        case "6":
                            colour = ChatColor.DARK_PURPLE;
                            break;
                        case "7":
                            colour = ChatColor.DARK_RED;
                            break;
                        default:
                            break;
                    }
                    player2.sendMessage(colour + "[" + FOPMR_Rank.getFromLevel(level).name + " Chat] " + sender.getName() + ": " + replaceAll);
                }
            }
            ChatColor colour = ChatColor.WHITE;
            String levelString = "" + level;
            switch (levelString)
            {
                case "1":
                    colour = ChatColor.YELLOW;
                    break;
                case "2":
                    colour = ChatColor.AQUA;
                    break;
                case "3":
                    colour = ChatColor.LIGHT_PURPLE;
                    break;
                case "4":
                    colour = ChatColor.GOLD;
                    break;
                case "5":
                    colour = ChatColor.GREEN;
                    break;
                case "6":
                    colour = ChatColor.DARK_PURPLE;
                    break;
                case "7":
                    colour = ChatColor.DARK_RED;
                    break;
                default:
                    break;
            }
            if (level <= 3)
            {
                Bukkit.getConsoleSender().sendMessage(colour + "[" + FOPMR_Rank.getFromLevel(level).name + " Chat] " + sender.getName() + ": " + replaceAll);
            }
            if (Bukkit.getPluginManager().getPlugin("BukkitTelnet").isEnabled())
            {
                for (ClientSession session : BukkitTelnet.getClientSessions())
                {
                    String name = session.getCommandSender().getName().replaceAll("[^A-Za-z0-9]", "");
                    Rank rank = FOPMR_Rank.getFromUsername(name);
                    if (rank.level >= level)
                    {
                        session.getCommandSender().sendMessage(colour + "[" + FOPMR_Rank.getFromLevel(level).name + " Chat] " + sender.getName() + ": " + replaceAll);
                    }
                }
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this chat level.");
        }
        return true;
    }
}
