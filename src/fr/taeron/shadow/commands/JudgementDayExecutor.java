package fr.taeron.shadow.commands;

import fr.taeron.core.util.command.ArgumentExecutor;
import fr.taeron.core.util.command.CommandArgument;
import fr.taeron.shadow.commands.arguments.JudgementDayAddArgument;
import fr.taeron.shadow.commands.arguments.JudgementDayRemoveArgument;
import fr.taeron.shadow.commands.arguments.JudgementDayStartArgument;

public class JudgementDayExecutor
extends ArgumentExecutor {
    public JudgementDayExecutor() {
        super("judgementday");
        this.addArgument((CommandArgument)new JudgementDayRemoveArgument());
        this.addArgument((CommandArgument)new JudgementDayAddArgument());
        this.addArgument((CommandArgument)new JudgementDayStartArgument());
    }
}

