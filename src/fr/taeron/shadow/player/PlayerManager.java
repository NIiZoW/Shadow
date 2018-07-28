package fr.taeron.shadow.player;

import fr.taeron.shadow.player.APlayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager
extends HashMap<UUID, APlayer> {
    private static final long serialVersionUID = 1;
    private ArrayList<Player> online = new ArrayList<Player>();

    public APlayer getByName(String name) {
        Player p = Bukkit.getPlayer((String)name);
        if (p == null) {
            return null;
        }
        return (APlayer)this.get(p.getUniqueId());
    }

    public ArrayList<Player> getOnlinePlayers() {
        return this.online;
    }

    public APlayer getByPlayer(Player p) {
        return (APlayer)this.get(p.getUniqueId());
    }

    public APlayer getByUUID(UUID uuid) {
        Player p = Bukkit.getPlayer((UUID)uuid);
        if (p == null) {
            return null;
        }
        return (APlayer)this.get(uuid);
    }

    public APlayer removeByPlayer(Player p) {
        this.online.remove((Object)p);
        return (APlayer)this.remove(p.getUniqueId());
    }

    public void registerNewPlayer(Player p) throws IOException {
        this.online.add(p);
        this.put(p.getUniqueId(), new APlayer(p));
    }

    public void registerNewPlayer(APlayer p) {
        this.online.add(Bukkit.getPlayer((String)p.getName()));
        this.put(p.getBukkitPlayer().getUniqueId(), p);
    }
}

