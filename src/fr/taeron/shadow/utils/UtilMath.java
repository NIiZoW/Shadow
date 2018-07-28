/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.NBTTagList
 *  net.minecraft.server.v1_7_R4.PlayerInventory
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package fr.taeron.shadow.utils;

import fr.taeron.shadow.utils.UtilBlock;
import fr.taeron.shadow.utils.UtilCheat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class UtilMath {
    public static Random random = new Random();

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double trim(int degree, double d) {
        String format = "#.#";
        int i = 1;
        while (i < degree) {
            format = String.valueOf(String.valueOf(String.valueOf(format))) + "#";
            ++i;
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d));
    }

    public static int r(int i) {
        return random.nextInt(i);
    }

    public static double abs(double a) {
        return a <= 0.0 ? 0.0 - a : a;
    }

    public static String ArrayToString(String[] list) {
        String string = "";
        String[] arrstring = list;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String key = arrstring[n2];
            string = String.valueOf(String.valueOf(String.valueOf(string))) + key + ",";
            ++n2;
        }
        if (string.length() != 0) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }

    public static String ArrayToString(List<String> list) {
        String string = "";
        for (String key : list) {
            string = String.valueOf(String.valueOf(String.valueOf(string))) + key + ",";
        }
        if (string.length() != 0) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }

    public static String[] StringToArray(String string, String split) {
        return string.split(split);
    }

    public static double offset2d(Entity a, Entity b) {
        return UtilMath.offset2d(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset2d(Location a, Location b) {
        return UtilMath.offset2d(a.toVector(), b.toVector());
    }

    public static double offset2d(Vector a, Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static double offset(Entity a, Entity b) {
        return UtilMath.offset(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset(Location a, Location b) {
        return UtilMath.offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static Vector getHorizontalVector(Vector v) {
        v.setY(0);
        return v;
    }

    public static Vector getVerticalVector(Vector v) {
        v.setX(0);
        v.setZ(0);
        return v;
    }

    public static String serializeLocation(Location location) {
        int X = (int)location.getX();
        int Y = (int)location.getY();
        int Z = (int)location.getZ();
        int P = (int)location.getPitch();
        int Yaw = (int)location.getYaw();
        return new String(String.valueOf(String.valueOf(String.valueOf(location.getWorld().getName()))) + "," + X + "," + Y + "," + Z + "," + P + "," + Yaw);
    }

    public static Location deserializeLocation(String string) {
        String[] parts = string.split(",");
        World world = Bukkit.getServer().getWorld(parts[0]);
        Double LX = Double.parseDouble(parts[1]);
        Double LY = Double.parseDouble(parts[2]);
        Double LZ = Double.parseDouble(parts[3]);
        Float P = Float.valueOf(Float.parseFloat(parts[4]));
        Float Y = Float.valueOf(Float.parseFloat(parts[5]));
        Location result = new Location(world, LX.doubleValue(), LY.doubleValue(), LZ.doubleValue());
        result.setPitch(P.floatValue());
        result.setYaw(Y.floatValue());
        return result;
    }

    public static long averageLong(List<Long> list) {
        long add = 0;
        for (Long listlist : list) {
            add += listlist.longValue();
        }
        return add / (long)list.size();
    }

    public static double averageDouble(List<Double> list) {
        Double add = 0.0;
        for (Double listlist : list) {
            add = add + listlist;
        }
        return add / (double)list.size();
    }

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

