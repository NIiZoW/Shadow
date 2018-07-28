package fr.taeron.shadow.checks.movement;

import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilCheat;
import fr.taeron.shadow.utils.UtilPlayer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HFly
  extends Check
  implements Listener
{
  private Map<UUID, Long> flyTicks;
  
  public HFly()
  {
    this.flyTicks = new HashMap<UUID, Long>();
    setMaximumViolation(5);
    setName("Fly (Horizontal)");
    setType(CheatType.Mouvement);
  }
  
  @EventHandler
  public void CheckFly(PlayerMoveEvent event)
  {
    Player player = event.getPlayer();
    if (player.getAllowFlight()) {
      return;
    }
    if (player.getVehicle() != null) {
      return;
    }
    if (UtilPlayer.isInWater(player)) {
      return;
    }
    if (UtilCheat.isInWeb(player)) {
      return;
    }
    if (UtilCheat.blocksNear(player))
    {
      if (this.flyTicks.containsKey(player.getUniqueId())) {
        this.flyTicks.remove(player.getUniqueId());
      }
      return;
    }
    if ((event.getTo().getX() == event.getFrom().getX()) && (event.getTo().getZ() == event.getFrom().getZ())) {
      return;
    }
    if (event.getTo().getY() != event.getFrom().getY())
    {
      if (this.flyTicks.containsKey(player.getUniqueId())) {
        this.flyTicks.remove(player.getUniqueId());
      }
      return;
    }
    long Time = System.currentTimeMillis();
    if (this.flyTicks.containsKey(player.getUniqueId())) {
      Time = ((Long)this.flyTicks.get(player.getUniqueId())).longValue();
    }
    long MS = System.currentTimeMillis() - Time;
    if (MS > 600L)
    {
      this.flyTicks.remove(player.getUniqueId());
      new ShadowAlert(player, this);
      return;
    }
    this.flyTicks.put(player.getUniqueId(), Long.valueOf(Time));
  }
}
