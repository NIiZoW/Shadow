package fr.taeron.shadow.checks.combat;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.utils.UtilDirection;
import fr.taeron.shadow.utils.UtilMath;
import fr.taeron.shadow.utils.UtilPlayer;
import fr.taeron.shadow.utils.UtilTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ReachA
extends Check
implements Listener {
    private Map<UUID, ReachEntry> ReachTicks = new HashMap<UUID, ReachEntry>();

    public ReachA() {
        this.setName("Reach (Check A)");
        this.setType(CheatType.Combat);
        this.setMaximumViolation(6);
        this.setViolationsToJday(4);
        this.setMaxPing(250);
        this.setJudgementDay(true);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!e.getCause().equals((Object)EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player damager = (Player)e.getDamager();
        Player player = (Player)e.getEntity();
        if (UtilDirection.yawToFace(damager.getLocation().getYaw()) == BlockFace.NORTH && UtilDirection.yawToFace(player.getLocation().getYaw()) != BlockFace.SOUTH) {
            return;
        }
        if (UtilDirection.yawToFace(damager.getLocation().getYaw()) == BlockFace.SOUTH && UtilDirection.yawToFace(player.getLocation().getYaw()) != BlockFace.NORTH) {
            return;
        }
        if (UtilDirection.yawToFace(damager.getLocation().getYaw()) == BlockFace.EAST && UtilDirection.yawToFace(player.getLocation().getYaw()) != BlockFace.WEST) {
            return;
        }
        if (UtilDirection.yawToFace(damager.getLocation().getYaw()) == BlockFace.WEST && UtilDirection.yawToFace(player.getLocation().getYaw()) != BlockFace.EAST) {
            return;
        }
        if (damager.getAllowFlight()) {
            return;
        }
        if (player.getAllowFlight()) {
            return;
        }
        long Time = System.currentTimeMillis();
        List<Double> Reachs = new ArrayList<Double>();
        if (this.ReachTicks.containsKey(damager.getUniqueId())) {
            Time = this.ReachTicks.get(damager.getUniqueId()).getLastTime();
            Reachs = new ArrayList<Double>(this.ReachTicks.get(damager.getUniqueId()).getReachs());
        }
        double MaxReach = 3.9;
        if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
            int Level2 = 0;
            for (PotionEffect Effect : damager.getActivePotionEffects()) {
                if (!Effect.getType().equals((Object)PotionEffectType.SPEED)) continue;
                Level2 = Effect.getAmplifier();
                break;
            }
            switch (Level2) {
                case 0: {
                    MaxReach = 3.9;
                    break;
                }
                case 1: {
                    MaxReach = 4.0;
                    break;
                }
                case 2: {
                    MaxReach = 4.1;
                    break;
                }
                case 3: {
                    MaxReach = 4.1;
                    break;
                }
                case 4: {
                    MaxReach = 4.2;
                    break;
                }
                case 5: {
                    MaxReach = 4.2;
                    break;
                }
                default: {
                    return;
                }
            }
        }
        if (player.getVelocity().length() > 0.08 || Shadow.getInstance().LastVelocity.containsKey(player.getUniqueId())) {
            return;
        }
        double Reach = UtilPlayer.getEyeLocation(damager).distance(player.getLocation());
        CraftPlayer cpd = (CraftPlayer)damager;
        int Ping = cpd.getHandle().ping;
        if (Ping >= 100 && Ping < 150) {
            MaxReach += 0.1;
        } else if (Ping >= 150 && Ping < 250) {
            MaxReach += 0.2;
        } else if (Ping >= 250 && Ping < 300) {
            MaxReach += 0.4;
        } else if (Ping >= 300 && Ping < 350) {
            MaxReach += 0.6;
        } else if (Ping >= 350 && Ping < 400) {
            MaxReach += 0.8;
        } else if (Ping > 400) {
            return;
        }
        if (Reach > (MaxReach += Math.abs((UtilPlayer.getEyeLocation(player).getY() - UtilPlayer.getEyeLocation(damager).getY()) / 2.0))) {
            Reachs.add(Double.valueOf(Reach));
            Time = System.currentTimeMillis();
        }
        if (this.ReachTicks.containsKey(damager.getUniqueId()) && UtilTime.elapsed(Time, 25000)) {
            Reachs.clear();
            Time = System.currentTimeMillis();
        }
        if (Reachs.size() > 3) {
            Double AverageReach = Double.valueOf(UtilMath.averageDouble(Reachs));
            Double A = Double.valueOf(6.0D - MaxReach);
            if (A < 0.0) {
                A = 0.0;
            }
            if ((Double.valueOf(AverageReach - MaxReach)) < 0.0) {
            }
            Reachs.clear();
            new fr.taeron.shadow.alerts.ShadowAlert(damager, this);
        }
        this.ReachTicks.put(damager.getUniqueId(), new ReachEntry(Long.valueOf(Time), Reachs));
    }

    public class ReachEntry {
        public Long LastTime;
        public List<Double> Reachs;

        public ReachEntry(Long LastTime, List<Double> Reachs) {
            this.Reachs = new ArrayList<Double>();
            this.LastTime = LastTime;
            this.Reachs = Reachs;
        }

        public Long getLastTime() {
            return this.LastTime;
        }

        public List<Double> getReachs() {
            return this.Reachs;
        }

        public void setLastTime(Long LastTime) {
            this.LastTime = LastTime;
        }

        public void setReachs(List<Double> Reachs) {
            this.Reachs = Reachs;
        }

        public void addReach(Double Reach) {
            this.Reachs.add(Reach);
        }
    }

}

