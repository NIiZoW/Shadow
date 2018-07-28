package fr.taeron.shadow.utils;

import fr.taeron.shadow.utils.UtilBlock;
import fr.taeron.shadow.utils.UtilCheat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.Damageable;

public class UtilPlayer {
    public static void clear(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        ((CraftPlayer)player).getHandle().inventory.b(new NBTTagList());
        player.setSprinting(false);
        player.setFoodLevel(20);
        player.setSaturation(3.0f);
        player.setExhaustion(0.0f);
        player.setMaxHealth(20.0);
        player.setHealth(((Damageable)player).getMaxHealth());
        player.setFireTicks(0);
        player.setFallDistance(0.0f);
        player.setLevel(0);
        player.setExp(0.0f);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();
        for (PotionEffect potion : player.getActivePotionEffects()) {
            player.removePotionEffect(potion.getType());
        }
    }

    public static Location getEyeLocation(Player player) {
        Location eye = player.getLocation();
        eye.setY(eye.getY() + player.getEyeHeight());
        return eye;
    }

    public static boolean isInWater(Player player) {
        Material m = player.getLocation().getBlock().getType();
        if (m != Material.STATIONARY_WATER && m != Material.WATER) {
            return false;
        }
        return true;
    }

    public static boolean isOnClimbable(Player player) {
        for (Block block : UtilBlock.getSurrounding(player.getLocation().getBlock(), false)) {
            if (block.getType() != Material.LADDER && block.getType() != Material.VINE) continue;
            return true;
        }
        if (player.getLocation().getBlock().getType() != Material.LADDER && player.getLocation().getBlock().getType() != Material.VINE) {
            return false;
        }
        return true;
    }

    public static boolean isOnGround(Player player) {
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            return true;
        }
        Location a = player.getLocation().clone();
        a.setY(a.getY() - 0.5);
        if (a.getBlock().getType() != Material.AIR) {
            return true;
        }
        a = player.getLocation().clone();
        a.setY(a.getY() + 0.5);
        if (a.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && !UtilCheat.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER})) {
            return false;
        }
        return true;
    }

    public static List<Entity> getNearbyRidables(Location loc, double distance) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (final Entity entity : new ArrayList<Entity>(loc.getWorld().getEntities())) {
            if (!entity.getType().equals((Object)EntityType.HORSE) && !entity.getType().equals((Object)EntityType.BOAT)) continue;
            Bukkit.getServer().broadcastMessage(String.valueOf(entity.getLocation().distance(loc)));
            if (entity.getLocation().distance(loc) > distance) continue;
            entities.add(entity);
        }
        return entities;
    }
}

