package fr.taeron.core.util.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandArgument
{
  private final String name;
  protected boolean isPlayerOnly;
  protected String description;
  protected String permission;
  protected String[] aliases;
  
  public CommandArgument(final String name, final String description) {
      this(name, description, (String)null);
  }
  
  public CommandArgument(String name, String description, String permission)
  {
    this(name, description, permission, ArrayUtils.EMPTY_STRING_ARRAY);
  }
  
  public CommandArgument(String name, String description, String[] aliases)
  {
    this(name, description, null, aliases);
  }
  
  public CommandArgument(String name, String description, String permission, String[] aliases)
  {
    this.isPlayerOnly = false;
    this.name = name;
    this.description = description;
    this.permission = permission;
    this.aliases = ((String[])Arrays.copyOf(aliases, aliases.length));
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public boolean isPlayerOnly()
  {
    return this.isPlayerOnly;
  }
  
  public final String getDescription()
  {
    return this.description;
  }
  
  public final String getPermission()
  {
    return this.permission;
  }
  
  public final String[] getAliases()
  {
    if (this.aliases == null) {
      this.aliases = ArrayUtils.EMPTY_STRING_ARRAY;
    }
    return (String[])Arrays.copyOf(this.aliases, this.aliases.length);
  }
  
  public abstract String getUsage(String paramString);
  
  public abstract boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString);
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
  {
    return Collections.emptyList();
  }
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CommandArgument)) {
      return false;
    }
    CommandArgument that = (CommandArgument)o;
    if (this.name != null ? 
      !this.name.equals(that.name) : 
      
      that.name != null) {
      return false;
    }
    if (this.description != null ? 
      !this.description.equals(that.description) : 
      
      that.description != null) {
      return false;
    }
    if (this.permission != null)
    {
      if (this.permission.equals(that.permission)) {
        return Arrays.equals(this.aliases, that.aliases);
      }
    }
    else if (that.permission == null) {
      return Arrays.equals(this.aliases, that.aliases);
    }
    return false;
  }
  
  public int hashCode()
  {
    int result = this.name != null ? this.name.hashCode() : 0;
    result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
    result = 31 * result + (this.permission != null ? this.permission.hashCode() : 0);
    result = 31 * result + (this.aliases != null ? Arrays.hashCode(this.aliases) : 0);
    return result;
  }
}
