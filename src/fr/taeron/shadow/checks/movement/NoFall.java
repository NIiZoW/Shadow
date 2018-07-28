/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package fr.taeron.shadow.checks.movement;

import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilCheat;
import fr.taeron.shadow.utils.UtilPlayer;
import fr.taeron.shadow.utils.UtilTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFall
extends Check
implements Listener {
    private Map<UUID, Map.Entry<Long, Integer>> NoFallTicks = new HashMap<UUID, Map.Entry<Long, Integer>>();
    private Map<UUID, Double> FallDistance = new HashMap<UUID, Double>();

    public NoFall() {
        this.setName("NoFall");
        this.setType(CheatType.Mouvement);
        this.setMaximumViolation(4);
        this.setBanTimer(false);
    }

    @SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void Move(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        if (player.getGameMode().equals((Object)GameMode.CREATIVE)) {
            return;
        }
        if (player.getVehicle() != null) {
            return;
        }
        if (((Damageable)player).getHealth() <= 0.0) {
            return;
        }
        if (UtilPlayer.isOnClimbable(player)) {
            return;
        }
        if (UtilPlayer.isInWater(player)) {
            return;
        }
        if (UtilCheat.isInWeb(player)) {
            return;
        }
        if (player.getLocation().getBlock().getType() != Material.AIR) {
            return;
        }
        double Falling = 0.0;
        if (!UtilPlayer.isOnGround(player) && e.getFrom().getY() > e.getTo().getY()) {
            if (this.FallDistance.containsKey(player.getUniqueId())) {
                Falling = this.FallDistance.get(player.getUniqueId());
            }
            Falling += e.getFrom().getY() - e.getTo().getY();
        }
        this.FallDistance.put(player.getUniqueId(), Falling);
        if (Falling < 3.0) {
            return;
        }
        long Time = System.currentTimeMillis();
        int Count = 0;
        if (this.NoFallTicks.containsKey(player.getUniqueId())) {
            Time = this.NoFallTicks.get(player.getUniqueId()).getKey();
            Count = this.NoFallTicks.get(player.getUniqueId()).getValue();
        }
        Count = player.isOnGround() || player.getFallDistance() == 0.0f ? ++Count : 0;
        if (this.NoFallTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 10000)) {
            Count = 0;
            Time = System.currentTimeMillis();
        }
        if (Count >= 3) {
            Count = 0;
            this.FallDistance.put(player.getUniqueId(), 0.0);
            new fr.taeron.shadow.alerts.ShadowAlert(player, this);
        }
        this.NoFallTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Long, Integer>(Time, Count));
    }
}

