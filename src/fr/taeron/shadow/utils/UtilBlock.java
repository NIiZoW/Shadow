package fr.taeron.shadow.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.bukkit.material.Gate;

@SuppressWarnings("deprecation")
public class UtilBlock
{
    public static HashSet<Byte> blockPassSet;
    public static HashSet<Byte> blockAirFoliageSet;
    public static HashSet<Byte> fullSolid;
    public static HashSet<Byte> blockUseSet;
    
    static {
        UtilBlock.blockPassSet = new HashSet<Byte>();
        UtilBlock.blockAirFoliageSet = new HashSet<Byte>();
        UtilBlock.fullSolid = new HashSet<Byte>();
        UtilBlock.blockUseSet = new HashSet<Byte>();
    }
    
    public static Block getLowestBlockAt(final Location Location) {
        Block Block = Location.getWorld().getBlockAt((int)Location.getX(), 0, (int)Location.getZ());
        if (Block == null || Block.getType().equals((Object)Material.AIR)) {
            Block = Location.getBlock();
            for (int y = (int)Location.getY(); y > 0; --y) {
                final Block Current = Location.getWorld().getBlockAt((int)Location.getX(), y, (int)Location.getZ());
                final Block Below = Current.getLocation().subtract(0.0, 1.0, 0.0).getBlock();
                if (Below == null || Below.getType().equals((Object)Material.AIR)) {
                    Block = Current;
                }
            }
        }
        return Block;
    }
    
