package fr.taeron.shadow.utils;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_7_R4.*;
import java.util.*;

public class UtilServer
{
    @SuppressWarnings("deprecation")
	public static Player[] getPlayers() {
        return Bukkit.getServer().getOnlinePlayers();
    }
    
    @SuppressWarnings("unchecked")
	public static List<Entity> getEntities(final World world) {
        final List<Entity> entities = new ArrayList<Entity>();
        final net.minecraft.server.v1_7_R4.World nmsworld = (net.minecraft.server.v1_7_R4.World)((CraftWorld)world).getHandle();
        for (final Object o : new ArrayList<Object>(nmsworld.entityList)) {
            if (o instanceof net.minecraft.server.v1_7_R4.Entity) {
                final net.minecraft.server.v1_7_R4.Entity mcEnt = (net.minecraft.server.v1_7_R4.Entity)o;
                final Entity bukkitEntity = (Entity)mcEnt.getBukkitEntity();
                if (bukkitEntity == null) {
                    continue;
                }
                entities.add(bukkitEntity);
            }
        }
        return entities;
    }
}
