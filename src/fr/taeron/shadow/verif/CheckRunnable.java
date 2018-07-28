package fr.taeron.shadow.verif;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckRunnable
extends BukkitRunnable {
    private int timeBetweenAlerts;
    private int maxCps;

    public CheckRunnable(int secondes, int maxCps, String Perm) {
        this.timeBetweenAlerts = secondes;
        this.maxCps = maxCps;
    }

    public void run() {
        @SuppressWarnings("deprecation")
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
        int length = onlinePlayers.length;
        int i = 0;
        while (i < length) {
            Player p = onlinePlayers[i];
            APlayer wp = Shadow.getInstance().getPlayerManager().getByPlayer(p);
            if (wp == null) {
                return;
            }
            if (wp.clicks >= this.maxCps && wp.lastAlert + (long)this.timeBetweenAlerts * 1000 < System.currentTimeMillis()) {
                wp.lastAlert = System.currentTimeMillis();
                APlayer aPlayer = wp;
                ++aPlayer.nombreAlertesAutoClick;
                new fr.taeron.shadow.verif.AutoAlert(p, wp.clicks);
            }
            wp.clicks6 = wp.clicks5;
            wp.clicks5 = wp.clicks4;
            wp.clicks4 = wp.clicks3;
            wp.clicks3 = wp.clicks2;
            wp.clicks2 = wp.clicks;
            wp.clicks = 0;
            ++i;
        }
    }
}

