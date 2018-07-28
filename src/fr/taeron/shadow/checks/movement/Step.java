package fr.taeron.shadow.checks.movement;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilCheat;
import fr.taeron.shadow.utils.UtilPlayer;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.potion.PotionEffectType;

public class Step
extends Check
implements Listener {
    private HashMap<Player, Long> lastTeleport;

    public Step() {
        this.setName("Step");
        this.setType(CheatType.Mouvement);
        this.lastTeleport = new HashMap<Player, Long>();
        this.setAutoban(false);
    }

    public boolean isOnGround(Player player) {
        if (UtilPlayer.isOnClimbable(player)) {
            return false;
        }
        if (player.getVehicle() != null) {
            return false;
        }
        if (player.getLocation().getBlock().getType() != Material.AIR) {
            return false;
        }
        Material type = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
        if (type != Material.AIR && type.isBlock() && type.isSolid() && type != Material.LADDER && type != Material.VINE) {
            return true;
        }
        Location a = player.getLocation().clone();
        a.setY(a.getY() - 0.5);
        type = a.getBlock().getType();
        if (type != Material.AIR && type.isBlock() && type.isSolid() && type != Material.LADDER && type != Material.VINE) {
            return true;
        }
        a = player.getLocation().clone();
        a.setY(a.getY() + 0.5);
        type = a.getBlock().getRelative(BlockFace.DOWN).getType();
        if (!(type != Material.AIR && type.isBlock() && type.isSolid() && type != Material.LADDER && type != Material.VINE || UtilCheat.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER}))) {
            return false;
        }
        return true;
    }

    @EventHandler
    public void resetTeleport(PlayerTeleportEvent e) {
        this.lastTeleport.put(e.getPlayer(), System.currentTimeMillis());
    }

    @EventHandler
    public void vehicle(VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            Player p = (Player)e.getEntered();
            this.lastTeleport.put(p, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!this.isOnGround(player)) {
            return;
        }
        if (player.getAllowFlight()) {
            return;
        }
        if (UtilCheat.slabsNear(player.getLocation())) {
            return;
        }
        if (player.hasPotionEffect(PotionEffectType.JUMP)) {
            return;
        }
        if (Shadow.getInstance().LastVelocity.containsKey(player.getUniqueId())) {
            return;
        }
        if (player.getVehicle() != null) {
            return;
        }
        double yDist = event.getTo().getY() - event.getFrom().getY();
        if (yDist > 0.9 && player.getVelocity().getY() < 0.1) {
            if (!this.lastTeleport.containsKey((Object)player)) {
                new fr.taeron.shadow.alerts.ShadowAlert(player, this);
            } else if (System.currentTimeMillis() - this.lastTeleport.get((Object)player) > 3000) {
                new fr.taeron.shadow.alerts.ShadowAlert(player, this);
            }
        }
        this.lastTeleport.remove((Object)player);
    }
}

