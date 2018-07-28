package fr.taeron.shadow.utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class UtilActionMessage
{
  private List<AMText> Text;
  
  public UtilActionMessage()
  {
    this.Text = new ArrayList<AMText>();
  }
  
  public AMText addText(String Message)
  {
    AMText Text = new AMText(Message);
    this.Text.add(Text);
    return Text;
  }
  
  public String getFormattedMessage()
  {
    String Chat = "[\"\",";
    for (AMText Text : this.Text) {
      Chat = String.valueOf(Chat) + Text.getFormattedMessage() + ",";
    }
    Chat = Chat.substring(0, Chat.length() - 1);
    Chat = String.valueOf(Chat) + "]";
    return Chat;
  }
  
  public void sendToPlayer(Player Player)
  {
    IChatBaseComponent base = ChatSerializer.a(getFormattedMessage());
    PacketPlayOutChat packet = new PacketPlayOutChat(base, 1);
    ((CraftPlayer)Player).getHandle().playerConnection.sendPacket(packet);
  }
  
  public static enum ClickableType
  {
    RunCommand("RunCommand", 0, "run_command"),  SuggestCommand("SuggestCommand", 1, "suggest_command"),  OpenURL("OpenURL", 2, "open_url");
    
    public String Action;
    
    private ClickableType(String s, int n, String Action)
    {
      this.Action = Action;
    }
  }
  
  public class AMText
  {
    private String Message;
    private Map<String, Map.Entry<String, String>> Modifiers;
    
    public AMText(String Text)
    {
      this.Message = "";
      this.Modifiers = new HashMap<String, Entry<String, String>>();
      this.Message = Text;
    }
    
    public String getMessage()
    {
      return this.Message;
    }
    
    public String getFormattedMessage()
    {
      String Chat = "{\"text\":\"" + this.Message + "\"";
      for (String Event : this.Modifiers.keySet())
      {
          final Map.Entry<String, String> Modifier = this.Modifiers.get(Event);
        Chat = String.valueOf(Chat) + ",\"" + Event + "\":{\"action\":\"" + (String)Modifier.getKey() + "\",\"value\":" + (String)Modifier.getValue() + "}";
      }
      Chat = String.valueOf(Chat) + "}";
      return Chat;
    }
    
    public AMText addHoverText(String... Text)
    {
        String Value = "";
      if (Text.length == 1)
      {
        Value = "{\"text\":\"" + Text[0] + "\"}";
      }
      else
      {
        Value = "{\"text\":\"\",\"extra\":[";
        String[] arrayOfString;
        int j = (arrayOfString = Text).length;
        for (int i = 0; i < j; i++)
        {
          String Message = arrayOfString[i];
          Value = String.valueOf(Value) + "{\"text\":\"" + Message + "\"},";
        }
        Value = Value.substring(0, Value.length() - 1);
        Value = String.valueOf(Value) + "]}";
      }
      final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<String, String>("show_text", Value);
      this.Modifiers.put("hoverEvent", Values);
      return this;
    }
    
    public AMText addHoverItem(org.bukkit.inventory.ItemStack Item)
    {
        String Value = CraftItemStack.asNMSCopy(Item).getTag().toString();
      final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<String, String>("show_item", Value);
      this.Modifiers.put("hoverEvent", Values);
      return this;
    }
    
    public AMText setClickEvent(UtilActionMessage.ClickableType Type, String Value)
    {
        final String Key = Type.Action;
        final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<String, String>(Key, "\"" + Value + "\"");
      this.Modifiers.put("clickEvent", Values);
      return this;
    }
  }
}
