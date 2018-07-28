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
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.checks.CheckManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckEnableArgument
extends CommandArgument {
    public CheckEnableArgument() {
        super("enable", "Activer un check");
    }

    public String getUsage(String label) {
        return (Object)ChatColor.YELLOW + "/" + label + " disable <check>";
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (!sender.isOp()) {
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage("\u00a7cUtilisation: " + this.getUsage(label));
            return false;
        }
        CheckManager cm = Shadow.getInstance().getCheckManager();
        if (!cm.exists(args[1].replace("_", " "))) {
            sender.sendMessage("\u00a7cLe check '" + args[1].replace("_", " ") + "' n'existe pas.");
            return false;
        }
        Check c = cm.getCheck(args[1].replace("_", " "));
        if (c.isEnabled()) {
            sender.sendMessage("\u00a7cLe check " + c.getCheckName() + " est d\u00e9j\u00e0 activ\u00e9.");
            return false;
        }
        cm.enableCheck(c);
        Command.broadcastCommandMessage((CommandSender)sender, (String)("\u00a7aTu as activ\u00e9 le check " + c.getCheckName()));
        return false;
    }
}

