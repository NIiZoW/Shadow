
package fr.taeron.shadow.commands;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.alerts.ShadowAlert;
import fr.taeron.shadow.player.APlayer;
import java.io.FileNotFoundException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DumpCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Merci d'utiliser cette commande en jeu.");
            return false;
        }
        APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer((Player)sender);
        if (!ap.isStaff()) {
            sender.sendMessage("\u00a7cTu n'as pas la permission.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("\u00a7cUtilisation: /" + label + " <joueur>");
            return false;
        }
        try {
            ShadowAlert.sendDump((Player)sender, args[0]);
        }
        catch (FileNotFoundException e) {
            sender.sendMessage("\u00a7cAucun log trouv\u00e9 pour " + args[0]);
            e.printStackTrace();
        }
        return false;
    }
}