    public static boolean containsBlock(final Location Location, final Material Material) {
        for (int y = 0; y < 256; ++y) {
            final Block Current = Location.getWorld().getBlockAt((int)Location.getX(), y, (int)Location.getZ());
            if (Current != null && Current.getType().equals((Object)Material)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsBlock(final Location Location) {
        for (int y = 0; y < 256; ++y) {
            final Block Current = Location.getWorld().getBlockAt((int)Location.getX(), y, (int)Location.getZ());
            if (Current != null && !Current.getType().equals((Object)Material.AIR)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsBlockBelow(final Location Location) {
        for (int y = 0; y < (int)Location.getY(); ++y) {
            final Block Current = Location.getWorld().getBlockAt((int)Location.getX(), y, (int)Location.getZ());
            if (Current != null && !Current.getType().equals((Object)Material.AIR)) {
                return true;
            }
        }
        return false;
    }
    
    public static ArrayList<Block> getBlocksAroundCenter(final Location loc, final int radius) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; ++x) {
            for (int y = loc.getBlockY() - radius; y <= loc.getBlockY() + radius; ++y) {
                for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; ++z) {
                    final Location l = new Location(loc.getWorld(), (double)x, (double)y, (double)z);
                    if (l.distance(loc) <= radius) {
                        blocks.add(l.getBlock());
                    }
                }
            }
        }
        return blocks;
    }
    
    public static Location StringToLocation(final String Key) {
        final String[] Args = Key.split(",");
        final World World = Bukkit.getWorld(Args[0]);
        final double X = Double.parseDouble(Args[1]);
        final double Y = Double.parseDouble(Args[2]);
        final double Z = Double.parseDouble(Args[3]);
        final float Pitch = Float.parseFloat(Args[4]);
        final float Yaw = Float.parseFloat(Args[5]);
        return new Location(World, X, Y, Z, Pitch, Yaw);
    }
    
    public static String LocationToString(final Location Location) {
        return String.valueOf(String.valueOf(String.valueOf(Location.getWorld().getName()))) + "," + Location.getX() + "," + Location.getY() + "," + Location.getZ() + "," + Location.getPitch() + "," + Location.getYaw();
    }
    
	public static boolean solid(final Block block) {
        return block != null && solid(block.getTypeId());
    }
    
    public static boolean solid(final int block) {
        return solid((byte)block);
    }
    
    public static boolean solid(final byte block) {
        if (UtilBlock.blockPassSet.isEmpty()) {
            UtilBlock.blockPassSet.add((byte)0);
            UtilBlock.blockPassSet.add((byte)6);
            UtilBlock.blockPassSet.add((byte)8);
            UtilBlock.blockPassSet.add((byte)9);
            UtilBlock.blockPassSet.add((byte)10);
            UtilBlock.blockPassSet.add((byte)11);
            UtilBlock.blockPassSet.add((byte)26);
            UtilBlock.blockPassSet.add((byte)27);
            UtilBlock.blockPassSet.add((byte)28);
            UtilBlock.blockPassSet.add((byte)30);
            UtilBlock.blockPassSet.add((byte)31);
            UtilBlock.blockPassSet.add((byte)32);
            UtilBlock.blockPassSet.add((byte)37);
            UtilBlock.blockPassSet.add((byte)38);
            UtilBlock.blockPassSet.add((byte)39);
            UtilBlock.blockPassSet.add((byte)40);
            UtilBlock.blockPassSet.add((byte)50);
            UtilBlock.blockPassSet.add((byte)51);
            UtilBlock.blockPassSet.add((byte)55);
            UtilBlock.blockPassSet.add((byte)59);
            UtilBlock.blockPassSet.add((byte)63);
            UtilBlock.blockPassSet.add((byte)64);
            UtilBlock.blockPassSet.add((byte)65);
            UtilBlock.blockPassSet.add((byte)66);
            UtilBlock.blockPassSet.add((byte)68);
            UtilBlock.blockPassSet.add((byte)69);
            UtilBlock.blockPassSet.add((byte)70);
            UtilBlock.blockPassSet.add((byte)71);
            UtilBlock.blockPassSet.add((byte)72);
            UtilBlock.blockPassSet.add((byte)75);
            UtilBlock.blockPassSet.add((byte)76);
            UtilBlock.blockPassSet.add((byte)77);
            UtilBlock.blockPassSet.add((byte)78);
            UtilBlock.blockPassSet.add((byte)83);
            UtilBlock.blockPassSet.add((byte)90);
            UtilBlock.blockPassSet.add((byte)92);
            UtilBlock.blockPassSet.add((byte)93);
            UtilBlock.blockPassSet.add((byte)94);
            UtilBlock.blockPassSet.add((byte)96);
            UtilBlock.blockPassSet.add((byte)101);
            UtilBlock.blockPassSet.add((byte)102);
            UtilBlock.blockPassSet.add((byte)104);
            UtilBlock.blockPassSet.add((byte)105);
            UtilBlock.blockPassSet.add((byte)106);
            UtilBlock.blockPassSet.add((byte)107);
            UtilBlock.blockPassSet.add((byte)111);
            UtilBlock.blockPassSet.add((byte)115);
            UtilBlock.blockPassSet.add((byte)116);
            UtilBlock.blockPassSet.add((byte)117);
            UtilBlock.blockPassSet.add((byte)118);
            UtilBlock.blockPassSet.add((byte)119);
            UtilBlock.blockPassSet.add((byte)120);
            UtilBlock.blockPassSet.add((byte)(-85));
        }
        return !UtilBlock.blockPassSet.contains(block);
    }
    
	public static boolean airFoliage(final Block block) {
        return block != null && airFoliage(block.getTypeId());
    }
    
    public static boolean airFoliage(final int block) {
        return airFoliage((byte)block);
    }
    
    public static boolean airFoliage(final byte block) {
        if (UtilBlock.blockAirFoliageSet.isEmpty()) {
            UtilBlock.blockAirFoliageSet.add((byte)0);
            UtilBlock.blockAirFoliageSet.add((byte)6);
            UtilBlock.blockAirFoliageSet.add((byte)31);
            UtilBlock.blockAirFoliageSet.add((byte)32);
            UtilBlock.blockAirFoliageSet.add((byte)37);
            UtilBlock.blockAirFoliageSet.add((byte)38);
            UtilBlock.blockAirFoliageSet.add((byte)39);
            UtilBlock.blockAirFoliageSet.add((byte)40);
            UtilBlock.blockAirFoliageSet.add((byte)51);
            UtilBlock.blockAirFoliageSet.add((byte)59);
            UtilBlock.blockAirFoliageSet.add((byte)104);
            UtilBlock.blockAirFoliageSet.add((byte)105);
            UtilBlock.blockAirFoliageSet.add((byte)115);
            UtilBlock.blockAirFoliageSet.add((byte)(-115));
            UtilBlock.blockAirFoliageSet.add((byte)(-114));
        }
        return UtilBlock.blockAirFoliageSet.contains(block);
    }
    
	public static boolean fullSolid(final Block block) {
        return block != null && fullSolid(block.getTypeId());
    }
    
    public static boolean fullSolid(final int block) {
        return fullSolid((byte)block);
    }
    
    public static boolean fullSolid(final byte block) {
        if (UtilBlock.fullSolid.isEmpty()) {
            UtilBlock.fullSolid.add((byte)1);
            UtilBlock.fullSolid.add((byte)2);
            UtilBlock.fullSolid.add((byte)3);
            UtilBlock.fullSolid.add((byte)4);
            UtilBlock.fullSolid.add((byte)5);
            UtilBlock.fullSolid.add((byte)7);
            UtilBlock.fullSolid.add((byte)12);
            UtilBlock.fullSolid.add((byte)13);
            UtilBlock.fullSolid.add((byte)14);
            UtilBlock.fullSolid.add((byte)15);
            UtilBlock.fullSolid.add((byte)16);
            UtilBlock.fullSolid.add((byte)17);
            UtilBlock.fullSolid.add((byte)19);
            UtilBlock.fullSolid.add((byte)20);
            UtilBlock.fullSolid.add((byte)21);
            UtilBlock.fullSolid.add((byte)22);
            UtilBlock.fullSolid.add((byte)23);
            UtilBlock.fullSolid.add((byte)24);
            UtilBlock.fullSolid.add((byte)25);
            UtilBlock.fullSolid.add((byte)29);
            UtilBlock.fullSolid.add((byte)33);
            UtilBlock.fullSolid.add((byte)35);
            UtilBlock.fullSolid.add((byte)41);
            UtilBlock.fullSolid.add((byte)42);
            UtilBlock.fullSolid.add((byte)43);
            UtilBlock.fullSolid.add((byte)44);
            UtilBlock.fullSolid.add((byte)45);
            UtilBlock.fullSolid.add((byte)46);
            UtilBlock.fullSolid.add((byte)47);
            UtilBlock.fullSolid.add((byte)48);
            UtilBlock.fullSolid.add((byte)49);
            UtilBlock.fullSolid.add((byte)56);
            UtilBlock.fullSolid.add((byte)57);
            UtilBlock.fullSolid.add((byte)58);
            UtilBlock.fullSolid.add((byte)60);
            UtilBlock.fullSolid.add((byte)61);
            UtilBlock.fullSolid.add((byte)62);
            UtilBlock.fullSolid.add((byte)73);
            UtilBlock.fullSolid.add((byte)74);
            UtilBlock.fullSolid.add((byte)79);
            UtilBlock.fullSolid.add((byte)80);
            UtilBlock.fullSolid.add((byte)82);
            UtilBlock.fullSolid.add((byte)84);
            UtilBlock.fullSolid.add((byte)86);
            UtilBlock.fullSolid.add((byte)87);
            UtilBlock.fullSolid.add((byte)88);
            UtilBlock.fullSolid.add((byte)89);
            UtilBlock.fullSolid.add((byte)91);
            UtilBlock.fullSolid.add((byte)95);
            UtilBlock.fullSolid.add((byte)97);
            UtilBlock.fullSolid.add((byte)98);
            UtilBlock.fullSolid.add((byte)99);
            UtilBlock.fullSolid.add((byte)100);
            UtilBlock.fullSolid.add((byte)103);
            UtilBlock.fullSolid.add((byte)110);
            UtilBlock.fullSolid.add((byte)112);
            UtilBlock.fullSolid.add((byte)121);
            UtilBlock.fullSolid.add((byte)123);
            UtilBlock.fullSolid.add((byte)124);
            UtilBlock.fullSolid.add((byte)125);
            UtilBlock.fullSolid.add((byte)126);
            UtilBlock.fullSolid.add((byte)(-127));
            UtilBlock.fullSolid.add((byte)(-123));
            UtilBlock.fullSolid.add((byte)(-119));
            UtilBlock.fullSolid.add((byte)(-118));
            UtilBlock.fullSolid.add((byte)(-104));
            UtilBlock.fullSolid.add((byte)(-103));
            UtilBlock.fullSolid.add((byte)(-101));
            UtilBlock.fullSolid.add((byte)(-98));
        }
        return UtilBlock.fullSolid.contains(block);
    }
    
	public static boolean usable(final Block block) {
        return block != null && usable(block.getTypeId());
    }
    
    public static boolean usable(final int block) {
        return usable((byte)block);
    }
    
    public static boolean usable(final byte block) {
        if (UtilBlock.blockUseSet.isEmpty()) {
            UtilBlock.blockUseSet.add((byte)23);
            UtilBlock.blockUseSet.add((byte)26);
            UtilBlock.blockUseSet.add((byte)33);
            UtilBlock.blockUseSet.add((byte)47);
            UtilBlock.blockUseSet.add((byte)54);
            UtilBlock.blockUseSet.add((byte)58);
            UtilBlock.blockUseSet.add((byte)61);
            UtilBlock.blockUseSet.add((byte)62);
            UtilBlock.blockUseSet.add((byte)64);
            UtilBlock.blockUseSet.add((byte)69);
            UtilBlock.blockUseSet.add((byte)71);
            UtilBlock.blockUseSet.add((byte)77);
            UtilBlock.blockUseSet.add((byte)93);
            UtilBlock.blockUseSet.add((byte)94);
            UtilBlock.blockUseSet.add((byte)96);
            UtilBlock.blockUseSet.add((byte)107);
            UtilBlock.blockUseSet.add((byte)116);
            UtilBlock.blockUseSet.add((byte)117);
            UtilBlock.blockUseSet.add((byte)(-126));
            UtilBlock.blockUseSet.add((byte)(-111));
            UtilBlock.blockUseSet.add((byte)(-110));
            UtilBlock.blockUseSet.add((byte)(-102));
            UtilBlock.blockUseSet.add((byte)(-98));
        }
        return UtilBlock.blockUseSet.contains(block);
    }
    
    public static HashMap<Block, Double> getInRadius(final Location loc, final double dR) {
        return getInRadius(loc, dR, 999.0);
    }
    
    public static HashMap<Block, Double> getInRadius(final Location loc, final double dR, final double heightLimit) {
        final HashMap<Block, Double> blockList = new HashMap<Block, Double>();
        for (int iR = (int)dR + 1, x = -iR; x <= iR; ++x) {
            for (int z = -iR; z <= iR; ++z) {
                for (int y = -iR; y <= iR; ++y) {
                    if (Math.abs(y) <= heightLimit) {
                        final Block curBlock = loc.getWorld().getBlockAt((int)(loc.getX() + x), (int)(loc.getY() + y), (int)(loc.getZ() + z));
                        final double offset = UtilMath.offset(loc, curBlock.getLocation().add(0.5, 0.5, 0.5));
                        if (offset <= dR) {
                            blockList.put(curBlock, 1.0 - offset / dR);
                        }
                    }
                }
            }
        }
        return blockList;
    }
    
    public static HashMap<Block, Double> getInRadius(final Block block, final double dR) {
        final HashMap<Block, Double> blockList = new HashMap<Block, Double>();
        for (int iR = (int)dR + 1, x = -iR; x <= iR; ++x) {
            for (int z = -iR; z <= iR; ++z) {
                for (int y = -iR; y <= iR; ++y) {
                    final Block curBlock = block.getRelative(x, y, z);
                    final double offset = UtilMath.offset(block.getLocation(), curBlock.getLocation());
                    if (offset <= dR) {
                        blockList.put(curBlock, 1.0 - offset / dR);
                    }
                }
            }
        }
        return blockList;
    }
    
	public static boolean isBlock(final ItemStack item) {
        return item != null && item.getTypeId() > 0 && item.getTypeId() < 256;
    }
    
    public static Block getHighest(final Location locaton) {
        return getHighest(locaton, null);
    }
    
    public static Block getHighest(final Location location, final HashSet<Material> ignore) {
        location.setY(0.0);
        for (int i = 0; i < 256; ++i) {
            location.setY((double)(256 - i));
            if (solid(location.getBlock())) {
                break;
            }
        }
        return location.getBlock().getRelative(BlockFace.UP);
    }
    
    public static boolean isInAir(final Player player) {
        boolean nearBlocks = false;
        for (final Block block : getSurrounding(player.getLocation().getBlock(), true)) {
            if (block.getType() != Material.AIR) {
                nearBlocks = true;
                break;
            }
        }
        return nearBlocks;
    }
    
    public static ArrayList<Block> getSurrounding(final Block block, final boolean diagonals) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        if (diagonals) {
            for (int x = -1; x <= 1; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        if (x != 0 || y != 0 || z != 0) {
                            blocks.add(block.getRelative(x, y, z));
                        }
                    }
                }
            }
        }
        else {
            blocks.add(block.getRelative(BlockFace.UP));
            blocks.add(block.getRelative(BlockFace.DOWN));
            blocks.add(block.getRelative(BlockFace.NORTH));
            blocks.add(block.getRelative(BlockFace.SOUTH));
            blocks.add(block.getRelative(BlockFace.EAST));
            blocks.add(block.getRelative(BlockFace.WEST));
        }
        return blocks;
    }
    
