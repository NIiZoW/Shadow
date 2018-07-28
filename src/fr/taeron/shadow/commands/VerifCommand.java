package fr.taeron.shadow.commands;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.player.APlayer;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class VerifCommand
implements CommandExecutor {
    public static HashMap<Player, APlayer> verifiers = new HashMap<Player, APlayer>();
    private String perm;

    public VerifCommand(String perm) {
        this.perm = perm;
    }

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("\u00a7cLa console n'est pas support\u00e9e.");
            return true;
        }
        if (!sender.hasPermission(this.perm)) {
            sender.sendMessage("\u00a7cTu n'as pas la permission.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("\u00a7cUtilisation: /verif <joueur>");
            return true;
        }
        String pseudo = args[0];
        if (Bukkit.getPlayer((String)pseudo) != null) {
            Inventory i = Bukkit.createInventory((InventoryHolder)null, (int)54, (String)("\u00a7cVerif > " + pseudo));
            verifiers.put((Player)sender, Shadow.getInstance().getPlayerManager().getByPlayer(Bukkit.getPlayer((String)pseudo)));
            ((Player)sender).openInventory(i);
        } else {
            ((Player)sender).sendMessage("\u00a7cJoueur non connect\u00e9.");
        }
        return true;
    }
}

