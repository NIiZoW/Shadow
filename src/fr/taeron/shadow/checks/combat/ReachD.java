package fr.taeron.shadow.checks.combat;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilAngle;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ReachD
  extends Check
{
  public ReachD()
  {
    setName("Reach (Check D) [EN TEST]");
    setType(CheatType.Combat);
    setAutoban(false);
  }
  
  private HashMap<Player, Long> lastViolation = new HashMap<Player, Long>();
  private HashMap<Player, Integer> outOfReachHits = new HashMap<Player, Integer>();
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
  public void onDamage(EntityDamageByEntityEvent e)
  {
    if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
      return;
    }
    if (!(e.getDamager() instanceof Player)) {
      return;
    }
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    Player damager = (Player)e.getDamager();
    Player player = (Player)e.getEntity();
    if (damager.getAllowFlight()) {
      return;
    }
    if (player.getAllowFlight()) {
      return;
    }
    double MaxReach = 4.1D + player.getVelocity().length() * 4.0D + damager.getVelocity().length() * 4.0D;
    
    double Reach = UtilAngle.getDistance(damager.getLocation().getX(), damager.getLocation().getZ(), player.getLocation().getX(), player.getLocation().getZ());
    int Ping = Shadow.getInstance().getPlayerManager().getByPlayer(damager).getPing();
    if ((Ping >= 100) && (Ping < 200)) {
      MaxReach += 0.2D;
    } else if ((Ping >= 200) && (Ping < 250)) {
      MaxReach += 0.4D;
    } else if ((Ping >= 250) && (Ping < 300)) {
      MaxReach += 0.7D;
    } else if ((Ping >= 300) && (Ping < 350)) {
      MaxReach += 1.0D;
    } else if ((Ping >= 350) && (Ping < 400)) {
      MaxReach += 1.4D;
    } else if (Ping > 400) {
      return;
    }
    if (damager.getLocation().getY() > player.getLocation().getY())
    {
      double Difference = damager.getLocation().getY() - player.getLocation().getY();
      MaxReach += Difference / 2.0D;
    }
    else if (player.getLocation().getY() > damager.getLocation().getY())
    {
      double Difference = player.getLocation().getY() - damager.getLocation().getY();
      MaxReach += Difference / 2.0D;
    }
    if (Reach > MaxReach)
    {
      if (!this.outOfReachHits.containsKey(damager))
      {
        this.outOfReachHits.put(damager, Integer.valueOf(1));
      }
      else
      {
        this.outOfReachHits.put(damager, Integer.valueOf(((Integer)this.outOfReachHits.get(damager)).intValue() + 1));
        if (((Integer)this.outOfReachHits.get(damager)).intValue() > 2)
        {
          this.outOfReachHits.remove(damager);
          if ((this.lastViolation.containsKey(damager)) && 
            (System.currentTimeMillis() - ((Long)this.lastViolation.get(damager)).longValue() < 2000L)) {
            return;
          }
          this.lastViolation.put(damager, Long.valueOf(System.currentTimeMillis()));
          new ShadowAlert(damager, this);
        }
      }
    }
    else if (this.outOfReachHits.containsKey(damager)) {
      this.outOfReachHits.put(damager, Integer.valueOf(((Integer)this.outOfReachHits.get(damager)).intValue() - 1));
    }
  }
  
  @EventHandler
  public void clear(PlayerQuitEvent e)
  {
    if (this.lastViolation.containsKey(e.getPlayer())) {
      this.lastViolation.remove(e.getPlayer());
    }
  }
}