    public static ArrayList<Block> getSurroundingXZ(final Block block) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(block.getRelative(BlockFace.NORTH));
        blocks.add(block.getRelative(BlockFace.NORTH_EAST));
        blocks.add(block.getRelative(BlockFace.NORTH_WEST));
        blocks.add(block.getRelative(BlockFace.SOUTH));
        blocks.add(block.getRelative(BlockFace.SOUTH_EAST));
        blocks.add(block.getRelative(BlockFace.SOUTH_WEST));
        blocks.add(block.getRelative(BlockFace.EAST));
        blocks.add(block.getRelative(BlockFace.WEST));
        return blocks;
    }
    
    public static String serializeLocation(final Location location) {
        final int X = (int)location.getX();
        final int Y = (int)location.getY();
        final int Z = (int)location.getZ();
        final int P = (int)location.getPitch();
        final int Yaw = (int)location.getYaw();
        return new String(String.valueOf(String.valueOf(String.valueOf(location.getWorld().getName()))) + "," + X + "," + Y + "," + Z + "," + P + "," + Yaw);
    }
    
    public static Location deserializeLocation(final String string) {
        if (string == null) {
            return null;
        }
        final String[] parts = string.split(",");
        final World world = Bukkit.getServer().getWorld(parts[0]);
        final Double LX = Double.parseDouble(parts[1]);
        final Double LY = Double.parseDouble(parts[2]);
        final Double LZ = Double.parseDouble(parts[3]);
        final Float P = Float.parseFloat(parts[4]);
        final Float Y = Float.parseFloat(parts[5]);
        final Location result = new Location(world, (double)LX, (double)LY, (double)LZ);
        result.setPitch((float)P);
        result.setYaw((float)Y);
        return result;
    }
    
    public static boolean isVisible(final Block block) {
        for (final Block other : getSurrounding(block, false)) {
            if (!other.getType().isOccluding()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isGate(final Location location) {
        return location.getBlock().getType().getData() == Gate.class;
    }
    
	public static boolean isDoor(final Location location) {
        return location.getBlock().getType().getData() == Door.class;
    }
    
    public static BlockFace getDirection(final Location from, final Location to) {
        final int xdiff = to.getBlockX() - from.getBlockX();
        final int zdiff = to.getBlockZ() - from.getBlockZ();
        if (xdiff == 0 && zdiff == 0) {
            return BlockFace.SELF;
        }
        if (xdiff == 0) {
            if (zdiff < 0) {
                return BlockFace.NORTH;
            }
            if (zdiff > 0) {
                return BlockFace.SOUTH;
            }
        }
        else if (zdiff == 0) {
            if (xdiff < 0) {
                return BlockFace.WEST;
            }
            if (xdiff > 0) {
                return BlockFace.EAST;
            }
        }
        return BlockFace.SELF;
    }
    
	public static Block getBottomDoor(final Block block) {
        Block down;
        if ((block.getData() & 0x8) == 0x8) {
            down = block.getRelative(BlockFace.DOWN);
        }
        else {
            down = block;
        }
        return down;
    }
    
	public static void updateDoor(final Player player, final Block block) {
        Block up;
        Block down;
        if ((block.getData() & 0x8) == 0x8) {
            up = block;
            down = up.getRelative(BlockFace.DOWN);
        }
        else {
            down = block;
            up = block.getRelative(BlockFace.UP);
        }
        player.sendBlockChange(down.getLocation(), down.getTypeId(), down.getData());
        player.sendBlockChange(up.getLocation(), up.getTypeId(), up.getData());
    }
    
	public static BlockFace getDoorSide(final Block block) {
        Block up;
        Block down;
        if ((block.getData() & 0x8) == 0x8) {
            up = block;
            down = up.getRelative(BlockFace.DOWN);
        }
        else {
            down = block;
            up = block.getRelative(BlockFace.UP);
        }
        final byte data = (byte)(down.getData() & 0x3);
        final boolean isOpen = (down.getData() & 0x4) == 0x4;
        final boolean hinge = (up.getData() & 0x1) == 0x1;
        switch (data) {
            case 0: {
                if (!isOpen) {
                    return BlockFace.WEST;
                }
                if (hinge) {
                    return BlockFace.SOUTH;
                }
                return BlockFace.NORTH;
            }
            case 1: {
                if (!isOpen) {
                    return BlockFace.NORTH;
                }
                if (hinge) {
                    return BlockFace.WEST;
                }
                return BlockFace.EAST;
            }
            case 2: {
                if (!isOpen) {
                    return BlockFace.EAST;
                }
                if (hinge) {
                    return BlockFace.NORTH;
                }
                return BlockFace.SOUTH;
            }
            case 3: {
                if (!isOpen) {
                    return BlockFace.SOUTH;
                }
                if (hinge) {
                    return BlockFace.EAST;
                }
                return BlockFace.WEST;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
}
