package net.camtech.fopmremastered;

import net.camtech.camutils.CUtils_Methods;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class FOPMR_Announcements
{

    public static void setup()
    {
        System.out.println("Announcements Loaded.");
        FOPMR_Configs.getAnnouncements().getConfig().getKeys(false).stream().forEach((announce) ->
        {
            announce(FOPMR_Configs.getAnnouncements().getConfig().getString(announce + ".message"), FOPMR_Configs.getAnnouncements().getConfig().getInt(announce + ".time"));
        });
    }

    private static void announce(String message, int delay)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.broadcastMessage(CUtils_Methods.colour(message));
                announce(message, delay);
            }
        }.runTaskLater(FreedomOpModRemastered.plugin, delay * 20);
    }
}
