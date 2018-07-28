package fr.taeron.core.util.command;

import com.google.common.collect.ImmutableList;

import fr.taeron.shadow.utils.BukkitUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public abstract class ArgumentExecutor
  implements CommandExecutor, TabCompleter
{
  protected final List<CommandArgument> arguments;
  protected final String label;
  
  public ArgumentExecutor(String label)
  {
    this.arguments = new ArrayList<CommandArgument>();
    this.label = label;
  }
  
  public boolean containsArgument(CommandArgument argument)
  {
    return this.arguments.contains(argument);
  }
  
  public void addArgument(CommandArgument argument)
  {
    this.arguments.add(argument);
  }
  
  public void removeArgument(CommandArgument argument)
  {
    this.arguments.remove(argument);
  }
  
  public CommandArgument getArgument(String id)
  {
    for (CommandArgument argument : this.arguments)
    {
      String name = argument.getName();
      if ((name.equalsIgnoreCase(id)) || (Arrays.asList(argument.getAliases()).contains(id.toLowerCase()))) {
        return argument;
      }
    }
    return null;
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public List<CommandArgument> getArguments()
  {
    return ImmutableList.copyOf(this.arguments);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 1)
    {
      sender.sendMessage("§6§l§m---*-----------------------------------*---");
      sender.sendMessage("§6§6Aide §7(" + WordUtils.capitalizeFully(this.label) + ")");
      for (CommandArgument argument : this.arguments)
      {
        String permission = argument.getPermission();
        if ((permission == null) || (sender.hasPermission(permission))) {
          sender.sendMessage(" §6* " + ChatColor.YELLOW + argument.getUsage(label) + ChatColor.GRAY + " -> " + argument.getDescription());
        }
      }
      sender.sendMessage("§6§l§m---*-----------------------------------*---");
      return true;
    }
    CommandArgument argument2 = getArgument(args[0]);
    String permission2 = argument2 == null ? null : argument2.getPermission();
    if ((argument2 == null) || ((permission2 != null) && (!sender.hasPermission(permission2))))
    {
      sender.sendMessage(ChatColor.RED + "L'argument " + WordUtils.capitalizeFully(this.label) + ' ' + args[0] + " n'existe pas.");
      return true;
    }
    argument2.onCommand(sender, command, label, args);
    return true;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
  {
    List<String> results = new ArrayList<String>();
    if (args.length < 2)
    {
      for (CommandArgument argument : this.arguments)
      {
        String permission = argument.getPermission();
        if ((permission == null) || (sender.hasPermission(permission))) {
          results.add(argument.getName());
        }
      }
    }
    else
    {
      CommandArgument argument2 = getArgument(args[0]);
      if (argument2 == null) {
        return results;
      }
      String permission2 = argument2.getPermission();
      if ((permission2 == null) || (sender.hasPermission(permission2)))
      {
        results = argument2.onTabComplete(sender, command, label, args);
        if (results == null) {
          return null;
        }
      }
    }
    return BukkitUtils.getCompletions(args, results);
  }
}
