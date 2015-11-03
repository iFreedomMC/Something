package net.camtech.fopmremastered.commands;

import java.util.ArrayList;
import java.util.List;
import net.camtech.fopmremastered.FOPMR_Commons;
import net.camtech.fopmremastered.FOPMR_Rank;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandParameters(name = "potion", usage = "/potion <list | clear [target name] | add <type> <duration> <amplifier> [target name]>", description = "Change potion effects.")
public class Command_potion
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player sender_p = null;
        if (sender instanceof Player)
        {
            sender_p = (Player) sender;
        }
        if (args.length == 1 || args.length == 2)
        {
            if (args[0].equalsIgnoreCase("list"))
            {
                List<String> potionEffectTypeNames = new ArrayList<>();
                for (PotionEffectType potion_effect_type : PotionEffectType.values())
                {
                    if (potion_effect_type != null)
                    {
                        potionEffectTypeNames.add(potion_effect_type.getName());
                    }
                }
                FOPMR_Commons.playerMsg(sender, "Potion effect types: " + StringUtils.join(potionEffectTypeNames, ", "), ChatColor.AQUA);
            }
            else if (args[0].equalsIgnoreCase("clearall"))
            {
                if (!FOPMR_Rank.isAdmin(sender))
                {
                    FOPMR_Commons.playerMsg(sender, "You don't have permission.", ChatColor.RED);
                    return true;
                }
                FOPMR_Commons.adminAction(sender.getName(), "Cleared all potion effects from all players", true);
                for (Player target : Bukkit.getOnlinePlayers())
                {
                    for (PotionEffect potion_effect : target.getActivePotionEffects())
                    {
                        target.removePotionEffect(potion_effect.getType());
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("clear"))
            {
                Player target = sender_p;

                if (args.length == 2)
                {
                    target = getPlayer(args[1]);

                    if (target == null)
                    {
                        FOPMR_Commons.playerMsg(sender, "Player was not found.", ChatColor.RED);
                        return true;
                    }
                }

                if (!target.equals(sender_p))
                {
                    if (!FOPMR_Rank.isAdmin(sender))
                    {
                        FOPMR_Commons.playerMsg(sender, "Only superadmins can clear potion effects from other players.");
                        return true;
                    }
                }
                else if (!(sender instanceof Player))
                {
                    FOPMR_Commons.playerMsg(sender, "You must specify a target player when using this command from the console.");
                    return true;
                }

                for (PotionEffect potion_effect : target.getActivePotionEffects())
                {
                    target.removePotionEffect(potion_effect.getType());
                }

                FOPMR_Commons.playerMsg(sender, "Cleared all active potion effects " + ((!target.equals(sender_p)) ? "from player " + target.getName() + "." : "from yourself."), ChatColor.AQUA);
            }
            else
            {
                return false;
            }
        }
        else if (args.length == 4 || args.length == 5)
        {
            if (args[0].equalsIgnoreCase("add"))
            {
                Player target = sender_p;

                if (args.length == 5)
                {

                    target = getPlayer(args[4]);

                    if (target == null)
                    {
                        FOPMR_Commons.playerMsg(sender, "Player was not found.", ChatColor.RED);
                        return true;
                    }
                }

                if (!target.equals(sender_p))
                {
                    if (!FOPMR_Rank.isAdmin(sender))
                    {
                        sender.sendMessage("Only superadmins can apply potion effects to other players.");
                        return true;
                    }
                }
                else if (!(sender instanceof Player))
                {
                    sender.sendMessage("You must specify a target player when using this command from the console.");
                    return true;
                }

                PotionEffectType potion_effect_type = PotionEffectType.getByName(args[1]);
                if (potion_effect_type == null)
                {
                    sender.sendMessage(ChatColor.AQUA + "Invalid potion effect type.");
                    return true;
                }

                int duration;
                try
                {
                    duration = Integer.parseInt(args[2]);
                    duration = Math.min(duration, 100000);
                } catch (NumberFormatException ex)
                {
                    FOPMR_Commons.playerMsg(sender, "Invalid potion duration.", ChatColor.RED);
                    return true;
                }

                int amplifier;
                try
                {
                    amplifier = Integer.parseInt(args[3]);
                    amplifier = Math.min(amplifier, 100000);
                } catch (NumberFormatException ex)
                {
                    FOPMR_Commons.playerMsg(sender, "Invalid potion amplifier.", ChatColor.RED);
                    return true;
                }

                PotionEffect new_effect = potion_effect_type.createEffect(duration, amplifier);
                target.addPotionEffect(new_effect, true);
                FOPMR_Commons.playerMsg(sender, "Added potion effect: " + new_effect.getType().getName() + ", Duration: " + new_effect.getDuration() + ", Amplifier: " + new_effect.getAmplifier() + (!target.equals(sender_p) ? " to player " + target.getName() + "." : " to yourself."), ChatColor.AQUA);

                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;
    }

}
