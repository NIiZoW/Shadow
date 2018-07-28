package fr.taeron.shadow.checks.misc;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.packets.events.PacketPlayerEvent;
import fr.taeron.shadow.player.APlayer;
import fr.taeron.shadow.utils.LagUtils;
import fr.taeron.shadow.utils.UtilTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MorePackets
extends Check
implements Listener {
    private Map<UUID, Map.Entry<Integer, Long>> packetTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
    private Map<UUID, Long> lastPacket = new HashMap<UUID, Long>();
    private List<UUID> blacklist = new ArrayList<UUID>();
	private HashMap<Player, List<Integer>> packets = new HashMap<Player, List<Integer>>();

    public MorePackets() {
        this.setName("Speed (Packets [Timer/Blink])");
        this.setType(CheatType.Misc);
        this.setMaxPing(240);
        this.setMaximumViolation(5);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        this.blacklist.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
        this.blacklist.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerTeleport(PlayerTeleportEvent e) {
        this.blacklist.add(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerRespawn(PlayerRespawnEvent event) {
        this.blacklist.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PacketPlayer(PacketPlayerEvent event) {
        Player player = event.getPlayer();
        if (!Shadow.getInstance().isShadowEnabled() || ((CraftPlayer)player).getHandle().ping > 250) {
            return;
        }
        if (player.getGameMode().equals((Object)GameMode.CREATIVE)) {
            return;
        }
        if (LagUtils.getTPS() > 21.0) {
            return;
        }
        if (player.hasPermission("blink")) {
            return;
        }
        int Count = 0;
        long Time = System.currentTimeMillis();
        if (this.packetTicks.containsKey(player.getUniqueId())) {
            Count = this.packetTicks.get(player.getUniqueId()).getKey();
            Time = this.packetTicks.get(player.getUniqueId()).getValue();
        }
        if (this.lastPacket.containsKey(player.getUniqueId())) {
            long MS = System.currentTimeMillis() - this.lastPacket.get(player.getUniqueId());
            if (MS >= 100) {
                this.blacklist.add(player.getUniqueId());
            } else if (MS > 1 && this.blacklist.contains(player.getUniqueId())) {
                this.blacklist.remove(player.getUniqueId());
            }
        }
        if (!this.blacklist.contains(player.getUniqueId())) {
            ++Count;
            if (this.packetTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 1000)) {
                if (this.packets.containsKey((Object)player)) {
                    List<Integer> list = this.packets.get((Object)player);
                    list.add(Count);
                    this.packets.put(player, list);
                    if (this.packets.get((Object)player).size() > 6) {
                        double moyenne = 0.0;
                        int i = 0;
                        while (i < this.packets.get((Object)player).size()) {
                            moyenne += (double)this.packets.get((Object)player).get(i).intValue();
                            ++i;
                        }
                        moyenne /= (double)this.packets.get((Object)player).size();
                        APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(player);
                        double max = 22.3;
                        if (ap.getPing() > 100 && ap.getPing() < 130) {
                            max += 2.0;
                        } else if (ap.getPing() > 130 && ap.getPing() < 160) {
                            max += 4.0;
                        } else if (ap.getPing() > 160) {
                            this.packets.remove((Object)player);
                            return;
                        }
                        if (moyenne > max) {
                            new fr.taeron.shadow.alerts.ShadowAlert(player, this);
                        }
                        this.packets.remove((Object)player);
                    }
                } else {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(Count);
                    this.packets.put(player, list);
                }
                Count = 0;
                Time = UtilTime.nowlong();
            }
        } else {
            Count = 0;
        }
        this.packetTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
        this.lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
    }
}

