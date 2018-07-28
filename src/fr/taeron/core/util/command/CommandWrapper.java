package fr.taeron.core.util.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;

public class CommandWrapper
  implements CommandExecutor, TabCompleter
{
  private final Collection<CommandArgument> arguments;
  
  public CommandWrapper(Collection<CommandArgument> arguments)
  {
    this.arguments = arguments;
  }
  
  public static void printUsage(CommandSender sender, String label, Collection<CommandArgument> arguments)
  {
    sender.sendMessage(ChatColor.DARK_AQUA + "*** " + WordUtils.capitalizeFully(label) + " Help ***");
    for (CommandArgument argument : arguments)
    {
      String permission = argument.getPermission();
      if ((permission == null) || (sender.hasPermission(permission))) {
        sender.sendMessage(ChatColor.GRAY + argument.getUsage(label) + " - " + argument.getDescription());
      }
    }
  }
  
  public static CommandArgument matchArgument(String id, CommandSender sender, Collection<CommandArgument> arguments)
  {
    for (CommandArgument argument : arguments)
    {
      String permission = argument.getPermission();
      if (((permission == null) || (sender.hasPermission(permission))) && ((argument.getName().equalsIgnoreCase(id)) || (Arrays.asList(argument.getAliases()).contains(id)))) {
        return argument;
      }
    }
    return null;
  }
  
  public static List<String> getAccessibleArgumentNames(CommandSender sender, Collection<CommandArgument> arguments)
  {
    List<String> results = new ArrayList<String>();
    for (CommandArgument argument : arguments)
    {
      String permission = argument.getPermission();
      if ((permission == null) || (sender.hasPermission(permission))) {
        results.add(argument.getName());
      }
    }
    return results;
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 1)
    {
      printUsage(sender, label, this.arguments);
      return true;
    }
    CommandArgument argument = matchArgument(args[0], sender, this.arguments);
    if (argument == null)
    {
      printUsage(sender, label, this.arguments);
      return true;
    }
    return argument.onCommand(sender, command, label, args);
  }
  
  @SuppressWarnings("serial")
public static class ArgumentComparator
    implements Comparator<CommandArgument>, Serializable
  {
    public int compare(CommandArgument primaryArgument, CommandArgument secondaryArgument)
    {
      return secondaryArgument.getName().compareTo(primaryArgument.getName());
    }
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
  {
    return null;
  }
}
