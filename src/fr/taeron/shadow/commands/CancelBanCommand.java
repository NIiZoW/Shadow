package fr.taeron.shadow.commands;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.player.APlayer;
import fr.taeron.shadow.utils.AutoBanTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CancelBanCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage((Object)ChatColor.RED + "Utilisation: /" + label + " <joueur>");
            return false;
        }
        Player t = Bukkit.getPlayer((String)args[0]);
        if (t == null) {
            sender.sendMessage((Object)ChatColor.RED + "Ce joueur n'est pas connect\u00e9.");
        }
    APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer((Player)sender);
    if (!ap.isStaff()) {
    	sender.sendMessage("\u00a7cTu n'as pas la permission.");
        return false;
    }
        if (AutoBanTimer.nameList.contains(t.getName())) {
            AutoBanTimer.nameList.remove(t.getName());
            Command.broadcastCommandMessage((CommandSender)sender, (String)((Object)ChatColor.GREEN + "Tu as annul\u00e9 l'autoban de " + t.getName()));
            return true;
        }
        sender.sendMessage((Object)ChatColor.RED + t.getName() + " ne va pas \u00eatre banni.");
        return false;
    }
}

