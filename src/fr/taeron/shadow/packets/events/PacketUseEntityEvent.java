/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.wrappers.EnumWrappers
 *  com.comphenix.protocol.wrappers.EnumWrappers$EntityUseAction
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package fr.taeron.shadow.packets.events;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketUseEntityEvent
extends Event
implements Cancellable {
    public EnumWrappers.EntityUseAction Action;
    public Player Attacker;
    public Entity Attacked;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public PacketUseEntityEvent(EnumWrappers.EntityUseAction Action2, Player Attacker, Entity Attacked) {
        this.Action = Action2;
        this.Attacker = Attacker;
        this.Attacked = Attacked;
    }

    public EnumWrappers.EntityUseAction getAction() {
        return this.Action;
    }

    public Player getAttacker() {
        return this.Attacker;
    }

    public Entity getAttacked() {
        return this.Attacked;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean arg0) {
        this.cancelled = arg0;
    }
}

