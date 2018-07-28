package fr.taeron.shadow;

import fr.taeron.shadow.basiclisteners.BasicListener;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.checks.CheckManager;
import fr.taeron.shadow.checks.combat.WallHit;
import fr.taeron.shadow.commands.AlertsCommand;
import fr.taeron.shadow.commands.CancelBanCommand;
import fr.taeron.shadow.commands.CheckCommand;
import fr.taeron.shadow.commands.DumpCommand;
import fr.taeron.shadow.commands.JudgementDayExecutor;
import fr.taeron.shadow.commands.ShadowBanCommand;
import fr.taeron.shadow.commands.ShadowCommand;
import fr.taeron.shadow.commands.ToggleShadowCommand;
import fr.taeron.shadow.commands.VerifCommand;
import fr.taeron.shadow.commands.arguments.JudgementDayStartArgument;
import fr.taeron.shadow.judgementday.JudgementDayCache;
import fr.taeron.shadow.packets.PacketCore;
import fr.taeron.shadow.player.PlayerManager;
import fr.taeron.shadow.utils.LagUtils;
import fr.taeron.shadow.verif.AutoclickListener;
import fr.taeron.shadow.verif.CheckRunnable;
import fr.taeron.shadow.verif.VerifRunnable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Shadow
extends JavaPlugin {
    private PlayerManager pmanager;
    private static Shadow instance;
    public Map<UUID, Map.Entry<Long, Vector>> LastVelocity = new HashMap<UUID, Map.Entry<Long, Vector>>();
    public PacketCore packet;
    private CheckManager checkManager;
    public static boolean enabled;
    private JudgementDayCache jdCache;

    @SuppressWarnings("deprecation")
	public void onEnable() {
        Bukkit.getLogger().info("[OBEY] Chargement de l'instance");
        Shadow.setInstance(this);
        Bukkit.getLogger().info("[OBEY] Chargement des commandes");
        this.registerCommands();
        Bukkit.getLogger().info("[OBEY] Chargement des listeners");
        this.registerListeners();
        Bukkit.getLogger().info("[OBEY] Chargement du PlayerManager");
        this.loadPlayerManager(new PlayerManager());
        Bukkit.getLogger().info("[OBEY] Lancement de l'update automatique");
        this.register();
        enabled = true;
        if (Bukkit.getOnlinePlayers().length > 0) {
            Player[] onlinePlayers = Bukkit.getOnlinePlayers();
            int length = onlinePlayers.length;
            int i = 0;
            while (i < length) {
                Player p = onlinePlayers[i];
                try {
                    Shadow.getInstance().getPlayerManager().registerNewPlayer(p);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                ++i;
            }
        }
    }

    public void onDisable() {
        this.jdCache.saveCache();
    }

    public static void setInstance(Shadow instance) {
        Shadow.instance = instance;
    }

    public static Shadow getInstance() {
        return instance;
    }

    public String getPrefix() {
        return "\u00a78[\u00a74\u00a7lOBEY\u00a78] ";
    }

    public void register() {
        new VerifRunnable().runTaskTimerAsynchronously((Plugin)this, 0, 1);
        new CheckRunnable(1, 18, "heaven.staff").runTaskTimerAsynchronously((Plugin)this, 0, 20);
        this.packet = new PacketCore(this);
        this.checkManager = new CheckManager();
        this.jdCache = new JudgementDayCache();
        for (Check c : this.checkManager.getChecks()) {
            Bukkit.getPluginManager().registerEvents((Listener)c, (Plugin)Shadow.getInstance());
        }
    }

    public void registerListeners() {
        PluginManager p = Bukkit.getPluginManager();
        p.registerEvents((Listener)new BasicListener(), (Plugin)this);
        p.registerEvents((Listener)new CheckManager(), (Plugin)this);
        p.registerEvents((Listener)new AutoclickListener(), (Plugin)this);
        p.registerEvents((Listener)new JudgementDayStartArgument(), (Plugin)this);
        WallHit.loadBlockList();
    }

    public void registerCommands() {
        ShadowCommand scommand = new ShadowCommand();
        this.getCommand("shadow").setExecutor((CommandExecutor)scommand);
        AlertsCommand acommand = new AlertsCommand();
        this.getCommand("alerts").setExecutor((CommandExecutor)acommand);
        this.getCommand("verif").setExecutor((CommandExecutor)new VerifCommand("heaven.staff"));
        this.getCommand("logshistory").setExecutor((CommandExecutor)new DumpCommand());
        this.getCommand("shadowban").setExecutor((CommandExecutor)new ShadowBanCommand());
        this.getCommand("check").setExecutor((CommandExecutor)new CheckCommand());
        this.getCommand("toggleshadow").setExecutor((CommandExecutor)new ToggleShadowCommand());
        this.getCommand("cancelautoban").setExecutor((CommandExecutor)new CancelBanCommand());
        this.getCommand("judgementday").setExecutor((CommandExecutor)new JudgementDayExecutor());
    }

    public boolean isShadowEnabled() {
        if (enabled && LagUtils.getTPS() > 19.0 && LagUtils.getTPS() < 20.2) {
            return true;
        }
        return false;
    }

    public void loadPlayerManager(PlayerManager pManager) {
        this.pmanager = pManager;
    }

    public PlayerManager getPlayerManager() {
        return this.pmanager;
    }

    public Map<UUID, Map.Entry<Long, Vector>> getLastVelocity() {
        return new HashMap<UUID, Map.Entry<Long, Vector>>(this.LastVelocity);
    }

    public CheckManager getCheckManager() {
        return this.checkManager;
    }

    public JudgementDayCache getJudgementDayCache() {
        return this.jdCache;
    }
}

