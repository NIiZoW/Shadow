/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  fr.taeron.core.util.command.CommandArgument
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package fr.taeron.shadow.commands.arguments;

import fr.taeron.core.util.command.CommandArgument;
import fr.taeron.shadow.Shadow;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckGuiArgument
extends CommandArgument {
    public CheckGuiArgument() {
        super("gui", "Voir le menu des checks");
    }

    public String getUsage(String label) {
        return (Object)ChatColor.YELLOW + "/" + label + " <gui>";
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (!sender.isOp()) {
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("\u00a7cUtilisation: " + this.getUsage(label));
            return false;
        }
        Player p = (Player)sender;
        Shadow.getInstance().getCheckManager().openCheckManagerGui(p);
        return false;
    }
}

