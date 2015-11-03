package net.camtech.fopmremastered.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import net.camtech.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Commons;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import static net.camtech.fopmremastered.FOPMR_Rank.isSuper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandParameters(name = "admin", usage = "/admin [[add | delete] [username] <rank>] | [list]", description = "Add somebody to admin.")
public class Command_admin
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        FileConfiguration admins = FOPMR_Configs.getAdmins().getConfig();
        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("list"))
            {
                HashMap<String, String> adminlist = new HashMap<>();
                for (String name : admins.getConfigurationSection("").getKeys(false))
                {
                    if (!admins.getString(name + ".rank").equalsIgnoreCase("op"))
                    {
                        adminlist.put(CUtils_Methods.colour(" " + ChatColor.GOLD + admins.getString(name + ".lastName")), admins.getString(name + ".rank"));
                    }
                }
                ArrayList<String> radmins = new ArrayList<>();
                ArrayList<String> sadmins = new ArrayList<>();
                ArrayList<String> sradmins = new ArrayList<>();
                ArrayList<String> execs = new ArrayList<>();
                ArrayList<String> specs = new ArrayList<>();
                ArrayList<String> sysadmins = new ArrayList<>();
                ArrayList<String> owners = new ArrayList<>();
                ArrayList<ArrayList<String>> arrays = new ArrayList<>();
                arrays.add(radmins);
                arrays.add(sadmins);
                arrays.add(sradmins);
                arrays.add(execs);
                arrays.add(specs);
                arrays.add(sysadmins);
                arrays.add(owners);
                sender.sendMessage(ChatColor.AQUA + "iFreedom Admins:");
                for(Entry<String, String> entry : adminlist.entrySet())
                {
                    Rank rank = FOPMR_Rank.getFromName(entry.getValue());
                    String name = entry.getKey();
                    switch(rank)
                    {
                        case ADMIN:
                            radmins.add(name);
                            break;
                        case SUPER:
                            sadmins.add(name);
                            break;
                        case SENIOR:
                            sradmins.add(name);
                            break;
                        case EXECUTIVE:
                            execs.add(name);
                            break;
                        case SPECIALIST:
                            specs.add(name);
                            break;
                        case SYSTEM:
                            sysadmins.add(name);
                            break;
                        case OWNER:
                            owners.add(name);
                            break;
                        default:
                            break;
                    }
                    //sender.sendMessage("Added " + name + " to " + rank.name + ".");
                }
                arrays.stream().forEach((array) ->
                {
                    Collections.sort(array, String.CASE_INSENSITIVE_ORDER);
                });
                sender.sendMessage(ChatColor.GOLD + "  - Total Admins: " + adminlist.size());
                sender.sendMessage(ChatColor.YELLOW + "    - Admins:");
                sender.sendMessage("        - " + StringUtils.join(radmins, ", "));
                sender.sendMessage(ChatColor.AQUA + "    - Super Admins:");
                sender.sendMessage("        - " + StringUtils.join(sadmins, ", "));
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "    - Senior Admins:");
                sender.sendMessage("        - " + StringUtils.join(sradmins, ", "));
                sender.sendMessage(ChatColor.GOLD + "    - Executives:");
                sender.sendMessage("        - " + StringUtils.join(execs, ", "));
                sender.sendMessage(ChatColor.GREEN + "    - Specialists:");
                sender.sendMessage("        - " + StringUtils.join(specs, ", "));
                sender.sendMessage(ChatColor.DARK_PURPLE + "    - System Admins:");
                sender.sendMessage("        - " + StringUtils.join(sysadmins, ", "));
                sender.sendMessage(ChatColor.DARK_RED + "    - Owners:");
                sender.sendMessage("        - " + StringUtils.join(owners, ", "));
                return true;
            }
            if(args[0].equalsIgnoreCase("purge") && FOPMR_Rank.isSenior(sender))
            {
                FOPMR_Commons.adminAction(sender.getName(), "Purging the player list.", true);
                sender.sendMessage(ChatColor.RED + "PREPARE FOR SPAM!");
                for (String name : admins.getConfigurationSection("").getKeys(false))
                {
                   if(!admins.contains(name + ".lastLogin"))
                   {
                       return true;
                   }
                   long lasttime = admins.getLong(name + ".lastLogin");
                   long current = System.currentTimeMillis();
                   long change = current - lasttime;
                   if(change > 604800000)
                   {
                       sender.sendMessage("Removed " + admins.getString(name + ".lastName") + ", time since last login in milliseconds: " + change + ".");
                       admins.set(name, null);
                   }
                }
                return true;
            }
            return false;
        }
        if(!isSuper(sender))
        {
            return true;
        }
        Player player;
        if (args.length == 2)
        {
            player = Bukkit.getPlayer(args[1]);
            if (player == null)
            {
                sender.sendMessage("Player: " + args[1] + " is not online.");
                return true;
            }
            if (args[0].equalsIgnoreCase("delete"))
            {
                if (FOPMR_Rank.isEqualOrHigher(FOPMR_Rank.getRank(player), FOPMR_Rank.getRank(sender)))
                {
                    sender.sendMessage("You can only remove someone of a lower rank than yourself from admin.");
                    return true;
                }
                FOPMR_Rank.setRank(player, FOPMR_Rank.Rank.OP, sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("add"))
            {
                FOPMR_Rank.setRank(player, FOPMR_Rank.Rank.ADMIN, sender);
                return true;
            }
            return false;
        }
        if (args.length >= 3)
        {
            if (!args[0].equalsIgnoreCase("add"))
            {
                sender.sendMessage("You only need 2 arguments for a removal.");
                return true;
            }
            player = Bukkit.getPlayer(args[1]);
            if (player == null)
            {
                sender.sendMessage("Player: " + args[1] + " is not online.");
                return true;
            }
            int level;
            try
            {
                level = Integer.parseInt(args[2]);
            } catch (Exception ex)
            {
                String rank = StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ");
                level = FOPMR_Rank.getFromName(rank).level;
            }
            if (level == 0)
            {
                Bukkit.broadcastMessage(StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ") + " is an invalid rank.");
                return true;
            }
            FOPMR_Rank.setRank(player, FOPMR_Rank.getFromLevel(level), sender);
            return true;
        }
        return false;
    }

}
