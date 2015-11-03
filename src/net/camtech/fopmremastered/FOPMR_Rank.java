package net.camtech.fopmremastered;

import net.camtech.camutils.CUtils_Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FOPMR_Rank
{
    
    public static Rank getRank(CommandSender player)
    {
        if (!(player instanceof Player))
        {
            if ("Console".equalsIgnoreCase(player.getName()))
            {
                return Rank.CONSOLE;
            }
            else
            {
                FileConfiguration config = FOPMR_Configs.admins.getConfig();
                OfflinePlayer offplayer = Bukkit.getOfflinePlayer(player.getName().replaceAll("[^A-Za-z0-9]", ""));
                if(offplayer == null)
                {
                    return Rank.SUPER;
                }
                for (Rank rank : Rank.values())
                {
                    if (config.getString(offplayer.getUniqueId().toString() + ".rank").equalsIgnoreCase((rank.name)))
                    {
                        return rank;
                    }
                }
                return Rank.SUPER;
            }
        }
        if (FOPMR_Commons.imposters.contains(player.getName()))
        {
            return Rank.IMPOSTER;
        }
        try
        {
            for (Rank rank : Rank.values())
            {
                FileConfiguration config = FOPMR_Configs.admins.getConfig();
                if (config.getString(((Player) player).getUniqueId().toString() + ".rank").equalsIgnoreCase(rank.name))
                {
                    return rank;
                }
            }
        }
        catch (Exception ignored)
        {

        }
        return Rank.OP;
    }

    public static Rank getFromUsername(String name)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        if (player != null)
        {
            for (Rank rank : Rank.values())
            {
                FileConfiguration config = FOPMR_Configs.admins.getConfig();
                if (config.getString(player.getUniqueId().toString() + ".rank").equalsIgnoreCase(rank.name))
                {
                    return rank;
                }
            }
        }
        return Rank.OP;
    }

    public static Rank getFromLevel(int level)
    {
        for (Rank rank : Rank.values())
        {
            if (rank.level == level)
            {
                return rank;
            }
        }
        return Rank.OP;
    }

    public static Rank getFromName(String name)
    {
        for (Rank rank : Rank.values())
        {
            if (rank.name.equalsIgnoreCase(name) || rank.name.split(" ")[0].equalsIgnoreCase(name))
            {
                return rank;
            }
        }
        return Rank.OP;
    }

    public static boolean isImposter(CommandSender player)
    {
        return getRank(player).level == -1;
    }

    public static boolean isAdmin(CommandSender player)
    {
        return getRank(player).level >= 1;
    }

    public static boolean isSuper(CommandSender player)
    {
        return getRank(player).level >= 2;
    }

    public static boolean isSenior(CommandSender player)
    {
        return getRank(player).level >= 3;
    }

    public static boolean isExecutive(CommandSender player)
    {
        return getRank(player).level >= 4;
    }

    public static boolean isSpecialist(CommandSender player)
    {
        return getRank(player).level >= 5;
    }

    public static boolean isSystem(CommandSender player)
    {
        return getRank(player).level >= 6;
    }

    public static boolean isOwner(CommandSender player)
    {
        return getRank(player).level >= 7;
    }

    public static boolean isRank(CommandSender player, int rank)
    {
        return getRank(player).level >= rank;
    }

    public static boolean isRank(CommandSender player, Rank rank)
    {
        return getRank(player).equals(rank);
    }

    public static void setRank(Player player, Rank rank, CommandSender sender)
    {
        if (getRank(player) == Rank.IMPOSTER)
        {
            FOPMR_Commons.imposters.remove(player.getName());
            FOPMR_Configs.admins.getConfig().set(player.getUniqueId().toString() + ".imposter", false);
            FOPMR_Configs.admins.getConfig().set(player.getUniqueId().toString() + ".lastIp", player.getAddress().getHostString());
            FOPMR_Configs.admins.saveConfig();
            Bukkit.broadcastMessage(ChatColor.AQUA + sender.getName() + " - verifying " + player.getName() + " as an admin.");
            return;
        }
        if (getRank(sender).level <= getRank(player).level && rank != Rank.OP)
        {
            sender.sendMessage(ChatColor.RED + "You can only add people to a rank who are lower than yourself.");
            return;
        }
        if (rank.level >= getRank(sender).level)
        {
            sender.sendMessage(ChatColor.RED + "You can only add people to a rank lower than yourself.");
            return;
        }
        if (rank.level < getRank(player).level && (!rank.equals(Rank.OP)))
        {
            sender.sendMessage(ChatColor.RED + rank.name + " is a lower rank than " + player.getName() + "'s current rank of " + getRank(player).name + ".");
            FOPMR_Configs.admins.saveConfig();
            return;
        }
        Bukkit.broadcastMessage(ChatColor.AQUA + sender.getName() + " - adding " + player.getName() + " to the clearance level of " + rank.level + " as " + CUtils_Methods.aOrAn(rank.name) + " " + rank.name);
        FOPMR_Configs.admins.getConfig().set(player.getUniqueId().toString() + ".rank", rank.name);
        FOPMR_Configs.admins.saveConfig();
    }

    public static String getTag(Player player)
    {
        if (!"default&r".equals(FOPMR_Configs.admins.getConfig().getString(player.getUniqueId().toString() + ".tag")) && !"off&r".equals(FOPMR_Configs.admins.getConfig().getString(player.getUniqueId().toString() + ".tag")) && !"default".equals(FOPMR_Configs.admins.getConfig().getString(player.getUniqueId().toString() + ".tag")) && !"off".equals(FOPMR_Configs.admins.getConfig().getString(player.getUniqueId().toString() + ".tag")))
        {
            return FOPMR_Configs.admins.getConfig().getString(player.getUniqueId().toString() + ".tag");
        }
        return getRank(player).tag;
    }

    public static Rank getRankFromIp(String ip)
    {
        FileConfiguration config = FOPMR_Configs.admins.getConfig();
        for (String uuid : config.getConfigurationSection("").getKeys(false))
        {
            if (config.getString(uuid + ".lastIp").equals(ip))
            {
                for (Rank rank : Rank.values())
                {
                    if (config.getString(uuid + ".rank").equalsIgnoreCase(rank.name))
                    {
                        return rank;
                    }
                }
            }
        }
        return Rank.OP;
    }

    public static String getNameFromIp(String ip)
    {
        FileConfiguration config = FOPMR_Configs.admins.getConfig();
        for (String uuid : config.getConfigurationSection("").getKeys(false))
        {
            if (config.getString(uuid + ".lastIp").equals(ip))
            {
                return config.getString(uuid + ".lastName");
            }
        }
        return null;
    }

    public static boolean isEqualOrHigher(Rank rank, Rank rank2)
    {
        return rank.level >= rank2.level;
    }

    public static void unImposter(Player player)
    {
        FOPMR_Commons.imposters.remove(player.getName());
        FOPMR_Configs.admins.getConfig().set(player.getUniqueId().toString() + ".imposter", false);
        FOPMR_Configs.admins.getConfig().set(player.getUniqueId().toString() + ".lastIp", player.getAddress().getHostString());
        FOPMR_Configs.admins.saveConfig();
    }

    public enum Rank
    {

        OP("Op", "&7[&cOp&7]&r", 0), ADMIN("Admin", "&7[&eAdmin&7]&r", 1), SUPER("Super Admin", "&7[&bSA&7]&r", 2), SENIOR("Senior Admin", "&7[&dSrA&7]&r", 3), CONSOLE("CONSOLE", "&7[&aCONSOLE&7]&r", 3),
        EXECUTIVE("Executive", "&7[&6Exec&7]&r", 4), SPECIALIST("Specialist", "&7[&aSpec&7]&r", 5), SYSTEM("System Admin", "&7[&5Sys-Admin&7]&r", 6), OWNER("Owner", "&7[&4Owner&7]&r", 7),
        IMPOSTER("Imposter", "&7[Imp]&r", -1);

        public final String name;
        public final String tag;
        public final int level;

        private Rank(String name, String tag, int level)
        {
            this.name = name;
            this.tag = tag;
            this.level = level;
        }
    }
}
