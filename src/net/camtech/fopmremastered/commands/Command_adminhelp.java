package net.camtech.fopmremastered.commands;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Command_adminhelp extends FOPMR_Command
{
    public Command_adminhelp()
    {
        super("adminhelp", "/adminhelp", "Receive info on the new administrative commands in the FOPM: R.", Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        sender.sendMessage(ChatColor.GOLD + "Command :|: Description :|: Usage :|: Aliases");
        try
        {
            Pattern PATTERN = Pattern.compile("net/camtech/fopmremastered/commands/(Command_[^\\$]+)\\.class");
            CodeSource codeSource = FreedomOpModRemastered.class.getProtectionDomain().getCodeSource();
            if (codeSource != null)
            {
                ZipInputStream zip = new ZipInputStream(codeSource.getLocation().openStream());
                ZipEntry zipEntry;
                while ((zipEntry = zip.getNextEntry()) != null)
                {
                    String entryName = zipEntry.getName();
                    Matcher matcher = PATTERN.matcher(entryName);
                    if (matcher.find())
                    {
                        try
                        {
                            Class<?> commandClass = Class.forName("net.camtech.fopmremastered.commands." + matcher.group(1));
                            FOPMR_Command cmdconstructed;
                            if (commandClass.isAnnotationPresent(CommandParameters.class))
                            {
                                Annotation annotation = commandClass.getAnnotation(CommandParameters.class);
                                CommandParameters params = (CommandParameters) annotation;
                                cmdconstructed = new FOPMR_BlankCommand(params.name(), params.usage(), params.description(), Arrays.asList(params.aliases().split(", ")), params.rank(), commandClass);
                            }
                            else
                            {
                                Constructor construct = commandClass.getConstructor();
                                cmdconstructed = (FOPMR_Command) construct.newInstance();
                            }
                            String message = ChatColor.GOLD + cmdconstructed.command + ChatColor.GREEN + " :|: " + ChatColor.BLUE + cmdconstructed.description + ChatColor.GREEN + " :|: " + ChatColor.AQUA + cmdconstructed.usage;
                            if (!(cmdconstructed.alias == null) && !cmdconstructed.alias.isEmpty())
                            {
                                message = message + ChatColor.GREEN + " :|: " + ChatColor.RED + "[" + StringUtils.join(cmdconstructed.alias, ", ") + "]";
                            }
                            sender.sendMessage(message);
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
                        {
                            Bukkit.broadcastMessage("" + ex);
                        }
                    }
                }
            }
        } catch (Exception ex)
        {
            FreedomOpModRemastered.plugin.getLogger().severe(ex.getLocalizedMessage());
        }
        return true;
    }

}
