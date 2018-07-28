package fr.taeron.shadow.commands.arguments;

import fr.taeron.core.util.command.CommandArgument;
import fr.taeron.shadow.Shadow;
import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JudgementDayRemoveArgument
extends CommandArgument {
    public JudgementDayRemoveArgument() {
        super("remove", "Supprimer un joueur (ou un UUID) du prochain JudgementDay");
    }

    public String getUsage(String label) {
        return (Object)ChatColor.YELLOW + "/" + label + " remove <joueur|uuid>";
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage((Object)ChatColor.RED + "Utilisation: /" + label + " remove <joueur|uuid>");
        }
        if (!Shadow.getInstance().getJudgementDayCache().willBeBanned(args[1])) {
            sender.sendMessage((Object)ChatColor.RED + "Ce joueur n'a pas \u00e9t\u00e9 ajout\u00e9 au prochain Judgement Day.");
            return false;
        }
        Shadow.getInstance().getJudgementDayCache().removeBannedPlayer(args[1]);
        Command.broadcastCommandMessage((CommandSender)sender, (String)((Object)ChatColor.AQUA + args[1] + " a \u00e9t\u00e9 retir\u00e9 du prochain Judgement Day"));
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            return Shadow.getInstance().getJudgementDayCache().getBannedPlayers();
        }
        return Collections.emptyList();
    }
}

