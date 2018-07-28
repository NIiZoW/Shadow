package fr.taeron.shadow.checks.movement;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilPlayer;
import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VFly
  extends Check
  implements Listener
{
  public HashMap<Player, Integer> positifYAirMoves;
  public HashMap<Player, Material> lastBlockBelowPlayer;
  
  public VFly()
  {
    this.positifYAirMoves = new HashMap<Player, Integer>();
    this.lastBlockBelowPlayer = new HashMap<Player, Material>();
    setInstantBan(false);
    setMaximumViolation(4);
    setName("Fly (Vertical)");
    setType(CheatType.Mouvement);
    setBanTimer(false);
  }
  
  @EventHandler
  public void CheckYMovements(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    if ((p.getAllowFlight()) || (p.getGameMode().equals(GameMode.CREATIVE)) || (Shadow.getInstance().LastVelocity.containsKey(p))) {
      return;
    }
    if (p.getVelocity().getY() > 0.0D) {
      return;
    }
    if (!Shadow.getInstance().isShadowEnabled()) {
      return;
    }
    if (UtilPlayer.isInWater(p)) {
      return;
    }
    double maxMoves = 7.0D;
    for (PotionEffect effect : p.getActivePotionEffects()) {
      if (effect.getType() == PotionEffectType.JUMP) {
        maxMoves += effect.getAmplifier() * 2;
      }
    }
    double distance = e.getTo().getY() - e.getFrom().getY();
    if (distance < 0.2D)
    {
      this.positifYAirMoves.put(p, Integer.valueOf(0));
      return;
    }
    if (!this.lastBlockBelowPlayer.containsKey(p))
    {
      this.lastBlockBelowPlayer.put(p, p.getLocation().getBlock().getRelative(0, -1, 0).getType());
    }
    else if (!this.positifYAirMoves.containsKey(p))
    {
      this.positifYAirMoves.put(p, Integer.valueOf(1));
    }
    else
    {
      this.positifYAirMoves.put(p, Integer.valueOf(((Integer)this.positifYAirMoves.get(p)).intValue() + 1));
      if ((((Integer)this.positifYAirMoves.get(p)).intValue() > maxMoves) && (this.lastBlockBelowPlayer.get(p) == Material.AIR))
      {
        new ShadowAlert(p, this);
        this.positifYAirMoves.put(p, Integer.valueOf(0));
      }
    }
    this.lastBlockBelowPlayer.put(p, p.getLocation().getBlock().getRelative(0, -1, 0).getType());
  }
}
