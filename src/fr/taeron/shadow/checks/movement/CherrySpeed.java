package fr.taeron.shadow.checks.movement;

import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class CherrySpeed
  extends Check
  implements Listener
{
  public HashMap<Player, Double> lastSpeed;
  public HashMap<Player, Integer> violations;
  private HashMap<Player, Long> lastTeleport;
  
  public CherrySpeed()
  {
    this.lastSpeed = new HashMap<Player, Double>();
    this.violations = new HashMap<Player, Integer>();
    this.lastTeleport = new HashMap<Player, Long>();
    setName("Speed (Cherry)");
    setType(CheatType.Mouvement);
    setMaxPing(250);
    setMaximumViolation(4);
  }
  
  @EventHandler
  public void resetTeleport(PlayerTeleportEvent e)
  {
    this.lastTeleport.put(e.getPlayer(), Long.valueOf(System.currentTimeMillis()));
  }
  
  @EventHandler
  public void vehicle(VehicleEnterEvent e)
  {
    if ((e.getEntered() instanceof Player))
    {
      Player p = (Player)e.getEntered();
      this.lastTeleport.put(p, Long.valueOf(System.currentTimeMillis()));
    }
  }
  
  @EventHandler
  public void Move(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    double distanceZ = e.getTo().getZ() - e.getFrom().getZ();
    double distanceX = e.getTo().getX() - e.getFrom().getX();
    double realZ = Math.abs(distanceZ);
    double realX = Math.abs(distanceX);
    double total = realZ + realX;
    if (p.getVehicle() != null) {
      return;
    }
    if (!this.lastSpeed.containsKey(p))
    {
      this.lastSpeed.put(p, Double.valueOf(total));
    }
    else
    {
      if ((total > 1.5D) && (((Double)this.lastSpeed.get(p)).doubleValue() < 0.3D)) {
        if (!this.violations.containsKey(p))
        {
          this.violations.put(p, Integer.valueOf(1));
        }
        else
        {
          if (!this.lastTeleport.containsKey(p)) {
            new ShadowAlert(p, this);
          } else if (System.currentTimeMillis() - ((Long)this.lastTeleport.get(p)).longValue() > 1000L) {
            new ShadowAlert(p, this);
          }
          this.lastSpeed.remove(p);
          this.violations.remove(p);
        }
      }
      this.lastSpeed.put(p, Double.valueOf(total));
    }
  }
}
