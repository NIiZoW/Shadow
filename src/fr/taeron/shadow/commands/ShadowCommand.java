package fr.taeron.shadow.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShadowCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
        sender.sendMessage("En fait elle sert a rien cette commande c'est juste que je voulais faire une commande shadow avec des arguments apr\u00e8s puis en fait j'me suis dit que c'\u00e9tait mieux de faire directement les commandes s\u00e9par\u00e9es une par une genre /alerts et tout donc la le /shadow \u00e7a sert a rien mdr. Sinon \u00e7a va?");
        return false;
    }
}

