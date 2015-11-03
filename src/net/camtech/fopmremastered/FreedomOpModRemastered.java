package net.camtech.fopmremastered;

import java.io.File;
import java.util.logging.Level;
import net.camtech.camutils.CUtils_Methods;
import net.camtech.fopmremastered.commands.FOPMR_CommandRegistry;
import net.camtech.fopmremastered.listeners.FOPMR_CamVerifyListener;
import net.camtech.fopmremastered.listeners.FOPMR_CamzieListener;
import net.camtech.fopmremastered.listeners.FOPMR_PlayerListener;
import net.camtech.fopmremastered.listeners.FOPMR_TelnetListener;
import net.camtech.fopmremastered.listeners.FOPMR_ToggleableEventsListener;
import net.camtech.fopmremastered.worlds.FOPMR_WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class FreedomOpModRemastered extends JavaPlugin
{

    public static FreedomOpModRemastered plugin;
    
    @Override
    public void onEnable()
    {
        
        plugin = this;
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().log(Level.INFO, "{0}{1} v. {2} by {3} has been enabled!", new Object[]
        {
            ChatColor.BLUE, pdf.getName(), pdf.getVersion(), pdf.getAuthors()
        });
        new FOPMR_Configs();
        if(FOPMR_Configs.getMainConfig().getConfig().getBoolean("general.wipe"))
        {
            Bukkit.broadcastMessage("Wiping main world.");
            FOPMR_Configs.getMainConfig().getConfig().set("general.wipe", false);
            CUtils_Methods.deleteWorld(new File("world"));
        }
        new FOPMR_CommandRegistry();
        new FOPMR_PlayerListener();
        new FOPMR_TelnetListener();
        new FOPMR_CamVerifyListener();
        new FOPMR_ToggleableEventsListener();
        new FOPMR_CamzieListener();
        
        FOPMR_Announcements.setup();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            FileConfiguration config = FOPMR_Configs.admins.getConfig();
            if (config.getBoolean(player.getUniqueId().toString() + ".imposter"))
            {
                FOPMR_Commons.imposters.add(player.getName());
            }
        }
        FOPMR_WorldManager.getAdminWorld();
        FOPMR_WorldManager.getFlatlands();
    }

    @Override
    public void onDisable()
    {
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().log(Level.INFO, "{0}{1} has been disabled!", new Object[]
        {
            ChatColor.RED, pdf.getName()
        });
    }
}
