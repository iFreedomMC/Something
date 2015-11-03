package net.camtech.fopmremastered.listeners;

import java.util.ArrayList;
import java.util.Random;
import net.camtech.camutils.CUtils_Particle;
import net.camtech.camutils.CUtils_Player;
import net.camtech.fopmremastered.FOPMR_Commons;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import static net.camtech.fopmremastered.FreedomOpModRemastered.plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FOPMR_CamzieListener implements Listener
{
    public FOPMR_CamzieListener()
    {
        Bukkit.getPluginManager().registerEvents(this, FreedomOpModRemastered.plugin);
    }

    @EventHandler
    public void onCamzieHit(EntityDamageByEntityEvent event)
    {
        if (!FOPMR_Commons.camOverlordMode)
        {
            return;
        }
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if (player.getName().equals("Camzie99"))
            {
                event.getDamager().getWorld().strikeLightningEffect(event.getDamager().getLocation());
                event.getDamager().getWorld().strikeLightningEffect(event.getDamager().getLocation().add(5, 0, 5));
                event.getDamager().getWorld().strikeLightningEffect(event.getDamager().getLocation().add(5, 0, -5));
                event.getDamager().getWorld().strikeLightningEffect(event.getDamager().getLocation().add(-5, 0, 5));
                event.getDamager().getWorld().strikeLightningEffect(event.getDamager().getLocation().add(-5, 0, -5));
            }
        }
        if (event.getDamager() instanceof Player)
        {
            Player hitter = (Player) event.getDamager();
            if (hitter.getName().equals("Camzie99"))
            {
                if (hitter.isSneaking())
                {
                    ((LivingEntity) event.getEntity()).addPotionEffect(PotionEffectType.CONFUSION.createEffect(5, 5));
                    ((LivingEntity) event.getEntity()).addPotionEffect(PotionEffectType.SLOW.createEffect(5, 5));
                    event.setDamage(event.getDamage() * 2.5);
                }
            }
        }
    }

    @EventHandler
    public void onCamzieInteract(PlayerInteractEvent event)
    {
        if (!FOPMR_Commons.camOverlordMode)
        {
            return;
        }
        final Player player = event.getPlayer();
        if (player.getName().equals("Camzie99"))
        {
            if (player.isSneaking())
            {
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                {
                    CUtils_Player cplayer = new CUtils_Player(player);
                    Location loc;
                    if (!cplayer.isTargetingEntity(30))
                    {
                        loc = cplayer.getTargetBlock(30);
                    }
                    else
                    {
                        loc = cplayer.getTargetEntity(30).getLocation();
                    }
                    player.getWorld().createExplosion(loc.getX(), loc.getY() + 1, loc.getZ(), 3, true, false);
                    for (int i = 0; i < 10; i++)
                    {
                        int x = new Random().nextInt(3);
                        int y = new Random().nextInt(3);
                        int z = new Random().nextInt(3);
                        if (new Random().nextBoolean())
                        {
                            x = -x;
                        }
                        if (new Random().nextBoolean())
                        {
                            y = -y;
                        }
                        if (new Random().nextBoolean())
                        {
                            z = -z;
                        }
                        final Location newloc = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
                        if (newloc.getBlock().getType() != Material.AIR)
                        {
                            continue;
                        }
                        newloc.getBlock().setType(Material.WOOL);
                        newloc.getBlock().setData((byte) 10);
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                newloc.getBlock().setType(Material.AIR);
                            }
                        }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 2L);
                    }
                    if (cplayer.isTargetingEntity(30))
                    {
                        Entity entity = cplayer.getTargetEntity(30);
                        if (entity instanceof LivingEntity)
                        {
                            LivingEntity lentity = (LivingEntity) entity;
                            lentity.setVelocity(player.getLocation().getDirection().multiply(3).add(new Vector(0, 2, 0)));
                        }
                    }
                }
                else
                {
                    player.setSneaking(false);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
                            horse.setOwner(player);
                            horse.setVariant(Horse.Variant.HORSE);
                            horse.setTamed(true);
                            horse.setAdult();
                            horse.setColor(Horse.Color.BLACK);
                            horse.setStyle(Horse.Style.NONE);
                            horse.setCustomNameVisible(true);
                            horse.setCustomName(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Camzie99's Steed");
                            horse.setPassenger(player);
                            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
                            horse.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING, 1));
                        }
                    }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 2L);
                }
            }
            else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR)
            {
                CUtils_Player cplayer = new CUtils_Player(player);
                long total = 5;
                boolean isTarget = false;
                Location loc;
                if (cplayer.isTargetingEntity())
                {
                    final LivingEntity e = cplayer.getTargetEntity();
                    loc = e.getLocation();
                    double damage = 3;
                    e.teleport(loc);
                    e.setVelocity(new Vector(0, 0, 0));
                    if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
                    {
                        damage = damage * 1.8;
                    }
                    e.damage(damage);
                    isTarget = true;
                }
                else
                {
                    loc = cplayer.getTargetBlock(20);
                    loc.add(0.0d, 1.0d, 0.0d);
                }
                CUtils_Particle freeze = CUtils_Particle.SNOW_DIG;
                final Block orig = loc.getBlock();
                final Material origMaterial = orig.getType();
                CUtils_Particle.sendToLocation(freeze, loc, 3, 3, 3, 10, 20);
                for (BlockFace face : BlockFace.values())
                {
                    final Block block = orig.getRelative(face);
                    final Material original = block.getType();
                    if (original.equals(Material.AIR))
                    {
                        block.setType(Material.ICE);
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                block.setType(Material.AIR);
                            }
                        }.runTaskLater(plugin, 20L * 5L);
                    }
                }
                if (isTarget)
                {
                    LivingEntity e = cplayer.getTargetEntity(20);
                    Block next = loc.add(0.0d, 1.0d, 0.0d).getBlock();
                    for (BlockFace face : BlockFace.values())
                    {
                        final Block block = next.getRelative(face);
                        final Material original = block.getType();
                        if (original.equals(Material.AIR));
                        {
                            block.setType(Material.ICE);
                            new BukkitRunnable()
                            {
                                @Override
                                public void run()
                                {
                                    block.setType(Material.AIR);
                                }
                            }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 5L);
                        }
                    }
                }
            }
            else if (event.getAction() == RIGHT_CLICK_BLOCK)
            {
                final Block orig = event.getClickedBlock();
                for (BlockFace face : BlockFace.values())
                {
                    final Block block = orig.getRelative(face);
                    final Material original = block.getType();
                    if (!original.equals(Material.AIR))
                    {
                        block.setType(Material.AIR);
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                block.setType(original);
                            }
                        }.runTaskLater(plugin, 20L * 3L);
                    }
                }
                final Material original2 = orig.getType();
                if (!original2.equals(Material.AIR))
                {
                    orig.setType(Material.AIR);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            orig.setType(original2);
                        }
                    }.runTaskLater(plugin, 20L * 3L);
                }
            }
        }
    }

    @EventHandler
    public void onCamzieInteractWithEntity(PlayerInteractEntityEvent event)
    {
        if (!FOPMR_Commons.camOverlordMode)
        {
            return;
        }
        final Player player = event.getPlayer();
        if (player.getName().equals("Camzie99"))
        {
            final Entity e = event.getRightClicked();
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    e.setPassenger(player);
                }
            }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 2L);
        }
    }

    @EventHandler
    public void onCamzieJump(final PlayerMoveEvent event)
    {
        if (!FOPMR_Commons.camOverlordMode)
        {
            return;
        }
        Player player = event.getPlayer();
        if (event.getTo().getY() > event.getFrom().getY() && player.isSneaking())
        {
            if (player.getName().equals("Camzie99") && !player.isFlying())
            {
                if (event.getFrom().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
                {
                    player.setVelocity(player.getLocation().getDirection().multiply(1.3));
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            Location loc = event.getFrom().add(0, 1, 0);
                            loc.getWorld().strikeLightning(loc);
                            ArrayList<Location> locations = new ArrayList<>();
                            locations.add(loc);
                            locations.add(loc.getBlock().getLocation().add(1, 0, 1));
                            locations.add(loc.getBlock().getLocation().add(-1, 0, 1));
                            locations.add(loc.getBlock().getLocation().add(1, 0, -1));
                            locations.add(loc.getBlock().getLocation().add(-1, 0, -1));
                            locations.add(loc.getBlock().getLocation().add(1, 0, 0));
                            locations.add(loc.getBlock().getLocation().add(0, 0, 1));
                            locations.add(loc.getBlock().getLocation().add(0, 0, -1));
                            locations.add(loc.getBlock().getLocation().add(-1, 0, 0));
                            for (final Location l : locations)
                            {
                                if (l.getBlock().getType() == Material.AIR)
                                {
                                    l.getBlock().setType(Material.FIRE);
                                    new BukkitRunnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            l.getBlock().setType(Material.AIR);
                                        }
                                    }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 3L);
                                }
                            }
                        }
                    }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 2L);
                }
            }
        }
    }

    @EventHandler
    public void onCamzieFall(EntityDamageEvent event)
    {
        if (!FOPMR_Commons.camOverlordMode)
        {
            return;
        }
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if (player.getName().equals("Camzie99") && player.isSneaking())
            {
                if (event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.LIGHTNING)
                {
                    event.setCancelled(true);
                }
            }
        }
    }
}
