package fr.taeron.shadow.judgementday;

import fr.taeron.shadow.Shadow;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class JudgementDayRunnable {
    public JudgementDayRunnable() {
        this.run();
    }

    public void run() {
        new BukkitRunnable(){

            @SuppressWarnings("deprecation")
			public void run() {
                if (Shadow.getInstance().getJudgementDayCache().getBannedPlayers().size() > 0) {
                    if (Bukkit.getOfflinePlayer((String)Shadow.getInstance().getJudgementDayCache().getBannedPlayers().get(0)) != null) {
                        Bukkit.broadcastMessage((String)(String.valueOf(Shadow.getInstance().getPrefix()) + " \u00a7c" + Bukkit.getOfflinePlayer((String)Shadow.getInstance().getJudgementDayCache().getBannedPlayers().get(0)).getName() + " \u00a77a \u00e9t\u00e9 banni du serveur (Apocalypse #1)."));
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("ban " + Shadow.getInstance().getJudgementDayCache().getBannedPlayers().get(0) + " Cheating (Apocalypse #1)"));
                    }
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("jd remove " + Shadow.getInstance().getJudgementDayCache().getBannedPlayers().get(0)));
                } else {
                    Bukkit.broadcastMessage((String)"\u00a7aApocalypse termin\u00e9.");
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously((Plugin)Shadow.getInstance(), 40, 20);
    }

}

