/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package fr.taeron.shadow.judgementday;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;

public class JudgementDayCache {
    private List<String> playerList = new ArrayList<String>();

    public JudgementDayCache() {
        this.loadCache();
    }

    public void addBannedPlayer(String s) {
        if (!this.playerList.contains(s)) {
            this.playerList.add(s);
        }
    }

    public void removeBannedPlayer(String s) {
        if (this.playerList.contains(s)) {
            this.playerList.remove(s);
        }
    }

    public List<String> getBannedPlayers() {
        return this.playerList;
    }

    public boolean willBeBanned(String s) {
        return this.playerList.contains(s);
    }

    public void loadCache() {
        File f = new File("plugins/Shadow/cache/JudgementDay.yml");
        if (f.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)f);
            this.playerList = config.getStringList("list");
        }
    }

    public void saveCache() {
        try {
            File f = new File("plugins/Shadow/cache/JudgementDay.yml");
            YamlConfiguration config = new YamlConfiguration();
            config.set("list", this.playerList);
            config.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

