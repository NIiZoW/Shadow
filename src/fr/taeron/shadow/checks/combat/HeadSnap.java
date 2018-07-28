
package fr.taeron.shadow.checks.combat;

import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.packets.events.PacketPlayerEvent;
import fr.taeron.shadow.packets.events.PacketPlayerType;
import org.bukkit.event.EventHandler;

public class HeadSnap
extends Check {
    public HeadSnap() {
        this.setName("HeadSnap (Derp/Retard Hacks)");
        this.setAutoban(false);
        this.setType(CheatType.Combat);
    }

    @EventHandler
    public void Player(PacketPlayerEvent e) {
        if (e.getType() != PacketPlayerType.LOOK) {
            return;
        }
        if (e.getPitch() > 90.1f || e.getPitch() < -90.1f) {
            new fr.taeron.shadow.alerts.ShadowAlert(e.getPlayer(), this);
        }
    }
}

