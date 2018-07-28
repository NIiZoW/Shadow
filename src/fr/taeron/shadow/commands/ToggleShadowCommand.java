package fr.taeron.shadow.commands;

import fr.taeron.shadow.Shadow;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleShadowCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        Shadow.enabled = !Shadow.enabled;
        sender.sendMessage("\u00a7a\u00a7lOBEY est d\u00e9sormais " + Shadow.enabled);
        return false;
    }
}

