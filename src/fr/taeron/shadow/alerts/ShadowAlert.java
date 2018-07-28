package fr.taeron.shadow.alerts;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.player.APlayer;
import fr.taeron.shadow.utils.JsonMessage;
import fr.taeron.shadow.utils.LagUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ShadowAlert {
    private Shadow shadow;

    public ShadowAlert(Player p, Check check) {
        int violations;
        if (!Shadow.getInstance().isShadowEnabled()) {
            System.out.println("Impossible d'envoyer l'alerte: OBEY d\u00e9sactiv\u00e9");
            return;
        }
        APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
        if (ap == null) {
            try {
                Shadow.getInstance().getPlayerManager().registerNewPlayer(p);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        CheatType type = check.getCheckType();
        String name = check.getCheckName();
        if (ap.getPing() < check.getMaxPing() && check.isAutoBan()) {
            ap.increaseViolations(check, 1);
        }
        if ((violations = ap.getViolations(check.getCheckType())) >= check.getViolationsToAlert() && !check.isInstantBan()) {
            this.alertStaff(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + "\u00a7c" + p.getName() + " \u00a77a eu un log \u00a77(" + (Object)((Object)type) + ")" + " \u00a7c" + name + " \u00a77[VL: \u00a7c" + violations + " \u00a77Ping: " + ap.getPing() + " \u00a77TPS: \u00a77" + LagUtils.getTPS() + "\u00a77]", p);
        } else if (check.isInstantBan() && ap.getPing() < check.getMaxPing()) {
            ap.banPlayer(type, name, check.hasBanTimer());
        }
        if (violations >= check.getMaximimViolations() && ap.getPing() < check.getMaxPing() && check.isAutoBan()) {
            ap.banPlayer(type, name, check.hasBanTimer());
        }
        if (check.isJudgementDay() && violations == check.getViolationsToJday() && ap.getPing() < 150 && !Shadow.getInstance().getJudgementDayCache().willBeBanned(ap.getUUID())) {
            Shadow.getInstance().getJudgementDayCache().addBannedPlayer(ap.getUUID());
        }
        if (ap.getPing() >= check.getMaxPing()) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int mYear = calendar.get(1);
        int mMonth = calendar.get(2) + 1;
        int mDay = calendar.get(5);
        int mHour = calendar.get(11);
        int mMin = calendar.get(12);
        int mSec = calendar.get(13);
        this.dump(p, String.valueOf(String.valueOf(mDay)) + "/" + mMonth + "/" + mYear + "-" + mHour + "/" + mMin + "/" + mSec, String.valueOf(String.valueOf(p.getName())) + " > " + check.getCheckName() + " [" + violations + "] Ping: " + ap.getPing());
    }

    public void alertStaff(String s, Player target) {
        Bukkit.getLogger().warning(s.replace("\u00a77", "").replace("\u00a7c", "").replace("\u00a73", "").replace(Shadow.getInstance().getPrefix(), ""));
        for (Player p : Shadow.getInstance().getPlayerManager().getOnlinePlayers()) {
            APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
            if (ap == null || !ap.isStaff() || !ap.hasAlertsEnabled()) continue;
            JsonMessage.sendCommandMsg(p, s, "\u00a77Clique pour desactiver les alers \u00a7c" + target.getName(), "alerts");
        }
    }

    public void dump(Player p, String heure, String s) {
        File f = new File("plugins/Shadow/dump/" + p.getName().toLowerCase() + ".shadowlog");
        if (!f.exists()) {
            try {
                YamlConfiguration config = new YamlConfiguration();
                config.set(heure.replace(" ", ""), (Object)s);
                config.save(f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                YamlConfiguration config = YamlConfiguration.loadConfiguration((File)f);
                config.set(heure.replace(" ", ""), (Object)s);
                config.save(f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendDump(Player p, String s) throws FileNotFoundException {
        File dir = new File("plugins/Shadow/dump/" + s.toLowerCase() + ".shadowlog");
        if (dir.exists()) {
            FileInputStream ips = new FileInputStream(dir);
            InputStreamReader ipsr = new InputStreamReader(ips);
            try {
                BufferedReader br;
                br = new BufferedReader(ipsr);
                try {
                    String ligne;
                    Integer i = 0;
                    p.sendMessage(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + "\u00a77Historique des logs pour \u00a7c" + s + "\u00a77:");
                    while ((ligne = br.readLine()) != null) {
                        p.sendMessage(ligne);
                        i = i + 1;
                    }
                }
                finally {
                    if (br != null) {
                        br.close();
                    }
                }
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException | NumberFormatException br) {}
        } else {
            p.sendMessage("\u00a7cAucun log trouv\u00e9 pour " + s + ".");
        }
    }

    public Shadow getShadow() {
        return this.shadow;
    }

    public void sendReachMessageToStaRiToRe(final String name, final int before) {
        for (final Player p : Shadow.getInstance().getPlayerManager().getOnlinePlayers()) {
            if (!p.getName().equalsIgnoreCase("StaRiToRe")) continue;
            new BukkitRunnable(){

                public void run() {
                    int dif = Shadow.getInstance().getPlayerManager().getByName(name).getPing() - before;
                    p.sendMessage("Changement de ping 3 secondes apr\u00e8s le log reach de " + name + ": " + before + " -> " + Shadow.getInstance().getPlayerManager().getByName(name).getPing() + " [" + dif + "]");
                }
            }.runTaskLater((Plugin)Shadow.getInstance(), 60);
        }
    }

}

