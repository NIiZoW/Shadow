package fr.taeron.shadow.utils;

import fr.taeron.shadow.utils.UtilBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public final class UtilCheat {
    private static final List<Material> INSTANT_BREAK = new ArrayList<Material>();
    private static final List<Material> FOOD = new ArrayList<Material>();
    private static final List<Material> INTERACTABLE = new ArrayList<Material>();
    private static final Map<Material, Material> COMBO = new HashMap<Material, Material>();
    public static final String SPY_METADATA = "ac-spydata";
    private static int[] $SWITCH_TABLE$org$bukkit$Material;

    static {
        INSTANT_BREAK.add(Material.RED_MUSHROOM);
        INSTANT_BREAK.add(Material.RED_ROSE);
        INSTANT_BREAK.add(Material.BROWN_MUSHROOM);
        INSTANT_BREAK.add(Material.YELLOW_FLOWER);
        INSTANT_BREAK.add(Material.REDSTONE);
        INSTANT_BREAK.add(Material.REDSTONE_TORCH_OFF);
        INSTANT_BREAK.add(Material.REDSTONE_TORCH_ON);
        INSTANT_BREAK.add(Material.REDSTONE_WIRE);
        INSTANT_BREAK.add(Material.LONG_GRASS);
        INSTANT_BREAK.add(Material.PAINTING);
        INSTANT_BREAK.add(Material.WHEAT);
        INSTANT_BREAK.add(Material.SUGAR_CANE);
        INSTANT_BREAK.add(Material.SUGAR_CANE_BLOCK);
        INSTANT_BREAK.add(Material.DIODE);
        INSTANT_BREAK.add(Material.DIODE_BLOCK_OFF);
        INSTANT_BREAK.add(Material.DIODE_BLOCK_ON);
        INSTANT_BREAK.add(Material.SAPLING);
        INSTANT_BREAK.add(Material.TORCH);
        INSTANT_BREAK.add(Material.CROPS);
        INSTANT_BREAK.add(Material.SNOW);
        INSTANT_BREAK.add(Material.TNT);
        INSTANT_BREAK.add(Material.POTATO);
        INSTANT_BREAK.add(Material.CARROT);
        INTERACTABLE.add(Material.STONE_BUTTON);
        INTERACTABLE.add(Material.LEVER);
        INTERACTABLE.add(Material.CHEST);
        FOOD.add(Material.COOKED_BEEF);
        FOOD.add(Material.COOKED_CHICKEN);
        FOOD.add(Material.COOKED_FISH);
        FOOD.add(Material.GRILLED_PORK);
        FOOD.add(Material.PORK);
        FOOD.add(Material.MUSHROOM_SOUP);
        FOOD.add(Material.RAW_BEEF);
        FOOD.add(Material.RAW_CHICKEN);
        FOOD.add(Material.RAW_FISH);
        FOOD.add(Material.APPLE);
        FOOD.add(Material.GOLDEN_APPLE);
        FOOD.add(Material.MELON);
        FOOD.add(Material.COOKIE);
        FOOD.add(Material.BREAD);
        FOOD.add(Material.SPIDER_EYE);
        FOOD.add(Material.ROTTEN_FLESH);
        FOOD.add(Material.POTATO_ITEM);
        COMBO.put(Material.SHEARS, Material.WOOL);
        COMBO.put(Material.IRON_SWORD, Material.WEB);
        COMBO.put(Material.DIAMOND_SWORD, Material.WEB);
        COMBO.put(Material.STONE_SWORD, Material.WEB);
        COMBO.put(Material.WOOD_SWORD, Material.WEB);
    }

    public static boolean isSafeSetbackLocation(Player player) {
        if ((UtilCheat.isInWeb(player) || UtilCheat.isInWater(player) || !UtilCheat.cantStandAtSingle(player.getLocation().getBlock())) && !player.getEyeLocation().getBlock().getType().isSolid()) {
            return true;
        }
        return false;
    }

    public static double getXDelta(Location one, Location two) {
        return Math.abs(one.getX() - two.getX());
    }

    public static double getZDelta(Location one, Location two) {
        return Math.abs(one.getZ() - two.getZ());
    }

    public static double getDistance3D(Location one, Location two) {
        double toReturn = 0.0;
        double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static double getVerticalDistance(Location one, Location two) {
        double toReturn = 0.0;
        double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        double sqrt = Math.sqrt(ySqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static double getHorizontalDistance(Location one, Location two) {
        double toReturn = 0.0;
        double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        double sqrt = Math.sqrt(xSqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static boolean cantStandAtBetter(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);
        boolean center1 = otherBlock.getType() == Material.AIR;
        boolean north1 = otherBlock.getRelative(BlockFace.NORTH).getType() == Material.AIR;
        boolean east1 = otherBlock.getRelative(BlockFace.EAST).getType() == Material.AIR;
        boolean south1 = otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.AIR;
        boolean west1 = otherBlock.getRelative(BlockFace.WEST).getType() == Material.AIR;
        boolean northeast1 = otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.AIR;
        boolean northwest1 = otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.AIR;
        boolean southeast1 = otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.AIR;
        boolean southwest1 = otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.AIR;
        final boolean overAir1 = otherBlock.getRelative(BlockFace.DOWN).getType() == Material.AIR || otherBlock.getRelative(BlockFace.DOWN).getType() == Material.WATER || otherBlock.getRelative(BlockFace.DOWN).getType() == Material.LAVA;
        if (center1 && north1 && east1 && south1 && west1 && northeast1 && southeast1 && northwest1 && southwest1 && overAir1) {
            return true;
        }
        return false;
    }

    public static boolean cantStandAtSingle(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);
        boolean center = otherBlock.getType() == Material.AIR;
        return center;
    }

    public static boolean cantStandAtWater(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);
        boolean isHover = block.getType() == Material.AIR;
        boolean n = otherBlock.getRelative(BlockFace.NORTH).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER;
        boolean s = otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.WATER || otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.STATIONARY_WATER;
        boolean e = otherBlock.getRelative(BlockFace.EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.EAST).getType() == Material.STATIONARY_WATER;
        boolean w = otherBlock.getRelative(BlockFace.WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.WEST).getType() == Material.STATIONARY_WATER;
        boolean ne = otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.STATIONARY_WATER;
        boolean nw = otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.STATIONARY_WATER;
        boolean se = otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER;
        boolean sw = otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.STATIONARY_WATER;
        if (n && s && e && w && ne && nw && se && sw && isHover) {
            return true;
        }
        return false;
    }

    public static boolean canStandWithin(Block block) {
        boolean isSand = block.getType() == Material.SAND;
        boolean isGravel = block.getType() == Material.GRAVEL;
        final boolean solid = block.getType().isSolid() && !block.getType().name().toLowerCase().contains("door") && !block.getType().name().toLowerCase().contains("fence") && !block.getType().name().toLowerCase().contains("bars") && !block.getType().name().toLowerCase().contains("sign");
        if (!(isSand || isGravel || solid)) {
            return true;
        }
        return false;
    }

    public static Vector getRotation(Location one, Location two) {
        double dx = two.getX() - one.getX();
        double dy = two.getY() - one.getY();
        double dz = two.getZ() - one.getZ();
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float)(Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(dy, distanceXZ) * 180.0 / 3.141592653589793);
        return new Vector(yaw, pitch, 0.0f);
    }

    public static double clamp180(double theta) {
        if ((theta %= 360.0) >= 180.0) {
            theta -= 360.0;
        }
        if (theta < -180.0) {
            theta += 360.0;
        }
        return theta;
    }

    public static int getLevelForEnchantment(Player player, String enchantment) {
        try {
            Enchantment theEnchantment = Enchantment.getByName((String)enchantment);
            ItemStack[] armorContents = player.getInventory().getArmorContents();
            int length = armorContents.length;
            int i = 0;
            while (i < length) {
                ItemStack item = armorContents[i];
                if (item.containsEnchantment(theEnchantment)) {
                    return item.getEnchantmentLevel(theEnchantment);
                }
                ++i;
            }
        }
        catch (Exception e) {
            return -1;
        }
        return -1;
    }

    public static boolean cantStandAt(Block block) {
        if (!UtilCheat.canStand(block) && UtilCheat.cantStandClose(block) && UtilCheat.cantStandFar(block)) {
            return true;
        }
        return false;
    }

    public static boolean cantStandAtExp(Location location) {
        return UtilCheat.cantStandAt(new Location(location.getWorld(), UtilCheat.fixXAxis(location.getX()), location.getY() - 0.01, (double)location.getBlockZ()).getBlock());
    }

    public static boolean cantStandClose(Block block) {
        if (!(UtilCheat.canStand(block.getRelative(BlockFace.NORTH)) || UtilCheat.canStand(block.getRelative(BlockFace.EAST)) || UtilCheat.canStand(block.getRelative(BlockFace.SOUTH)) || UtilCheat.canStand(block.getRelative(BlockFace.WEST)))) {
            return true;
        }
        return false;
    }

    public static boolean cantStandFar(Block block) {
        if (!(UtilCheat.canStand(block.getRelative(BlockFace.NORTH_WEST)) || UtilCheat.canStand(block.getRelative(BlockFace.NORTH_EAST)) || UtilCheat.canStand(block.getRelative(BlockFace.SOUTH_WEST)) || UtilCheat.canStand(block.getRelative(BlockFace.SOUTH_EAST)))) {
            return true;
        }
        return false;
    }

    public static boolean canStand(Block block) {
        if (!block.isLiquid() && block.getType() != Material.AIR) {
            return true;
        }
        return false;
    }

    public static boolean isFullyInWater(Location player) {
        double touchedX = UtilCheat.fixXAxis(player.getX());
        if (new Location(player.getWorld(), touchedX, player.getY(), (double)player.getBlockZ()).getBlock().isLiquid() && new Location(player.getWorld(), touchedX, (double)Math.round(player.getY()), (double)player.getBlockZ()).getBlock().isLiquid()) {
            return true;
        }
        return false;
    }

    public static double fixXAxis(double x) {
        double touchedX = x;
        double rem = touchedX - (double)Math.round(touchedX) + 0.01;
        if (rem < 0.3) {
            touchedX = NumberConversions.floor((double)x) - 1;
        }
        return touchedX;
    }

    public static boolean isHoveringOverWater(Location player, int blocks) {
        int i = player.getBlockY();
        while (i > player.getBlockY() - blocks) {
            Block newloc = new Location(player.getWorld(), (double)player.getBlockX(), (double)i, (double)player.getBlockZ()).getBlock();
            if (newloc.getType() != Material.AIR) {
                return newloc.isLiquid();
            }
            --i;
        }
        return false;
    }

    public static boolean isHoveringOverWater(Location player) {
        return UtilCheat.isHoveringOverWater(player, 25);
    }

    public static boolean isInstantBreak(Material m) {
        return INSTANT_BREAK.contains((Object)m);
    }

    public static boolean isFood(Material m) {
        return FOOD.contains((Object)m);
    }

    public static boolean isSlab(Block block) {
        Material type = block.getType();
        switch (UtilCheat.$SWITCH_TABLE$org$bukkit$Material()[type.ordinal()]) {
            case 44: 
            case 45: 
            case 127: 
            case 128: {
                return true;
            }
        }
        return false;
    }

    public static boolean isStair(Block block) {
        Material type = block.getType();
        switch (UtilCheat.$SWITCH_TABLE$org$bukkit$Material()[type.ordinal()]) {
            case 54: 
            case 68: 
            case 111: 
            case 116: 
            case 130: 
            case 136: 
            case 137: 
            case 138: 
            case 158: {
                return true;
            }
        }
        return false;
    }

    public static boolean isInteractable(Material m) {
        return INTERACTABLE.contains((Object)m);
    }

    public static boolean sprintFly(Player player) {
        if (!player.isSprinting() && !player.isFlying()) {
            return false;
        }
        return true;
    }

    public static boolean isOnLilyPad(Player player) {
        Block block = player.getLocation().getBlock();
        Material lily = Material.WATER_LILY;
        if (block.getType() != lily && block.getRelative(BlockFace.NORTH).getType() != lily && block.getRelative(BlockFace.SOUTH).getType() != lily && block.getRelative(BlockFace.EAST).getType() != lily && block.getRelative(BlockFace.WEST).getType() != lily) {
            return false;
        }
        return true;
    }

    public static boolean isSubmersed(Player player) {
        if (player.getLocation().getBlock().isLiquid() && player.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid()) {
            return true;
        }
        return false;
    }

    public static boolean isInWater(Player player) {
        if (!(player.getLocation().getBlock().isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid())) {
            return false;
        }
        return true;
    }

    public static ArrayList<Location> getBlocksAroundCenter(Location loc, int radius) {
        ArrayList<Location> blocks = new ArrayList<Location>();
        int x = loc.getBlockX() - radius;
        while (x <= loc.getBlockX() + radius) {
            int y = loc.getBlockY() - radius;
            while (y <= loc.getBlockY() + radius) {
                int z = loc.getBlockZ() - radius;
                while (z <= loc.getBlockZ() + radius) {
                    Location l = new Location(loc.getWorld(), (double)x, (double)y, (double)z);
                    if (l.distance(loc) <= (double)radius) {
                        blocks.add(l);
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        return blocks;
    }

    public static boolean isInWeb(Player player) {
        for (Location b : UtilCheat.getBlocksAroundCenter(player.getLocation(), 2)) {
            if (b.getBlock().getType() != Material.WEB) continue;
            return true;
        }
        return false;
    }

    public static boolean isClimbableBlock(Block block) {
        if (block.getType() != Material.VINE && block.getType() != Material.LADDER && block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER) {
            return false;
        }
        return true;
    }

    public static boolean isOnVine(Player player) {
        if (player.getLocation().getBlock().getType() == Material.VINE) {
            return true;
        }
        return false;
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean blocksNear(Player player) {
        return UtilCheat.blocksNear(player.getLocation());
    }

    public static boolean blocksNear(Location loc) {
        boolean nearBlocks = false;
        for (Block block2 : UtilBlock.getSurrounding(loc.getBlock(), true)) {
            if (block2.getType() == Material.AIR) continue;
            nearBlocks = true;
            break;
        }
        for (Block block2 : UtilBlock.getSurrounding(loc.getBlock(), false)) {
            if (block2.getType() == Material.AIR) continue;
            nearBlocks = true;
            break;
        }
        loc.setY(loc.getY() - 0.5);
        if (loc.getBlock().getType() != Material.AIR) {
            nearBlocks = true;
        }
        if (UtilCheat.isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER})) {
            nearBlocks = true;
        }
        return nearBlocks;
    }

    public static boolean slabsNear(Location loc) {
        boolean nearBlocks = false;
        for (Block bl2 : UtilBlock.getSurrounding(loc.getBlock(), true)) {
            if (!bl2.getType().equals((Object)Material.STEP) && !bl2.getType().equals((Object)Material.DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_STEP)) continue;
            nearBlocks = true;
            break;
        }
        for (Block bl2 : UtilBlock.getSurrounding(loc.getBlock(), false)) {
            if (!bl2.getType().equals((Object)Material.STEP) && !bl2.getType().equals((Object)Material.DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_DOUBLE_STEP) && !bl2.getType().equals((Object)Material.WOOD_STEP)) continue;
            nearBlocks = true;
            break;
        }
        if (UtilCheat.isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.STEP, Material.DOUBLE_STEP, Material.WOOD_DOUBLE_STEP, Material.WOOD_STEP})) {
            nearBlocks = true;
        }
        return nearBlocks;
    }

    public static boolean isBlock(Block block, Material[] materials) {
        Material type = block.getType();
        Material[] arrmaterial = materials;
        int n = arrmaterial.length;
        int n2 = 0;
        while (n2 < n) {
            Material m = arrmaterial[n2];
            if (m == type) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static String[] getCommands(String command) {
        return command.replaceAll("COMMAND\\[", "").replaceAll("]", "").split(";");
    }

    public static String removeWhitespace(String string) {
        return string.replaceAll(" ", "");
    }

    public static boolean hasArmorEnchantment(Player player, Enchantment e) {
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        int length = armorContents.length;
        int i = 0;
        while (i < length) {
            ItemStack is = armorContents[i];
            if (is != null && is.containsEnchantment(e)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static String listToCommaString(List<String> list) {
        StringBuilder b = new StringBuilder();
        int i = 0;
        while (i < list.size()) {
            b.append(list.get(i));
            if (i < list.size() - 1) {
                b.append(",");
            }
            ++i;
        }
        return b.toString();
    }

    public static long lifeToSeconds(String string) {
        if (string.equals("0") || string.equals("")) {
            return 0;
        }
        String[] lifeMatch = new String[]{"d", "h", "m", "s"};
        int[] lifeInterval = new int[]{86400, 3600, 60, 1};
        long seconds = 0;
        int i = 0;
        while (i < lifeMatch.length) {
            Matcher matcher = Pattern.compile("([0-9]*)" + lifeMatch[i]).matcher(string);
            while (matcher.find()) {
                seconds += (long)(Integer.parseInt(matcher.group(1)) * lifeInterval[i]);
            }
            ++i;
        }
        return seconds;
    }

    public static double[] cursor(Player player, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0, entity.getEyeHeight(), 0.0);
        Location playerLoc = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        Vector expectedRotation = UtilCheat.getRotation(playerLoc, entityLoc);
        double deltaYaw = UtilCheat.clamp180(playerRotation.getX() - expectedRotation.getX());
        double deltaPitch = UtilCheat.clamp180(playerRotation.getY() - expectedRotation.getY());
        double horizontalDistance = UtilCheat.getHorizontalDistance(playerLoc, entityLoc);
        double distance = UtilCheat.getDistance3D(playerLoc, entityLoc);
        double offsetX = deltaYaw * horizontalDistance * distance;
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;
        return new double[]{Math.abs(offsetX), Math.abs(offsetY)};
    }

    public static double getAimbotoffset(Location playerLocLoc, double playerEyeHeight, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0, entity.getEyeHeight(), 0.0);
        Location playerLoc = playerLocLoc.add(0.0, playerEyeHeight, 0.0);
        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        Vector expectedRotation = UtilCheat.getRotation(playerLoc, entityLoc);
        double deltaYaw = UtilCheat.clamp180(playerRotation.getX() - expectedRotation.getX());
        double horizontalDistance = UtilCheat.getHorizontalDistance(playerLoc, entityLoc);
        double distance = UtilCheat.getDistance3D(playerLoc, entityLoc);
        double offsetX = deltaYaw * horizontalDistance * distance;
        return offsetX;
    }

    public static double getAimbotoffset2(Location playerLocLoc, double playerEyeHeight, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0, entity.getEyeHeight(), 0.0);
        Location playerLoc = playerLocLoc.add(0.0, playerEyeHeight, 0.0);
        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        Vector expectedRotation = UtilCheat.getRotation(playerLoc, entityLoc);
        double deltaPitch = UtilCheat.clamp180(playerRotation.getY() - expectedRotation.getY());
        double distance = UtilCheat.getDistance3D(playerLoc, entityLoc);
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;
        return offsetY;
    }

    public static double[] getOffsetsOffCursor(Player player, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0, entity.getEyeHeight(), 0.0);
        Location playerLoc = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        Vector expectedRotation = UtilCheat.getRotation(playerLoc, entityLoc);
        double deltaYaw = UtilCheat.clamp180(playerRotation.getX() - expectedRotation.getX());
        double deltaPitch = UtilCheat.clamp180(playerRotation.getY() - expectedRotation.getY());
        double horizontalDistance = UtilCheat.getHorizontalDistance(playerLoc, entityLoc);
        double distance = UtilCheat.getDistance3D(playerLoc, entityLoc);
        double offsetX = deltaYaw * horizontalDistance * distance;
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;
        return new double[]{Math.abs(offsetX), Math.abs(offsetY)};
    }

    public static double getOffsetOffCursor(Player player, LivingEntity entity) {
        double offset = 0.0;
        double[] offsets = UtilCheat.getOffsetsOffCursor(player, entity);
        offset += offsets[0];
        return offset += offsets[1];
    }

    @SuppressWarnings("deprecation")
	static int[] $SWITCH_TABLE$org$bukkit$Material() {
        int[] $switch_TABLE$org$bukkit$Material = $SWITCH_TABLE$org$bukkit$Material;
        if ($switch_TABLE$org$bukkit$Material != null) {
            return $switch_TABLE$org$bukkit$Material;
        }
        int[] $switch_TABLE$org$bukkit$Material2 = new int[Material.values().length];
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ACACIA_STAIRS.ordinal()] = 165;
        }
        catch (NoSuchFieldError var2_2) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ACTIVATOR_RAIL.ordinal()] = 159;
        }
        catch (NoSuchFieldError var2_3) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.AIR.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2_4) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ANVIL.ordinal()] = 147;
        }
        catch (NoSuchFieldError var2_5) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.APPLE.ordinal()] = 177;
        }
        catch (NoSuchFieldError var2_6) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ARROW.ordinal()] = 179;
        }
        catch (NoSuchFieldError var2_7) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BAKED_POTATO.ordinal()] = 310;
        }
        catch (NoSuchFieldError var2_8) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BEACON.ordinal()] = 140;
        }
        catch (NoSuchFieldError var2_9) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BED.ordinal()] = 272;
        }
        catch (NoSuchFieldError var2_10) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BEDROCK.ordinal()] = 8;
        }
        catch (NoSuchFieldError var2_11) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BED_BLOCK.ordinal()] = 27;
        }
        catch (NoSuchFieldError var2_12) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BIRCH_WOOD_STAIRS.ordinal()] = 137;
        }
        catch (NoSuchFieldError var2_13) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BLAZE_POWDER.ordinal()] = 294;
        }
        catch (NoSuchFieldError var2_14) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BLAZE_ROD.ordinal()] = 286;
        }
        catch (NoSuchFieldError var2_15) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BOAT.ordinal()] = 250;
        }
        catch (NoSuchFieldError var2_16) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BONE.ordinal()] = 269;
        }
        catch (NoSuchFieldError var2_17) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BOOK.ordinal()] = 257;
        }
        catch (NoSuchFieldError var2_18) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BOOKSHELF.ordinal()] = 48;
        }
        catch (NoSuchFieldError var2_19) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BOOK_AND_QUILL.ordinal()] = 303;
        }
        catch (NoSuchFieldError var2_20) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BOW.ordinal()] = 178;
        }
        catch (NoSuchFieldError var2_21) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BOWL.ordinal()] = 198;
        }
        catch (NoSuchFieldError var2_22) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BREAD.ordinal()] = 214;
        }
        catch (NoSuchFieldError var2_23) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BREWING_STAND.ordinal()] = 119;
        }
        catch (NoSuchFieldError var2_24) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BREWING_STAND_ITEM.ordinal()] = 296;
        }
        catch (NoSuchFieldError var2_25) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BRICK.ordinal()] = 46;
        }
        catch (NoSuchFieldError var2_26) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BRICK_STAIRS.ordinal()] = 110;
        }
        catch (NoSuchFieldError var2_27) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BROWN_MUSHROOM.ordinal()] = 40;
        }
        catch (NoSuchFieldError var2_28) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BUCKET.ordinal()] = 242;
        }
        catch (NoSuchFieldError var2_29) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.BURNING_FURNACE.ordinal()] = 63;
        }
        catch (NoSuchFieldError var2_30) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CACTUS.ordinal()] = 82;
        }
        catch (NoSuchFieldError var2_31) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CAKE.ordinal()] = 271;
        }
        catch (NoSuchFieldError var2_32) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CAKE_BLOCK.ordinal()] = 93;
        }
        catch (NoSuchFieldError var2_33) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CARPET.ordinal()] = 168;
        }
        catch (NoSuchFieldError var2_34) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CARROT.ordinal()] = 143;
        }
        catch (NoSuchFieldError var2_35) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CARROT_ITEM.ordinal()] = 308;
        }
        catch (NoSuchFieldError var2_36) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CARROT_STICK.ordinal()] = 315;
        }
        catch (NoSuchFieldError var2_37) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CAULDRON.ordinal()] = 120;
        }
        catch (NoSuchFieldError var2_38) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CAULDRON_ITEM.ordinal()] = 297;
        }
        catch (NoSuchFieldError var2_39) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CHAINMAIL_BOOTS.ordinal()] = 222;
        }
        catch (NoSuchFieldError var2_40) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 220;
        }
        catch (NoSuchFieldError var2_41) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CHAINMAIL_HELMET.ordinal()] = 219;
        }
        catch (NoSuchFieldError var2_42) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CHAINMAIL_LEGGINGS.ordinal()] = 221;
        }
        catch (NoSuchFieldError var2_43) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CHEST.ordinal()] = 55;
        }
        catch (NoSuchFieldError var2_44) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CLAY.ordinal()] = 83;
        }
        catch (NoSuchFieldError var2_45) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CLAY_BALL.ordinal()] = 254;
        }
        catch (NoSuchFieldError var2_46) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CLAY_BRICK.ordinal()] = 253;
        }
        catch (NoSuchFieldError var2_47) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COAL.ordinal()] = 180;
        }
        catch (NoSuchFieldError var2_48) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COAL_BLOCK.ordinal()] = 170;
        }
        catch (NoSuchFieldError var2_49) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COAL_ORE.ordinal()] = 17;
        }
        catch (NoSuchFieldError var2_50) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COBBLESTONE.ordinal()] = 5;
        }
        catch (NoSuchFieldError var2_51) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COBBLESTONE_STAIRS.ordinal()] = 68;
        }
        catch (NoSuchFieldError var2_52) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COBBLE_WALL.ordinal()] = 141;
        }
        catch (NoSuchFieldError var2_53) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COCOA.ordinal()] = 129;
        }
        catch (NoSuchFieldError var2_54) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COMMAND.ordinal()] = 139;
        }
        catch (NoSuchFieldError var2_55) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COMMAND_MINECART.ordinal()] = 331;
        }
        catch (NoSuchFieldError var2_56) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COMPASS.ordinal()] = 262;
        }
        catch (NoSuchFieldError var2_57) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COOKED_BEEF.ordinal()] = 281;
        }
        catch (NoSuchFieldError var2_58) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COOKED_CHICKEN.ordinal()] = 283;
        }
        catch (NoSuchFieldError var2_59) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COOKED_FISH.ordinal()] = 267;
        }
        catch (NoSuchFieldError var2_60) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.COOKIE.ordinal()] = 274;
        }
        catch (NoSuchFieldError var2_61) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.CROPS.ordinal()] = 60;
        }
        catch (NoSuchFieldError var2_62) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DARK_OAK_STAIRS.ordinal()] = 166;
        }
        catch (NoSuchFieldError var2_63) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DAYLIGHT_DETECTOR.ordinal()] = 153;
        }
        catch (NoSuchFieldError var2_64) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DEAD_BUSH.ordinal()] = 33;
        }
        catch (NoSuchFieldError var2_65) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DETECTOR_RAIL.ordinal()] = 29;
        }
        catch (NoSuchFieldError var2_66) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND.ordinal()] = 181;
        }
        catch (NoSuchFieldError var2_67) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_AXE.ordinal()] = 196;
        }
        catch (NoSuchFieldError var2_68) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_BARDING.ordinal()] = 328;
        }
        catch (NoSuchFieldError var2_69) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_BLOCK.ordinal()] = 58;
        }
        catch (NoSuchFieldError var2_70) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_BOOTS.ordinal()] = 230;
        }
        catch (NoSuchFieldError var2_71) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_CHESTPLATE.ordinal()] = 228;
        }
        catch (NoSuchFieldError var2_72) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_HELMET.ordinal()] = 227;
        }
        catch (NoSuchFieldError var2_73) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_HOE.ordinal()] = 210;
        }
        catch (NoSuchFieldError var2_74) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_LEGGINGS.ordinal()] = 229;
        }
        catch (NoSuchFieldError var2_75) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_ORE.ordinal()] = 57;
        }
        catch (NoSuchFieldError var2_76) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_PICKAXE.ordinal()] = 195;
        }
        catch (NoSuchFieldError var2_77) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_SPADE.ordinal()] = 194;
        }
        catch (NoSuchFieldError var2_78) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIAMOND_SWORD.ordinal()] = 193;
        }
        catch (NoSuchFieldError var2_79) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIODE.ordinal()] = 273;
        }
        catch (NoSuchFieldError var2_80) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIODE_BLOCK_OFF.ordinal()] = 94;
        }
        catch (NoSuchFieldError var2_81) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIODE_BLOCK_ON.ordinal()] = 95;
        }
        catch (NoSuchFieldError var2_82) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DIRT.ordinal()] = 4;
        }
        catch (NoSuchFieldError var2_83) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DISPENSER.ordinal()] = 24;
        }
        catch (NoSuchFieldError var2_84) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DOUBLE_PLANT.ordinal()] = 172;
        }
        catch (NoSuchFieldError var2_85) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DOUBLE_STEP.ordinal()] = 44;
        }
        catch (NoSuchFieldError var2_86) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DRAGON_EGG.ordinal()] = 124;
        }
        catch (NoSuchFieldError var2_87) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.DROPPER.ordinal()] = 160;
        }
        catch (NoSuchFieldError var2_88) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EGG.ordinal()] = 261;
        }
        catch (NoSuchFieldError var2_89) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EMERALD.ordinal()] = 305;
        }
        catch (NoSuchFieldError var2_90) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EMERALD_BLOCK.ordinal()] = 135;
        }
        catch (NoSuchFieldError var2_91) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EMERALD_ORE.ordinal()] = 131;
        }
        catch (NoSuchFieldError var2_92) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EMPTY_MAP.ordinal()] = 312;
        }
        catch (NoSuchFieldError var2_93) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENCHANTED_BOOK.ordinal()] = 320;
        }
        catch (NoSuchFieldError var2_94) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENCHANTMENT_TABLE.ordinal()] = 118;
        }
        catch (NoSuchFieldError var2_95) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENDER_CHEST.ordinal()] = 132;
        }
        catch (NoSuchFieldError var2_96) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENDER_PEARL.ordinal()] = 285;
        }
        catch (NoSuchFieldError var2_97) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENDER_PORTAL.ordinal()] = 121;
        }
        catch (NoSuchFieldError var2_98) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENDER_PORTAL_FRAME.ordinal()] = 122;
        }
        catch (NoSuchFieldError var2_99) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ENDER_STONE.ordinal()] = 123;
        }
        catch (NoSuchFieldError var2_100) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EXPLOSIVE_MINECART.ordinal()] = 324;
        }
        catch (NoSuchFieldError var2_101) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EXP_BOTTLE.ordinal()] = 301;
        }
        catch (NoSuchFieldError var2_102) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.EYE_OF_ENDER.ordinal()] = 298;
        }
        catch (NoSuchFieldError var2_103) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FEATHER.ordinal()] = 205;
        }
        catch (NoSuchFieldError var2_104) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FENCE.ordinal()] = 86;
        }
        catch (NoSuchFieldError var2_105) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FENCE_GATE.ordinal()] = 109;
        }
        catch (NoSuchFieldError var2_106) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FERMENTED_SPIDER_EYE.ordinal()] = 293;
        }
        catch (NoSuchFieldError var2_107) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FIRE.ordinal()] = 52;
        }
        catch (NoSuchFieldError var2_108) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FIREBALL.ordinal()] = 302;
        }
        catch (NoSuchFieldError var2_109) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FIREWORK.ordinal()] = 318;
        }
        catch (NoSuchFieldError var2_110) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FIREWORK_CHARGE.ordinal()] = 319;
        }
        catch (NoSuchFieldError var2_111) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FISHING_ROD.ordinal()] = 263;
        }
        catch (NoSuchFieldError var2_112) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FLINT.ordinal()] = 235;
        }
        catch (NoSuchFieldError var2_113) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FLINT_AND_STEEL.ordinal()] = 176;
        }
        catch (NoSuchFieldError var2_114) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FLOWER_POT.ordinal()] = 142;
        }
        catch (NoSuchFieldError var2_115) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FLOWER_POT_ITEM.ordinal()] = 307;
        }
        catch (NoSuchFieldError var2_116) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.FURNACE.ordinal()] = 62;
        }
        catch (NoSuchFieldError var2_117) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GHAST_TEAR.ordinal()] = 287;
        }
        catch (NoSuchFieldError var2_118) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GLASS.ordinal()] = 21;
        }
        catch (NoSuchFieldError var2_119) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GLASS_BOTTLE.ordinal()] = 291;
        }
        catch (NoSuchFieldError var2_120) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GLOWING_REDSTONE_ORE.ordinal()] = 75;
        }
        catch (NoSuchFieldError var2_121) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GLOWSTONE.ordinal()] = 90;
        }
        catch (NoSuchFieldError var2_122) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GLOWSTONE_DUST.ordinal()] = 265;
        }
        catch (NoSuchFieldError var2_123) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLDEN_APPLE.ordinal()] = 239;
        }
        catch (NoSuchFieldError var2_124) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLDEN_CARROT.ordinal()] = 313;
        }
        catch (NoSuchFieldError var2_125) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_AXE.ordinal()] = 203;
        }
        catch (NoSuchFieldError var2_126) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_BARDING.ordinal()] = 327;
        }
        catch (NoSuchFieldError var2_127) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_BLOCK.ordinal()] = 42;
        }
        catch (NoSuchFieldError var2_128) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_BOOTS.ordinal()] = 234;
        }
        catch (NoSuchFieldError var2_129) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_CHESTPLATE.ordinal()] = 232;
        }
        catch (NoSuchFieldError var2_130) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_HELMET.ordinal()] = 231;
        }
        catch (NoSuchFieldError var2_131) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_HOE.ordinal()] = 211;
        }
        catch (NoSuchFieldError var2_132) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_INGOT.ordinal()] = 183;
        }
        catch (NoSuchFieldError var2_133) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_LEGGINGS.ordinal()] = 233;
        }
        catch (NoSuchFieldError var2_134) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_NUGGET.ordinal()] = 288;
        }
        catch (NoSuchFieldError var2_135) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_ORE.ordinal()] = 15;
        }
        catch (NoSuchFieldError var2_136) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_PICKAXE.ordinal()] = 202;
        }
        catch (NoSuchFieldError var2_137) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_PLATE.ordinal()] = 149;
        }
        catch (NoSuchFieldError var2_138) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_RECORD.ordinal()] = 332;
        }
        catch (NoSuchFieldError var2_139) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_SPADE.ordinal()] = 201;
        }
        catch (NoSuchFieldError var2_140) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GOLD_SWORD.ordinal()] = 200;
        }
        catch (NoSuchFieldError var2_141) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GRASS.ordinal()] = 3;
        }
        catch (NoSuchFieldError var2_142) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GRAVEL.ordinal()] = 14;
        }
        catch (NoSuchFieldError var2_143) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GREEN_RECORD.ordinal()] = 333;
        }
        catch (NoSuchFieldError var2_144) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.GRILLED_PORK.ordinal()] = 237;
        }
        catch (NoSuchFieldError var2_145) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.HARD_CLAY.ordinal()] = 169;
        }
        catch (NoSuchFieldError var2_146) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.HAY_BLOCK.ordinal()] = 167;
        }
        catch (NoSuchFieldError var2_147) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.HOPPER.ordinal()] = 156;
        }
        catch (NoSuchFieldError var2_148) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.HOPPER_MINECART.ordinal()] = 325;
        }
        catch (NoSuchFieldError var2_149) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.HUGE_MUSHROOM_1.ordinal()] = 101;
        }
        catch (NoSuchFieldError var2_150) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.HUGE_MUSHROOM_2.ordinal()] = 102;
        }
        catch (NoSuchFieldError var2_151) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ICE.ordinal()] = 80;
        }
        catch (NoSuchFieldError var2_152) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.INK_SACK.ordinal()] = 268;
        }
        catch (NoSuchFieldError var2_153) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_AXE.ordinal()] = 175;
        }
        catch (NoSuchFieldError var2_154) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_BARDING.ordinal()] = 326;
        }
        catch (NoSuchFieldError var2_155) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_BLOCK.ordinal()] = 43;
        }
        catch (NoSuchFieldError var2_156) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_BOOTS.ordinal()] = 226;
        }
        catch (NoSuchFieldError var2_157) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_CHESTPLATE.ordinal()] = 224;
        }
        catch (NoSuchFieldError var2_158) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_DOOR.ordinal()] = 247;
        }
        catch (NoSuchFieldError var2_159) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_DOOR_BLOCK.ordinal()] = 72;
        }
        catch (NoSuchFieldError var2_160) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_FENCE.ordinal()] = 103;
        }
        catch (NoSuchFieldError var2_161) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_HELMET.ordinal()] = 223;
        }
        catch (NoSuchFieldError var2_162) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_HOE.ordinal()] = 209;
        }
        catch (NoSuchFieldError var2_163) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_INGOT.ordinal()] = 182;
        }
        catch (NoSuchFieldError var2_164) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_LEGGINGS.ordinal()] = 225;
        }
        catch (NoSuchFieldError var2_165) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_ORE.ordinal()] = 16;
        }
        catch (NoSuchFieldError var2_166) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_PICKAXE.ordinal()] = 174;
        }
        catch (NoSuchFieldError var2_167) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_PLATE.ordinal()] = 150;
        }
        catch (NoSuchFieldError var2_168) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_SPADE.ordinal()] = 173;
        }
        catch (NoSuchFieldError var2_169) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.IRON_SWORD.ordinal()] = 184;
        }
        catch (NoSuchFieldError var2_170) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ITEM_FRAME.ordinal()] = 306;
        }
        catch (NoSuchFieldError var2_171) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.JACK_O_LANTERN.ordinal()] = 92;
        }
        catch (NoSuchFieldError var2_172) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.JUKEBOX.ordinal()] = 85;
        }
        catch (NoSuchFieldError var2_173) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.JUNGLE_WOOD_STAIRS.ordinal()] = 138;
        }
        catch (NoSuchFieldError var2_174) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LADDER.ordinal()] = 66;
        }
        catch (NoSuchFieldError var2_175) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LAPIS_BLOCK.ordinal()] = 23;
        }
        catch (NoSuchFieldError var2_176) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LAPIS_ORE.ordinal()] = 22;
        }
        catch (NoSuchFieldError var2_177) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LAVA.ordinal()] = 11;
        }
        catch (NoSuchFieldError var2_178) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LAVA_BUCKET.ordinal()] = 244;
        }
        catch (NoSuchFieldError var2_179) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEASH.ordinal()] = 329;
        }
        catch (NoSuchFieldError var2_180) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEATHER.ordinal()] = 251;
        }
        catch (NoSuchFieldError var2_181) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEATHER_BOOTS.ordinal()] = 218;
        }
        catch (NoSuchFieldError var2_182) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEATHER_CHESTPLATE.ordinal()] = 216;
        }
        catch (NoSuchFieldError var2_183) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEATHER_HELMET.ordinal()] = 215;
        }
        catch (NoSuchFieldError var2_184) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEATHER_LEGGINGS.ordinal()] = 217;
        }
        catch (NoSuchFieldError var2_185) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEAVES.ordinal()] = 19;
        }
        catch (NoSuchFieldError var2_186) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEAVES_2.ordinal()] = 163;
        }
        catch (NoSuchFieldError var2_187) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LEVER.ordinal()] = 70;
        }
        catch (NoSuchFieldError var2_188) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LOCKED_CHEST.ordinal()] = 96;
        }
        catch (NoSuchFieldError var2_189) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LOG.ordinal()] = 18;
        }
        catch (NoSuchFieldError var2_190) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LOG_2.ordinal()] = 164;
        }
        catch (NoSuchFieldError var2_191) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.LONG_GRASS.ordinal()] = 32;
        }
        catch (NoSuchFieldError var2_192) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MAGMA_CREAM.ordinal()] = 295;
        }
        catch (NoSuchFieldError var2_193) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MAP.ordinal()] = 275;
        }
        catch (NoSuchFieldError var2_194) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MELON.ordinal()] = 277;
        }
        catch (NoSuchFieldError var2_195) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MELON_BLOCK.ordinal()] = 105;
        }
        catch (NoSuchFieldError var2_196) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MELON_SEEDS.ordinal()] = 279;
        }
        catch (NoSuchFieldError var2_197) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MELON_STEM.ordinal()] = 107;
        }
        catch (NoSuchFieldError var2_198) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MILK_BUCKET.ordinal()] = 252;
        }
        catch (NoSuchFieldError var2_199) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MINECART.ordinal()] = 245;
        }
        catch (NoSuchFieldError var2_200) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MOB_SPAWNER.ordinal()] = 53;
        }
        catch (NoSuchFieldError var2_201) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MONSTER_EGG.ordinal()] = 300;
        }
        catch (NoSuchFieldError var2_202) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MONSTER_EGGS.ordinal()] = 99;
        }
        catch (NoSuchFieldError var2_203) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MOSSY_COBBLESTONE.ordinal()] = 49;
        }
        catch (NoSuchFieldError var2_204) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MUSHROOM_SOUP.ordinal()] = 199;
        }
        catch (NoSuchFieldError var2_205) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.MYCEL.ordinal()] = 112;
        }
        catch (NoSuchFieldError var2_206) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NAME_TAG.ordinal()] = 330;
        }
        catch (NoSuchFieldError var2_207) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHERRACK.ordinal()] = 88;
        }
        catch (NoSuchFieldError var2_208) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_BRICK.ordinal()] = 114;
        }
        catch (NoSuchFieldError var2_209) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_BRICK_ITEM.ordinal()] = 322;
        }
        catch (NoSuchFieldError var2_210) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_BRICK_STAIRS.ordinal()] = 116;
        }
        catch (NoSuchFieldError var2_211) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_FENCE.ordinal()] = 115;
        }
        catch (NoSuchFieldError var2_212) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_STALK.ordinal()] = 289;
        }
        catch (NoSuchFieldError var2_213) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_STAR.ordinal()] = 316;
        }
        catch (NoSuchFieldError var2_214) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NETHER_WARTS.ordinal()] = 117;
        }
        catch (NoSuchFieldError var2_215) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.NOTE_BLOCK.ordinal()] = 26;
        }
        catch (NoSuchFieldError var2_216) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.OBSIDIAN.ordinal()] = 50;
        }
        catch (NoSuchFieldError var2_217) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PACKED_ICE.ordinal()] = 171;
        }
        catch (NoSuchFieldError var2_218) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PAINTING.ordinal()] = 238;
        }
        catch (NoSuchFieldError var2_219) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PAPER.ordinal()] = 256;
        }
        catch (NoSuchFieldError var2_220) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PISTON_BASE.ordinal()] = 34;
        }
        catch (NoSuchFieldError var2_221) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PISTON_EXTENSION.ordinal()] = 35;
        }
        catch (NoSuchFieldError var2_222) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PISTON_MOVING_PIECE.ordinal()] = 37;
        }
        catch (NoSuchFieldError var2_223) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PISTON_STICKY_BASE.ordinal()] = 30;
        }
        catch (NoSuchFieldError var2_224) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.POISONOUS_POTATO.ordinal()] = 311;
        }
        catch (NoSuchFieldError var2_225) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PORK.ordinal()] = 236;
        }
        catch (NoSuchFieldError var2_226) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PORTAL.ordinal()] = 91;
        }
        catch (NoSuchFieldError var2_227) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.POTATO.ordinal()] = 144;
        }
        catch (NoSuchFieldError var2_228) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.POTATO_ITEM.ordinal()] = 309;
        }
        catch (NoSuchFieldError var2_229) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.POTION.ordinal()] = 290;
        }
        catch (NoSuchFieldError var2_230) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.POWERED_MINECART.ordinal()] = 260;
        }
        catch (NoSuchFieldError var2_231) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.POWERED_RAIL.ordinal()] = 28;
        }
        catch (NoSuchFieldError var2_232) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PUMPKIN.ordinal()] = 87;
        }
        catch (NoSuchFieldError var2_233) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PUMPKIN_PIE.ordinal()] = 317;
        }
        catch (NoSuchFieldError var2_234) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PUMPKIN_SEEDS.ordinal()] = 278;
        }
        catch (NoSuchFieldError var2_235) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.PUMPKIN_STEM.ordinal()] = 106;
        }
        catch (NoSuchFieldError var2_236) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.QUARTZ.ordinal()] = 323;
        }
        catch (NoSuchFieldError var2_237) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.QUARTZ_BLOCK.ordinal()] = 157;
        }
        catch (NoSuchFieldError var2_238) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.QUARTZ_ORE.ordinal()] = 155;
        }
        catch (NoSuchFieldError var2_239) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.QUARTZ_STAIRS.ordinal()] = 158;
        }
        catch (NoSuchFieldError var2_240) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RAILS.ordinal()] = 67;
        }
        catch (NoSuchFieldError var2_241) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RAW_BEEF.ordinal()] = 280;
        }
        catch (NoSuchFieldError var2_242) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RAW_CHICKEN.ordinal()] = 282;
        }
        catch (NoSuchFieldError var2_243) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RAW_FISH.ordinal()] = 266;
        }
        catch (NoSuchFieldError var2_244) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_10.ordinal()] = 341;
        }
        catch (NoSuchFieldError var2_245) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_11.ordinal()] = 342;
        }
        catch (NoSuchFieldError var2_246) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_12.ordinal()] = 343;
        }
        catch (NoSuchFieldError var2_247) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_3.ordinal()] = 334;
        }
        catch (NoSuchFieldError var2_248) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_4.ordinal()] = 335;
        }
        catch (NoSuchFieldError var2_249) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_5.ordinal()] = 336;
        }
        catch (NoSuchFieldError var2_250) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_6.ordinal()] = 337;
        }
        catch (NoSuchFieldError var2_251) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_7.ordinal()] = 338;
        }
        catch (NoSuchFieldError var2_252) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_8.ordinal()] = 339;
        }
        catch (NoSuchFieldError var2_253) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RECORD_9.ordinal()] = 340;
        }
        catch (NoSuchFieldError var2_254) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE.ordinal()] = 248;
        }
        catch (NoSuchFieldError var2_255) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_BLOCK.ordinal()] = 154;
        }
        catch (NoSuchFieldError var2_256) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_COMPARATOR.ordinal()] = 321;
        }
        catch (NoSuchFieldError var2_257) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_COMPARATOR_OFF.ordinal()] = 151;
        }
        catch (NoSuchFieldError var2_258) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_COMPARATOR_ON.ordinal()] = 152;
        }
        catch (NoSuchFieldError var2_259) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_LAMP_OFF.ordinal()] = 125;
        }
        catch (NoSuchFieldError var2_260) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_LAMP_ON.ordinal()] = 126;
        }
        catch (NoSuchFieldError var2_261) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_ORE.ordinal()] = 74;
        }
        catch (NoSuchFieldError var2_262) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_TORCH_OFF.ordinal()] = 76;
        }
        catch (NoSuchFieldError var2_263) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_TORCH_ON.ordinal()] = 77;
        }
        catch (NoSuchFieldError var2_264) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.REDSTONE_WIRE.ordinal()] = 56;
        }
        catch (NoSuchFieldError var2_265) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RED_MUSHROOM.ordinal()] = 41;
        }
        catch (NoSuchFieldError var2_266) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.RED_ROSE.ordinal()] = 39;
        }
        catch (NoSuchFieldError var2_267) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.ROTTEN_FLESH.ordinal()] = 284;
        }
        catch (NoSuchFieldError var2_268) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SADDLE.ordinal()] = 246;
        }
        catch (NoSuchFieldError var2_269) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SAND.ordinal()] = 13;
        }
        catch (NoSuchFieldError var2_270) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SANDSTONE.ordinal()] = 25;
        }
        catch (NoSuchFieldError var2_271) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SANDSTONE_STAIRS.ordinal()] = 130;
        }
        catch (NoSuchFieldError var2_272) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SAPLING.ordinal()] = 7;
        }
        catch (NoSuchFieldError var2_273) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SEEDS.ordinal()] = 212;
        }
        catch (NoSuchFieldError var2_274) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SHEARS.ordinal()] = 276;
        }
        catch (NoSuchFieldError var2_275) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SIGN.ordinal()] = 240;
        }
        catch (NoSuchFieldError var2_276) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SIGN_POST.ordinal()] = 64;
        }
        catch (NoSuchFieldError var2_277) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SKULL.ordinal()] = 146;
        }
        catch (NoSuchFieldError var2_278) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SKULL_ITEM.ordinal()] = 314;
        }
        catch (NoSuchFieldError var2_279) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SLIME_BALL.ordinal()] = 258;
        }
        catch (NoSuchFieldError var2_280) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SMOOTH_BRICK.ordinal()] = 100;
        }
        catch (NoSuchFieldError var2_281) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SMOOTH_STAIRS.ordinal()] = 111;
        }
        catch (NoSuchFieldError var2_282) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SNOW.ordinal()] = 79;
        }
        catch (NoSuchFieldError var2_283) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SNOW_BALL.ordinal()] = 249;
        }
        catch (NoSuchFieldError var2_284) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SNOW_BLOCK.ordinal()] = 81;
        }
        catch (NoSuchFieldError var2_285) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SOIL.ordinal()] = 61;
        }
        catch (NoSuchFieldError var2_286) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SOUL_SAND.ordinal()] = 89;
        }
        catch (NoSuchFieldError var2_287) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SPECKLED_MELON.ordinal()] = 299;
        }
        catch (NoSuchFieldError var2_288) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SPIDER_EYE.ordinal()] = 292;
        }
        catch (NoSuchFieldError var2_289) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SPONGE.ordinal()] = 20;
        }
        catch (NoSuchFieldError var2_290) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SPRUCE_WOOD_STAIRS.ordinal()] = 136;
        }
        catch (NoSuchFieldError var2_291) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STAINED_CLAY.ordinal()] = 161;
        }
        catch (NoSuchFieldError var2_292) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STAINED_GLASS.ordinal()] = 97;
        }
        catch (NoSuchFieldError var2_293) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STAINED_GLASS_PANE.ordinal()] = 162;
        }
        catch (NoSuchFieldError var2_294) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STATIONARY_LAVA.ordinal()] = 12;
        }
        catch (NoSuchFieldError var2_295) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STATIONARY_WATER.ordinal()] = 10;
        }
        catch (NoSuchFieldError var2_296) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STEP.ordinal()] = 45;
        }
        catch (NoSuchFieldError var2_297) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STICK.ordinal()] = 197;
        }
        catch (NoSuchFieldError var2_298) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE.ordinal()] = 2;
        }
        catch (NoSuchFieldError var2_299) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_AXE.ordinal()] = 192;
        }
        catch (NoSuchFieldError var2_300) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_BUTTON.ordinal()] = 78;
        }
        catch (NoSuchFieldError var2_301) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_HOE.ordinal()] = 208;
        }
        catch (NoSuchFieldError var2_302) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_PICKAXE.ordinal()] = 191;
        }
        catch (NoSuchFieldError var2_303) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_PLATE.ordinal()] = 71;
        }
        catch (NoSuchFieldError var2_304) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_SPADE.ordinal()] = 190;
        }
        catch (NoSuchFieldError var2_305) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STONE_SWORD.ordinal()] = 189;
        }
        catch (NoSuchFieldError var2_306) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STORAGE_MINECART.ordinal()] = 259;
        }
        catch (NoSuchFieldError var2_307) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.STRING.ordinal()] = 204;
        }
        catch (NoSuchFieldError var2_308) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SUGAR.ordinal()] = 270;
        }
        catch (NoSuchFieldError var2_309) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SUGAR_CANE.ordinal()] = 255;
        }
        catch (NoSuchFieldError var2_310) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SUGAR_CANE_BLOCK.ordinal()] = 84;
        }
        catch (NoSuchFieldError var2_311) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.SULPHUR.ordinal()] = 206;
        }
        catch (NoSuchFieldError var2_312) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.THIN_GLASS.ordinal()] = 104;
        }
        catch (NoSuchFieldError var2_313) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.TNT.ordinal()] = 47;
        }
        catch (NoSuchFieldError var2_314) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.TORCH.ordinal()] = 51;
        }
        catch (NoSuchFieldError var2_315) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.TRAPPED_CHEST.ordinal()] = 148;
        }
        catch (NoSuchFieldError var2_316) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.TRAP_DOOR.ordinal()] = 98;
        }
        catch (NoSuchFieldError var2_317) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.TRIPWIRE.ordinal()] = 134;
        }
        catch (NoSuchFieldError var2_318) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.TRIPWIRE_HOOK.ordinal()] = 133;
        }
        catch (NoSuchFieldError var2_319) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.VINE.ordinal()] = 108;
        }
        catch (NoSuchFieldError var2_320) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WALL_SIGN.ordinal()] = 69;
        }
        catch (NoSuchFieldError var2_321) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WATCH.ordinal()] = 264;
        }
        catch (NoSuchFieldError var2_322) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WATER.ordinal()] = 9;
        }
        catch (NoSuchFieldError var2_323) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WATER_BUCKET.ordinal()] = 243;
        }
        catch (NoSuchFieldError var2_324) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WATER_LILY.ordinal()] = 113;
        }
        catch (NoSuchFieldError var2_325) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WEB.ordinal()] = 31;
        }
        catch (NoSuchFieldError var2_326) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WHEAT.ordinal()] = 213;
        }
        catch (NoSuchFieldError var2_327) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD.ordinal()] = 6;
        }
        catch (NoSuchFieldError var2_328) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOODEN_DOOR.ordinal()] = 65;
        }
        catch (NoSuchFieldError var2_329) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_AXE.ordinal()] = 188;
        }
        catch (NoSuchFieldError var2_330) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_BUTTON.ordinal()] = 145;
        }
        catch (NoSuchFieldError var2_331) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_DOOR.ordinal()] = 241;
        }
        catch (NoSuchFieldError var2_332) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_DOUBLE_STEP.ordinal()] = 127;
        }
        catch (NoSuchFieldError var2_333) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_HOE.ordinal()] = 207;
        }
        catch (NoSuchFieldError var2_334) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_PICKAXE.ordinal()] = 187;
        }
        catch (NoSuchFieldError var2_335) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_PLATE.ordinal()] = 73;
        }
        catch (NoSuchFieldError var2_336) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_SPADE.ordinal()] = 186;
        }
        catch (NoSuchFieldError var2_337) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_STAIRS.ordinal()] = 54;
        }
        catch (NoSuchFieldError var2_338) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_STEP.ordinal()] = 128;
        }
        catch (NoSuchFieldError var2_339) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOD_SWORD.ordinal()] = 185;
        }
        catch (NoSuchFieldError var2_340) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WOOL.ordinal()] = 36;
        }
        catch (NoSuchFieldError var2_341) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WORKBENCH.ordinal()] = 59;
        }
        catch (NoSuchFieldError var2_342) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.WRITTEN_BOOK.ordinal()] = 304;
        }
        catch (NoSuchFieldError var2_343) {
            // empty catch block
        }
        try {
            $switch_TABLE$org$bukkit$Material2[Material.YELLOW_FLOWER.ordinal()] = 38;
        }
        catch (NoSuchFieldError var2_344) {
            // empty catch block
        }
        $SWITCH_TABLE$org$bukkit$Material = $switch_TABLE$org$bukkit$Material2;
        return $SWITCH_TABLE$org$bukkit$Material;
    }
}

