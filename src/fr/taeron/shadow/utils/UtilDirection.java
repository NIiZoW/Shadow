/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.BlockFace
 */
package fr.taeron.shadow.utils;

import org.bukkit.block.BlockFace;

public class UtilDirection {
    public static final BlockFace[] axis = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    public static final BlockFace[] radial = new BlockFace[]{BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};

    public static BlockFace yawToFace(float yaw) {
        return UtilDirection.yawToFace(yaw, false);
    }

    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) {
            return radial[Math.round(yaw / 45.0f) & 7];
        }
        return axis[Math.round(yaw / 90.0f) & 3];
    }
}

