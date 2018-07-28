package fr.taeron.shadow.checks.combat;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilCheat;
import fr.taeron.shadow.utils.UtilTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Criticals
extends Check
implements Listener {
    private Map<UUID, Map.Entry<Integer, Long>> CritTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
    private Map<UUID, Double> FallDistance = new HashMap<UUID, Double>();
    private Map<Player, Long> lastHit = new HashMap<Player, Long>();

    public Criticals() {
        this.setType(CheatType.Combat);
        this.setName("Criticals");
        this.setMaximumViolation(5);
        this.setViolationsToAlert(2);
    }

    @SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!e.getCause().equals((Object)EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }
        Player player = (Player)e.getDamager();
        Player p = (Player)e.getEntity();
        if (!this.lastHit.containsKey((Object)p)) {
            this.lastHit.put(p, UtilTime.nowlong());
        } else {
            if (UtilTime.nowlong() - this.lastHit.get((Object)p) < 1500) {
                return;
            }
            this.lastHit.remove((Object)p);
        }
        if (player.getAllowFlight()) {
            return;
        }
        if (Shadow.getInstance().LastVelocity.containsKey(player.getUniqueId())) {
            return;
        }
        if (UtilCheat.slabsNear(player.getLocation())) {
            return;
        }
        Location pL = player.getLocation().clone();
        pL.add(0.0, player.getEyeHeight() + 1.0, 0.0);
        if (UtilCheat.blocksNear(pL)) {
            return;
        }
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (this.CritTicks.containsKey(player.getUniqueId())) {
            Count = this.CritTicks.get(player.getUniqueId()).getKey();
            Time = this.CritTicks.get(player.getUniqueId()).getValue();
        }
        if (!this.FallDistance.containsKey(player.getUniqueId())) {
            return;
        }
        double realFallDistance = this.FallDistance.get(player.getUniqueId());
        Count = (double)player.getFallDistance() > 0.0 && !player.isOnGround() && realFallDistance == 0.0 ? ++Count : 0;
        if (this.CritTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 10000)) {
            Count = 0;
            Time = UtilTime.nowlong();
        }
        if (Count >= 2) {
            Count = 0;
            new fr.taeron.shadow.alerts.ShadowAlert(player, this);
        }
        this.CritTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void Move(PlayerMoveEvent e) {
        Player Player2 = e.getPlayer();
        double Falling = 0.0;
        if (!Player2.isOnGround() && e.getFrom().getY() > e.getTo().getY()) {
            if (this.FallDistance.containsKey(Player2.getUniqueId())) {
                Falling = this.FallDistance.get(Player2.getUniqueId());
            }
            Falling += e.getFrom().getY() - e.getTo().getY();
        }
        this.FallDistance.put(Player2.getUniqueId(), Falling);
    }
}

