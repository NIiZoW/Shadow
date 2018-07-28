
package fr.taeron.shadow.player;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.AutoBanTimer;
import fr.taeron.shadow.utils.SmallAverageCollector;
import fr.taeron.shadow.utils.UtilAngle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class APlayer
implements Listener {
    public int clicks;
    public int clicks2;
    public int clicks3;
    public int clicks4;
    public int clicks5;
    public int clicks6;
    public int nombreAlertesAutoClick;
    public int maxClicks;
    public long lastBlockInteraction;
    public long lastAlert;
    public long Connexion;
    public String pseudo;
    private boolean alerts;
    private String name;
    private String UUID;
    private String IP;
    private HashMap<CheatType, Integer> violations = new HashMap<CheatType, Integer>();
    private ArrayList<Player> online = new ArrayList<Player>();
    public Entity target;
    public Entity lastTarget;
    public double locX;
    public double locZ;
    public double lastLocX;
    public double lastLocZ;
    public float lastYaw;
    public float yaw;
    public float lastYawDelta;
    public int hmrPacketHits;
    public int hmrPacketMisses;
    public int hmrRatioConsistancyVL;
    public int hmrPacketVL;
    public int hmrLookVL;
    public boolean hmrInBattle;
    public long hmrLastHitTime;
    public int hmrLookHits;
    public int hmrLookMisses;
    public float hmrLastYawDelta;
    public double hmrYawTotal;
    public int hmrYawCumulator;
    public float hmrLastYaw;
    public float hmrCurrentYaw;
    public float hmrYawDelta;
    public int hmrYawPackets;
    public double hmrSpeedCumulator;
    public double lastRatio;
    public Location lastLocation;
    public Location location;
    public long lastEntityUsePacket;
    public int pingKey = 0;
    public long pingNanoSent = 0;
    public int ping = 0;
    public long ACConsistentLastHit;
    public long ACConsistentCurrentSwingTime = 0;
    public long ACConsitentLastSwingTime = 0;
    public long ACConsistentCurrentSwingDelay = 0;
    public SmallAverageCollector ACConsistentCollector;
    public int ACConsistentVl = 0;
    public long ACConsitentLastSwingDelay = 0;
    public long ACConsitentCurrentSwingDelay = 0;
    public double ACConsistentLastAverage = 0.0;
    public float kaTypeCTotalYawDelta;
    public long kaTypeCLastHitTime;
    public double kaTypeCVL;
    public int kaTypeCStandingTicks;
    public int lastEntityInteractPacket = 0;
    public long lastNpcCheck = 0;

    public APlayer(Player p) throws IOException {
        this.alerts = true;
        this.UUID = String.valueOf(p.getUniqueId());
        this.name = p.getName();
        this.IP = String.valueOf(p.getAddress().getAddress().getHostAddress());
        this.online.add(p);
        this.clicks = 0;
        this.clicks2 = 0;
        this.clicks3 = 0;
        this.clicks4 = 0;
        this.clicks5 = 0;
        this.clicks6 = 0;
        this.nombreAlertesAutoClick = 0;
        this.maxClicks = 0;
        this.lastBlockInteraction = 0;
        this.lastAlert = 0;
        this.Connexion = System.currentTimeMillis();
        this.hmrLastHitTime = System.currentTimeMillis();
        this.initDataFile();
        this.runViolationsDecreament();
        this.lastLocation = p.getLocation();
        this.location = p.getLocation();
        this.lastEntityUsePacket = System.currentTimeMillis();
        this.ACConsistentLastHit = System.currentTimeMillis();
    }

    public APlayer() {
    }

    public void banPlayer(CheatType type, String name, boolean withTimer) {
        this.violations.clear();
        if (withTimer) {
            AutoBanTimer.startTimer(type, name, this.getBukkitPlayer());
        } else {
            new BukkitRunnable(){

                public void run() {
                    if (Bukkit.getPlayer((String)APlayer.this.getName()) != null) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("jd remove " + APlayer.this.UUID));
                        Bukkit.getPlayer((String)APlayer.this.getName()).kickPlayer(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + " \u00a7fTu t'es pris deux doigts !");
                    }
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("ban " + APlayer.this.getName() + " -s 120d Cheating"));
                    Bukkit.broadcastMessage((String)(String.valueOf(Shadow.getInstance().getPrefix()) + " \u00a7c" + APlayer.this.getName() + " \u00a77a \u00e9t\u00e9 banni du serveur."));
                }
            }.runTask((Plugin)Shadow.getInstance());
        }
    }

    @SuppressWarnings("deprecation")
	public void runViolationsDecreament() {
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask((Plugin)Shadow.getInstance(), new Runnable(){

            @Override
            public void run() {
                if (APlayer.this.getViolations(CheatType.Combat) > 0) {
                    APlayer.this.violations.put(CheatType.Combat, APlayer.this.getViolations(CheatType.Combat) - 1);
                }
                if (APlayer.this.getViolations(CheatType.Mouvement) > 0) {
                    APlayer.this.violations.put(CheatType.Mouvement, APlayer.this.getViolations(CheatType.Mouvement) - 1);
                }
                if (APlayer.this.getViolations(CheatType.Misc) > 0) {
                    APlayer.this.violations.put(CheatType.Misc, APlayer.this.getViolations(CheatType.Misc) - 1);
                }
                if (APlayer.this.getViolations(CheatType.Clicks) > 0) {
                    APlayer.this.violations.put(CheatType.Clicks, APlayer.this.getViolations(CheatType.Clicks) - 1);
                }
            }
        }, 0, 900);
    }

    public void toggleAlerts() {
        this.alerts = !this.alerts;
    }

    public ArrayList<Player> getOnlinePlayers() {
        return this.online;
    }

    public boolean hasAlertsEnabled() {
        return this.alerts;
    }

    public String getName() {
        return this.name;
    }

    public String getIP() {
        return this.IP;
    }

    public String getUUID() {
        return this.UUID;
    }

    public boolean isStaff() {
        if (Bukkit.getPlayer((String)this.name) != null && Bukkit.getPlayer((String)this.getName()).hasPermission("heaven.staff")) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	public HashMap<CheatType, Integer> getViolations() {
        return (HashMap<CheatType, Integer>)this.violations.clone();
    }

    public int getViolations(CheatType c) {
        if (this.violations.get((Object)c) == null) {
            return 0;
        }
        return this.violations.get((Object)c);
    }

    public int getPing() {
        CraftPlayer ap = (CraftPlayer)Bukkit.getPlayer((String)this.getName());
        if (ap != null) {
            return ap.getHandle().ping;
        }
        return 0;
    }

    public void increaseViolations(Check check, int amount) {
        this.violations.put(check.getCheckType(), this.getViolations(check.getCheckType()) + amount);
    }

    public void initDataFile() throws IOException {
        File f = new File("plugins/Shadow/data/" + this.getUUID() + ".yml");
        if (!f.exists()) {
            Bukkit.getLogger().info("[OBEY] Cr\u00e9ation du fichier de data de " + this.getName() + ".");
            YamlConfiguration config = new YamlConfiguration();
            config.createSection("Pseudos");
            config.createSection("IPs");
            ArrayList<String> pseudos = new ArrayList<String>();
            pseudos.add(this.getName());
            config.set("Pseudos", pseudos);
            ArrayList<String> ips = new ArrayList<String>();
            ips.add(this.getIP());
            config.set("IPs", ips);
            config.save(f);
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)f);
        if (!config.getStringList("IPs").contains(this.getIP())) {
            List<String> ips2 = config.getStringList("IPs");
            ips2.add(this.getIP());
            config.set("IPs", (Object)ips2);
        }
        if (!config.getStringList("Pseudos").contains(this.getName())) {
            List<String> pseudos = config.getStringList("Pseudos");
            pseudos.add(this.getName());
            config.set("Pseudos", (Object)pseudos);
        }
    }

    public float getDeltaYaw() {
        return Math.abs(Math.abs(UtilAngle.wrapAngleTo180_float(this.getBukkitPlayer().getLocation().getYaw())) - Math.abs(UtilAngle.wrapAngleTo180_float(this.getLastYaw())));
    }

    public double getLastTargetHorizontalDistance() {
        if (!(this.getTarget() instanceof Player)) {
            return 0.28;
        }
        Player p = (Player)this.getTarget();
        if (!p.isOnline()) {
            return 0.0;
        }
        APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer((Player)this.target);
        return ap.getLastHorizontalDistance();
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public Location getLocation() {
        return this.location;
    }

    public float getLastYaw() {
        return this.lastYaw;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer((String)this.getName());
    }

    public CraftPlayer getCraftBukkitPlayer() {
        return (CraftPlayer)Bukkit.getPlayer((String)this.getName());
    }

    public Entity getLastTarget() {
        return this.lastTarget;
    }

    public Entity getTarget() {
        return this.target;
    }

    public double getLastHorizontalDistance() {
        if (this.lastLocX == 0.0 || this.lastLocZ == 0.0 || this.locX == 0.0 || this.locZ == 0.0) {
            return 0.0;
        }
        return UtilAngle.getDistance(this.locX, this.locZ, this.lastLocX, this.lastLocZ);
    }

}

