package fr.taeron.shadow.checks.movement;
import org.bukkit.entity.*;
import java.util.*;
import fr.taeron.shadow.checks.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import fr.taeron.shadow.utils.*;
import fr.taeron.shadow.alerts.*;

public class Glide extends Check implements Listener
{
    private Map<UUID, Long> flyTicks;
    private Map<Player, Long> lastTeleport;
    
    public Glide() {
        this.flyTicks = new HashMap<UUID, Long>();
        this.lastTeleport = new HashMap<Player, Long>();
        this.setType(CheatType.Mouvement);
        this.setName("Glide");
        this.setViolationsToAlert(2);
        this.setAutoban(false);
    }
    
    @EventHandler
    public void onTPBypass(final PlayerTeleportEvent e) {
        this.lastTeleport.put(e.getPlayer(), System.currentTimeMillis());
    }
    
    @EventHandler
    public void CheckGlide(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        if (UtilCheat.isInWeb(player)) {
            return;
        }
        if (player.getVehicle() != null) {
            return;
        }
        if (this.lastTeleport.containsKey(player) && System.currentTimeMillis() - this.lastTeleport.get(player) < 5000L) {
            return;
        }
        if (UtilCheat.blocksNear(player)) {
            if (this.flyTicks.containsKey(player.getUniqueId())) {
                this.flyTicks.remove(player.getUniqueId());
            }
            return;
        }
        if (event.getTo().getX() == event.getFrom().getX() && event.getTo().getZ() == event.getFrom().getZ()) {
            return;
        }
        final double OffsetY = event.getFrom().getY() - event.getTo().getY();
        if (OffsetY <= 0.0 || OffsetY > 0.16) {
            if (this.flyTicks.containsKey(player.getUniqueId())) {
                this.flyTicks.remove(player.getUniqueId());
            }
            return;
        }
        long Time = System.currentTimeMillis();
        if (this.flyTicks.containsKey(player.getUniqueId())) {
            Time = this.flyTicks.get(player.getUniqueId());
        }
        final long MS = System.currentTimeMillis() - Time;
        if (MS > 1000L) {
            new ShadowAlert(player, this);
            this.flyTicks.remove(player.getUniqueId());
            return;
        }
        this.flyTicks.put(player.getUniqueId(), Time);
    }
}
