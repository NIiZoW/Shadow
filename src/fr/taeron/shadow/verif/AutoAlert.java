/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package fr.taeron.shadow.verif;

import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import org.bukkit.entity.Player;

public class AutoAlert
extends Check {
    public AutoAlert(Player p, int clicks) {
        this.setName("AutoClicker (" + clicks + " CPS)");
        this.setType(CheatType.Clicks);
        this.setMaximumViolation(9999);
        this.setMaxPing(250);
        new fr.taeron.shadow.alerts.ShadowAlert(p, this);
    }
}

