package fr.taeron.shadow.commands;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShadowBanCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp() && sender instanceof Player) {
            sender.sendMessage((Object)ChatColor.RED + "Tu n'as pas la permission.");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage((Object)ChatColor.RED + "Utilisation: /" + label + " <joueur>");
            return false;
        }
        Player p = Bukkit.getPlayer((String)args[0]);
        if (p == null) {
            sender.sendMessage((Object)ChatColor.RED + args[0] + " n'est pas connect\u00e9.");
            return false;
        }
        APlayer ap = Shadow.getInstance().getPlayerManager().getByName(args[0]);
        ap.banPlayer(CheatType.Combat, "Ban Manuel", false);
        return false;
    }
}

