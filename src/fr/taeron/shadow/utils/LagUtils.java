/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package fr.taeron.shadow.utils;

import org.bukkit.Bukkit;

public class LagUtils {
    public static double getTPS() {
        return Math.floor(Bukkit.spigot().getTPS()[0] * 100.0) / 100.0;
    }
}

