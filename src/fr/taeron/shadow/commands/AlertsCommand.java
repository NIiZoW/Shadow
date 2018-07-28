
package fr.taeron.shadow.commands;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.player.APlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage((Object)ChatColor.RED + "La console n'est pas support\u00e9e.");
            return false;
        }
        Player p = (Player)sender;
        APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(p);
        if (!ap.isStaff()) {
            p.sendMessage("\u00a7cTu n'as pas la permission.");
            return false;
        }
        ap.toggleAlerts();
        if (ap.hasAlertsEnabled()) {
            p.sendMessage(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + "\u00a77Alertes \u00a7aactiv\u00e9es");
        } else {
            p.sendMessage(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + "\u00a77Alertes \u00a7cd\u00e9sactiv\u00e9es");
        }
        return false;
    }
}

