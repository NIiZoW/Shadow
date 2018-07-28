package fr.taeron.shadow.utils;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.player.APlayer;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoBanTimer
{
  public static ArrayList<String> nameList = new ArrayList<String>();
  
  public static void startTimer(final CheatType type, final String name, Player p)
  {
    if (nameList.contains(p.getName())) {
      return;
    }
    final APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
    nameList.add(p.getName());
    UtilActionMessage msg = new UtilActionMessage();
    msg.addText(Shadow.getInstance().getPrefix());
    msg.addText(String.valueOf(ChatColor.RESET) + p.getName()).addHoverText(new String[] { String.valueOf(ChatColor.YELLOW) + "Clique pour te t§l§porter § " + ChatColor.RED + p.getName() }).setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/logs " + p.getName());
    msg.addText(String.valueOf(ChatColor.GRAY) + " va §tre");
    msg.addText(String.valueOf(ChatColor.RED) + " banni");
    msg.addText(String.valueOf(ChatColor.GRAY) + " pour");
    msg.addText(String.valueOf(ChatColor.BLUE) + " " + name);
    msg.addText(String.valueOf(ChatColor.GRAY) + " dans 5 secondes. ");
    msg.addText(String.valueOf(ChatColor.GREEN) + ChatColor.BOLD + "[Freeze]").addHoverText(new String[] { String.valueOf(ChatColor.GRAY) + "Clique pour freeze " + ChatColor.RED + p.getName() }).setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/freeze " + p.getName());
    msg.addText(" ");
    msg.addText(String.valueOf(ChatColor.RED) + ChatColor.BOLD + "[Annuler]").addHoverText(new String[] { String.valueOf(ChatColor.GRAY) + "Clique pour annuler l'autoban de " + ChatColor.RED + p.getName() }).setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/cancelautoban " + p.getName());
    msg.addText(" ");
    msg.addText(String.valueOf(ChatColor.DARK_RED) + ChatColor.BOLD + "[Ban]").addHoverText(new String[] { String.valueOf(ChatColor.GRAY) + "Clique pour bannir " + ChatColor.RED + p.getName() }).setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/sban " + p.getName());
    Player[] players;
    int length = (players = UtilServer.getPlayers()).length;
    for (int i = 0; i < length; i++)
    {
      Player playerplayer = players[i];
      if (playerplayer.hasPermission("heaven.staff"))
      {
        playerplayer.sendMessage(ChatColor.RED + BukkitUtils.STRAIGHT_LINE_DEFAULT);
        msg.sendToPlayer(playerplayer);
        playerplayer.sendMessage(ChatColor.RED + BukkitUtils.STRAIGHT_LINE_DEFAULT);
      }
    }
    new BukkitRunnable()
    {
      public void run()
      {
        if (AutoBanTimer.nameList.contains(p.getName())) {
          ap.banPlayer(type, name, false);
        }
        AutoBanTimer.nameList.remove(p.getName());
      }
    }.runTaskLaterAsynchronously(Shadow.getInstance(), 100L);
  }
}
