package fr.taeron.shadow.commands.arguments;

import fr.taeron.core.util.command.CommandArgument;
import fr.taeron.shadow.Shadow;
import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class JudgementDayStartArgument
extends CommandArgument
implements Listener {
	private HashMap<String, Boolean> confirm = new HashMap<String, Boolean>();

    public JudgementDayStartArgument() {
        super("start", "Commencer le Judgement Day");
    }

    public String getUsage(String label) {
        return (Object)ChatColor.YELLOW + "/" + label + " start";
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("\u00a7cUtilisation: /" + label + " start");
            return false;
        }
        if (this.confirm.containsKey(sender.getName())) {
            sender.sendMessage("\u00a7cMerci de confirmer ou d'annuler en \u00e9crivant oui ou non dans le chat.");
            return false;
        }
        new fr.taeron.shadow.judgementday.JudgementDayRunnable();
        return false;
    }

    @EventHandler
    public void confirm(AsyncPlayerChatEvent e) {
        if (!this.confirm.containsKey(e.getPlayer().getName())) {
            return;
        }
        this.confirm.remove(e.getPlayer().getName());
        e.setCancelled(true);
        if (e.getMessage().equalsIgnoreCase("oui")) {
            Bukkit.broadcastMessage((String)(String.valueOf(Shadow.getInstance().getPrefix()) + " \u00a77d\u00e9but du Judgement Day ! \u00a78(\u00a7c" + Shadow.getInstance().getJudgementDayCache().getBannedPlayers().size() + " joueurs vont \u00eatre bannis\u00a78)"));
            new fr.taeron.shadow.judgementday.JudgementDayRunnable();
        }
        if (e.getMessage().equalsIgnoreCase("non")) {
            return;
        }
    }
}

