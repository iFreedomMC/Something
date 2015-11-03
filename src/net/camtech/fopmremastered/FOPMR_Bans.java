package net.camtech.fopmremastered;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FOPMR_Bans
{
    private static boolean nameBan = false;
    private static boolean ipBan = false;
    private static boolean uuidBan = false;

    public static void addBan(final Player player, final String reason)
    {
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setVelocity(player.getVelocity().add(new Vector(0, 3, 0)));
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.kickPlayer(reason);
                player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
                player.getWorld().strikeLightning(player.getLocation());
            }
        }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 3L);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().strikeLightning(player.getLocation());
        player.setHealth(0d);
        addBan(player.getName(), reason);
    }

    public static void addBan(String name, String reason)
    {
        if (!FOPMR_Configs.admins.getConfig().contains(Bukkit.getOfflinePlayer(name).getUniqueId().toString()))
        {
            Bukkit.broadcastMessage(ChatColor.RED + name + " could not be found.");
            return;
        }
        if (FOPMR_Configs.getBans().getConfig().contains("names." + name))
        {
            nameBan = true;
        }

        if (FOPMR_Configs.getBans().getConfig().contains("ips." + FOPMR_Configs.admins.getConfig().getString(name + ".lastIp")))
        {
            ipBan = true;
        }

        if (FOPMR_Configs.getBans().getConfig().contains("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString()))
        {
            uuidBan = true;
        }

        if (!nameBan)
        {
            FOPMR_Configs.getBans().getConfig().set("names." + name + ".reason", reason);
            FOPMR_Configs.getBans().getConfig().set("names." + name + ".perm", false);
        }

        if (!ipBan)
        {
            FOPMR_Configs.getBans().getConfig().set("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-") + ".reason", reason);
            FOPMR_Configs.getBans().getConfig().set("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-") + ".perm", false);
        }

        if (!uuidBan)
        {
            FOPMR_Configs.getBans().getConfig().set("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".reason", reason);
            FOPMR_Configs.getBans().getConfig().set("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".perm", false);
        }

        nameBan = false;
        ipBan = false;
        uuidBan = false;
        FOPMR_Configs.getBans().saveConfig();
    }

    public static void unBan(Player player)
    {
        unBan(player.getName());
    }

    public static void unBan(String name)
    {
        if (!FOPMR_Configs.admins.getConfig().contains(Bukkit.getOfflinePlayer(name).getUniqueId().toString()))
        {
            Bukkit.broadcastMessage(ChatColor.RED + name + " could not be found.");
            return;
        }

        if (FOPMR_Configs.getBans().getConfig().contains("names." + name))
        {
            nameBan = true;
        }

        if (FOPMR_Configs.getBans().getConfig().contains("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-")))
        {
            ipBan = true;
        }

        if (FOPMR_Configs.getBans().getConfig().contains("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString()))
        {
            uuidBan = true;
        }

        if (nameBan)
        {
            if (FOPMR_Configs.getBans().getConfig().getBoolean("names." + name + ".perm"))
            {
                Bukkit.broadcastMessage(ChatColor.RED + name + " is eternally banned.");
            }
            else
            {
                FOPMR_Configs.getBans().getConfig().set("names." + name, null);
            }
        }

        if (ipBan)
        {
            if (FOPMR_Configs.getBans().getConfig().getBoolean("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-") + ".perm"))
            {
                Bukkit.broadcastMessage(ChatColor.RED + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-") + " is eternally banned.");
            }
            else
            {
                FOPMR_Configs.getBans().getConfig().set("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-"), null);
            }
        }

        if (uuidBan)
        {
            if (FOPMR_Configs.getBans().getConfig().getBoolean("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".perm"))
            {
                Bukkit.broadcastMessage(ChatColor.RED + Bukkit.getOfflinePlayer(name).getUniqueId().toString() + " is eternally banned.");
            }
            else
            {
                FOPMR_Configs.getBans().getConfig().set("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString(), null);
            }
        }
        nameBan = false;
        ipBan = false;
        uuidBan = false;
        FOPMR_Configs.bans.saveConfig();
    }

    public static boolean isBanned(Player player)
    {
        return isBanned(player.getName());
    }

    public static boolean isBanned(String name)
    {
        return isBanned(name, FOPMR_Configs.admins.getConfig().getString(name + ".lastIp"));
    }

    public static boolean isBanned(String name, String ip)
    {
        if (FOPMR_Configs.getBans().getConfig().contains("names." + name))
        {
            return true;
        }

        if (FOPMR_Configs.getBans().getConfig().contains("ips." + ip.replaceAll("\\.", "-")))
        {
            return true;
        }

        return FOPMR_Configs.getBans().getConfig().contains("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString());
    }

    public static String getReason(String name)
    {
        if (!FOPMR_Configs.admins.getConfig().contains(Bukkit.getOfflinePlayer(name).getUniqueId().toString()))
        {
            return name + " is not banned.";
        }
        if (FOPMR_Configs.getBans().getConfig().contains("names." + name))
        {
            return FOPMR_Configs.getBans().getConfig().getString("names." + name + ".reason");
        }
        if (FOPMR_Configs.getBans().getConfig().contains("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-")))
        {
            return FOPMR_Configs.getBans().getConfig().getString("ips." + FOPMR_Configs.admins.getConfig().getString(Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".lastIp").replaceAll("\\.", "-") + ".reason");
        }
        if (FOPMR_Configs.getBans().getConfig().contains("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString()))
        {
            return FOPMR_Configs.getBans().getConfig().getString("uuids." + Bukkit.getOfflinePlayer(name).getUniqueId().toString() + ".reason");
        }
        return "Player is not banned.";
    }
}
