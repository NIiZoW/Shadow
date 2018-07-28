package fr.taeron.shadow.commands.arguments;

import fr.taeron.core.util.command.CommandArgument;
import fr.taeron.shadow.Shadow;
import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JudgementDayAddArgument
extends CommandArgument {
    public JudgementDayAddArgument() {
        super("add", "Ajouter un joueur (ou un UUID) au prochain JudgementDay");
    }

    public String getUsage(String label) {
        return (Object)ChatColor.YELLOW + "/" + label + " add <joueur|uuid>";
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage((Object)ChatColor.RED + "Utilisation: /" + label + " add <joueur|uuid>");
        }
        if (Shadow.getInstance().getJudgementDayCache().willBeBanned(args[1])) {
            sender.sendMessage((Object)ChatColor.RED + "Ce joueur a d\u00e9j\u00e0 \u00e9t\u00e9 ajout\u00e9 au prochain Judgement Day.");
            return false;
        }
        Shadow.getInstance().getJudgementDayCache().addBannedPlayer(args[1]);
        Command.broadcastCommandMessage((CommandSender)sender, (String)((Object)ChatColor.AQUA + args[1] + " a \u00e9t\u00e9 ajout\u00e9 au prochain Judgement Day"));
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Shadow.getInstance().getJudgementDayCache().getBannedPlayers();
        }
        return Collections.emptyList();
    }
}

