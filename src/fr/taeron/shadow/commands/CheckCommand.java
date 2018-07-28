package fr.taeron.shadow.commands;

import fr.taeron.core.util.command.ArgumentExecutor;
import fr.taeron.core.util.command.CommandArgument;
import fr.taeron.shadow.commands.arguments.CheckDisableArgument;
import fr.taeron.shadow.commands.arguments.CheckEnableArgument;
import fr.taeron.shadow.commands.arguments.CheckGuiArgument;

public class CheckCommand
extends ArgumentExecutor {
    public CheckCommand() {
        super("check");
        this.addArgument((CommandArgument)new CheckDisableArgument());
        this.addArgument((CommandArgument)new CheckEnableArgument());
        this.addArgument((CommandArgument)new CheckGuiArgument());
    }
}

