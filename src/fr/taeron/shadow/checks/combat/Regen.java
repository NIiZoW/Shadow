package fr.taeron.shadow.checks.combat;

import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class Regen
  extends Check
  implements Listener
{
  HashMap<UUID, Long> lastRegen;
  HashMap<Player, Long> lastViolation;
  
  public Regen()
  {
    this.lastRegen = new HashMap<UUID, Long>();
    this.lastViolation = new HashMap<Player, Long>();
    setType(CheatType.Combat);
    setName("FastHeal");
    setMaxPing(250);
    setAutoban(false);
  }
  
  @EventHandler
  public void onRegenerate(EntityRegainHealthEvent e)
  {
    if (((e.getEntity() instanceof Player)) && (e.getRegainReason() != EntityRegainHealthEvent.RegainReason.MAGIC_REGEN) && (e.getRegainReason() != EntityRegainHealthEvent.RegainReason.MAGIC) && (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED))
    {
      Player p = (Player)e.getEntity();
      if (((CraftPlayer)p).getHandle().ping > getMaxPing()) {
        return;
      }
      if (!this.lastRegen.containsKey(e.getEntity().getUniqueId()))
      {
        this.lastRegen.put(e.getEntity().getUniqueId(), Long.valueOf(System.currentTimeMillis()));
        return;
      }
      if (System.currentTimeMillis() - ((Long)this.lastRegen.get(e.getEntity().getUniqueId())).longValue() <= TimeUnit.SECONDS.toMillis(1L))
      {
        new ShadowAlert(p, this);
        if (!this.lastViolation.containsKey(p))
        {
          new ShadowAlert(p, this);
          this.lastViolation.put(p, Long.valueOf(System.currentTimeMillis()));
          return;
        }
        if (System.currentTimeMillis() - ((Long)this.lastViolation.get(p)).longValue() < 2000L) {
          return;
        }
        new ShadowAlert(p, this);
        this.lastViolation.put(p, Long.valueOf(System.currentTimeMillis()));
      }
      this.lastRegen.put(e.getEntity().getUniqueId(), Long.valueOf(System.currentTimeMillis()));
    }
  }
}
