package fr.taeron.shadow.checks.combat;

import com.comphenix.protocol.wrappers.EnumWrappers;
import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.packets.events.PacketUseEntityEvent;
import fr.taeron.shadow.player.APlayer;
import fr.taeron.shadow.utils.UtilDirection;
import fr.taeron.shadow.utils.UtilPlayer;
import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ReachB
  extends Check
  implements Listener
{
  public HashMap<Player, Integer> violations;
  public HashMap<Player, Double> lastReach;
  public HashMap<Player, Long> lastAlert;
  
  public ReachB()
  {
    setName("Reach (Check B)");
    setType(CheatType.Combat);
    this.violations = new HashMap<Player, Integer>();
    this.lastReach = new HashMap<Player, Double>();
    this.lastAlert = new HashMap<Player, Long>();
    setMaxPing(210);
    setMaximumViolation(6);
    setViolationsToJday(4);
    setJudgementDay(Boolean.valueOf(true));
  }
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
  public void UseEntity(PacketUseEntityEvent e)
  {
    if (!(e.getAttacked() instanceof Player)) {
      return;
    }
    if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK) {
      return;
    }
    Player p = e.getAttacker();
    Player t = (Player)e.getAttacked();
    if ((UtilDirection.yawToFace(p.getLocation().getYaw()) == BlockFace.NORTH) && (UtilDirection.yawToFace(t.getLocation().getYaw()) != BlockFace.SOUTH)) {
      return;
    }
    if ((UtilDirection.yawToFace(p.getLocation().getYaw()) == BlockFace.SOUTH) && (UtilDirection.yawToFace(t.getLocation().getYaw()) != BlockFace.NORTH)) {
      return;
    }
    if ((UtilDirection.yawToFace(p.getLocation().getYaw()) == BlockFace.EAST) && (UtilDirection.yawToFace(t.getLocation().getYaw()) != BlockFace.WEST)) {
      return;
    }
    if ((UtilDirection.yawToFace(p.getLocation().getYaw()) == BlockFace.WEST) && (UtilDirection.yawToFace(t.getLocation().getYaw()) != BlockFace.EAST)) {
      return;
    }
    if (p.getGameMode().equals(GameMode.CREATIVE)) {
      return;
    }
    APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
    if (System.currentTimeMillis() - ap.lastEntityUsePacket < 5L) {
      return;
    }
    ap.lastEntityUsePacket = System.currentTimeMillis();
    Location pLoc = p.getLocation();
    pLoc.setY(UtilPlayer.getEyeLocation(p).getY());
    Location tLoc = t.getLocation();
    tLoc.setY(UtilPlayer.getEyeLocation(t).getY());
    double distance = pLoc.distance(tLoc);
    double MaxReach = 3.9D;
    if (p.hasPotionEffect(PotionEffectType.SPEED))
    {
      int Level = 0;
      for (PotionEffect Effect : p.getActivePotionEffects()) {
        if (Effect.getType().equals(PotionEffectType.SPEED))
        {
          Level = Effect.getAmplifier();
          break;
        }
      }
      switch (Level)
      {
      case 0: 
        MaxReach = 3.9D;
        break;
      case 1: 
        MaxReach = 4.0D;
        break;
      case 2: 
        MaxReach = 4.0D;
        break;
      case 3: 
        MaxReach = 4.3D;
        break;
      case 4: 
        MaxReach = 4.3D;
        break;
      case 5: 
        MaxReach = 4.3D;
      }
    }
    CraftPlayer cp = (CraftPlayer)p;
    if (cp.getHandle().ping > 130) {
      MaxReach += 0.1D;
    } else if (cp.getHandle().ping > 180) {
      MaxReach += 0.2D;
    }
    if (t.getMaximumNoDamageTicks() == 4) {
      MaxReach += 0.4D;
    }
    MaxReach += Math.abs((UtilPlayer.getEyeLocation(p).getY() - UtilPlayer.getEyeLocation(t).getY()) / 2.0D);
    if (distance >= MaxReach)
    {
      if (!this.lastReach.containsKey(p)) {
        this.lastReach.put(p, Double.valueOf(distance));
      } else if (((Double)this.lastReach.get(p)).doubleValue() >= MaxReach) {
        if (!this.violations.containsKey(p))
        {
          this.violations.put(p, Integer.valueOf(1));
        }
        else
        {
          this.violations.put(p, Integer.valueOf(((Integer)this.violations.get(p)).intValue() + 1));
          if (((Integer)this.violations.get(p)).intValue() >= 6)
          {
            this.violations.remove(p);
            if (!this.lastAlert.containsKey(p)) {
              this.lastAlert.put(p, Long.valueOf(System.currentTimeMillis()));
            } else if (System.currentTimeMillis() - ((Long)this.lastAlert.get(p)).longValue() < 1999L) {
              return;
            }
            this.lastAlert.put(p, Long.valueOf(System.currentTimeMillis()));
            new ShadowAlert(p, this);
          }
        }
      }
    }
    else
    {
      if (this.lastReach.containsKey(p)) {
        this.lastReach.remove(p);
      }
      if (this.violations.containsKey(p)) {
        this.violations.remove(p);
      }
    }
  }
}
