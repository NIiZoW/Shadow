package fr.taeron.shadow.basiclisteners;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.player.APlayer;
import fr.taeron.shadow.utils.AutoBanTimer;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BasicListener
  implements Listener
{
  @EventHandler
  public void onJoin(final PlayerJoinEvent e)
    throws IOException
  {
    Bukkit.getScheduler().runTaskLater(Shadow.getInstance(), new Runnable()
    {
      public void run()
      {
        if (!e.getPlayer().isOnline()) {
          return;
        }
        try
        {
          APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(e.getPlayer());
          if (ap != null) {
            return;
          }
          Shadow.getInstance().getPlayerManager().registerNewPlayer(e.getPlayer());
        }
        catch (IOException e1)
        {
          e.getPlayer().kickPlayer("§cUne erreur est survenue lors de la creation de ton fichier de data, essaye de te reconnecter. \nSi le probléme persiste, contacte un administrateur");
          e1.printStackTrace();
        }
      }
    }, 20L);
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    Shadow.getInstance().getPlayerManager().removeByPlayer(e.getPlayer());
    if (AutoBanTimer.nameList.contains(e.getPlayer().getName())) {
      AutoBanTimer.nameList.remove(e.getPlayer().getName());
    }
  }
  
  @EventHandler
  public void fixNonRegistered(PlayerMoveEvent e)
  {
    APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(e.getPlayer());
    if (ap == null) {
      try
      {
        Shadow.getInstance().getPlayerManager().registerNewPlayer(e.getPlayer());
      }
      catch (IOException e2)
      {
        e2.printStackTrace();
      }
    }
  }
  
  @EventHandler
  public void updateTarget(EntityDamageByEntityEvent e)
  {
    if (!(e.getDamager() instanceof Player)) {
      return;
    }
    Player p = (Player)e.getDamager();
    APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
    if (ap.getTarget() == null)
    {
      ap.target = e.getEntity();
    }
    else
    {
      ap.lastTarget = ap.target;
      ap.target = e.getEntity();
    }
  }
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
  public void updatePosition(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
    ap.lastLocation = e.getFrom();
    ap.location = e.getTo();
    ap.lastLocX = ap.locX;
    ap.lastLocZ = ap.locZ;
    ap.locX = e.getTo().getX();
    ap.locZ = e.getTo().getZ();
    ap.lastYaw = ap.yaw;
    ap.yaw = e.getPlayer().getLocation().getYaw();
  }
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
  public void updatePositionOnDamage(EntityDamageByEntityEvent e)
  {
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    Player p = (Player)e.getEntity();
    APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
    if (ap == null) {
      return;
    }
    ap.lastLocation = ap.getLocation();
    ap.location = p.getLocation();
    ap.lastLocX = ap.locX;
    ap.lastLocZ = ap.locZ;
    ap.locX = ap.getLocation().getX();
    ap.locZ = ap.getLocation().getX();
    ap.lastYaw = ap.yaw;
    ap.yaw = ap.getLocation().getYaw();
  }
}
