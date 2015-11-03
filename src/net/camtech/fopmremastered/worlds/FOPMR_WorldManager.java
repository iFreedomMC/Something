package net.camtech.fopmremastered.worlds;

import net.camtech.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FOPMR_WorldManager
{
    public static World getAdminWorld()
    {
        World world = Bukkit.getWorld("adminworld");
        if(world == null)
        {
            WorldCreator creator = new WorldCreator("adminworld");
            creator.generator(new FOPMR_FlatGenerator());
            world = creator.createWorld();
        }
        return world;
    }
    
    public static World getFlatlands()
    {
        World world = Bukkit.getWorld("flatlands");
        if(world == null)
        {
            WorldCreator creator = new WorldCreator("flatlands");
            creator.generator(new FOPMR_FlatGenerator());
            world = creator.createWorld();
        }
        return world;
    }
    
    public static void wipeFlatlands()
    {
        World flatlands = getFlatlands();
        for(Player player : flatlands.getPlayers())
        {
            player.setOp(false);
            player.setWhitelisted(false);
        }
        Bukkit.getServer().setWhitelist(true);
        CUtils_Methods.unloadWorld(flatlands);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                CUtils_Methods.deleteWorld(flatlands.getWorldFolder());
                Bukkit.getServer().setWhitelist(false);
            }
        }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 5L);
        getFlatlands();
    }
    
    public static void wipeMainWorld()
    {
        FOPMR_Configs.getMainConfig().getConfig().set("general.wipe", true);
        Bukkit.getServer().shutdown();
    }
}
