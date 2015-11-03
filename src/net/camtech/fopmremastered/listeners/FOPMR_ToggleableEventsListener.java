package net.camtech.fopmremastered.listeners;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class FOPMR_ToggleableEventsListener implements Listener
{

    public FOPMR_ToggleableEventsListener()
    {
        Bukkit.getPluginManager().registerEvents(this, FreedomOpModRemastered.plugin);
        checkTime();
    }

    public static void checkTime()
    {
        if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.time"))
        {
            for (World world : Bukkit.getWorlds())
            {
                world.setGameRuleValue("doDaylightCycle", "false");
            }
        }
        else
        {
            for (World world : Bukkit.getWorlds())
            {
                world.setGameRuleValue("doDaylightCycle", "true");
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event)
    {
        if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.explosions"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (event.getItem() == null)
        {
            return;
        }
        ItemStack item = event.getItem();
        if ((item.getType() == Material.WATER || item.getType() == Material.WATER_BUCKET || item.getType() == Material.STATIONARY_WATER) && !FOPMR_Rank.isAdmin(player) && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.waterplace"))
        {
            event.setCancelled(true);
        }
        if ((item.getType() == Material.LAVA || item.getType() == Material.LAVA_BUCKET || item.getType() == Material.STATIONARY_LAVA) && !FOPMR_Rank.isAdmin(player) && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.lavaplace"))
        {
            event.setCancelled(true);
        }
        if (item.getType() == Material.TNT && !FOPMR_Rank.isAdmin(player) && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.lavaplace"))
        {
            event.setCancelled(true);
        }
        if ((item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIRE || item.getType() == Material.FIREBALL) && !FOPMR_Rank.isAdmin(player) && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.fire"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event)
    {
        if (event.getBlock().getType() == Material.FIRE && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.fire"))
        {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event)
    {
        if(!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.fire"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent event)
    {
        if ((event.getBlock().getType() == Material.WATER || event.getBlock().getType() == Material.STATIONARY_WATER) && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.waterspread"))
        {
            event.setCancelled(true);
        }
        if ((event.getBlock().getType() == Material.LAVA || event.getBlock().getType() == Material.STATIONARY_LAVA) && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.lavaspread"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity)
        {
            LivingEntity lentity = (LivingEntity) entity;
            if (lentity instanceof Tameable)
            {
                Tameable tentity = (Tameable) lentity;
                if (tentity.isTamed() && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.petdamage"))
                {
                    event.setCancelled(true);
                }
            }
        }
        if (event.getCause() == DamageCause.ENTITY_EXPLOSION && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.explosions"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        if (event.toWeatherState() && !FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.weather"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        Entity item = event.getItemDrop();
        if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.drops"))
        {
            item.remove();
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG))
        {
            event.setCancelled(true);
            return;
        }

        Entity spawned = event.getEntity();

        if (spawned instanceof EnderDragon)
        {
            if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.enderdragon"))
            {
                event.setCancelled(true);
            }
        }
        else if (spawned instanceof Ghast)
        {
            if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.ghast"))
            {
                event.setCancelled(true);
            }
        }
        else if (spawned instanceof Slime)
        {
            if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.slime"))
            {
                event.setCancelled(true);
            }
        }
        else if (spawned instanceof Giant)
        {
            if (!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.giant"))
            {
                event.setCancelled(true);
            }
        }
        else if (spawned instanceof Wither)
        {
            if(!FOPMR_Configs.getMainConfig().getConfig().getBoolean("toggles.wither"))
            {
                event.setCancelled(true);
            }
        }
    }
}
