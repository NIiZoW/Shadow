package fr.taeron.shadow.checks.misc;

import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

public class TabComplete
extends Check {
    public Map<UUID, Long> TabComplete;

    public TabComplete() {
        this.setName("TabComplete (.friend add, ou autre, joueur \u00e0 surveiller)");
        this.setType(CheatType.Misc);
        this.TabComplete = new HashMap<UUID, Long>();
        this.setAutoban(false);
    }

    @EventHandler
    public void TabCompleteEvent(PlayerChatTabCompleteEvent e) {
        String[] Args = e.getChatMessage().split(" ");
        Player Player2 = e.getPlayer();
        if (Args[0].startsWith(".") && Args[0].substring(1, 2).equalsIgnoreCase("/")) {
            return;
        }
        if (Args.length > 1 && (Args[0].startsWith(".") || Args[0].startsWith("-") || Args[0].startsWith("#") || Args[0].startsWith("*"))) {
            if (this.TabComplete.containsKey(Player2.getUniqueId()) && System.currentTimeMillis() < this.TabComplete.get(Player2.getUniqueId()) + 1000) {
                return;
            }
            new fr.taeron.shadow.alerts.ShadowAlert(Player2, this);
            this.TabComplete.put(Player2.getUniqueId(), System.currentTimeMillis());
        }
    }
}

