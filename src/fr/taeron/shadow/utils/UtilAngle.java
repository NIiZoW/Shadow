/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 */
package fr.taeron.shadow.utils;

import org.bukkit.Location;

public class UtilAngle {
    public static double getDirection(Location from, Location to) {
        if (from == null || to == null) {
            return 0.0;
        }
        double difX = to.getX() - from.getX();
        double difZ = to.getZ() - from.getZ();
        return UtilAngle.wrapAngleTo180_float((float)(Math.atan2(difZ, difX) * 180.0 / 3.141592653589793) - 90.0f);
    }

    public static double getDistance(double p1, double p2, double p3, double p4) {
        double delta1 = p3 - p1;
        double delta2 = p4 - p2;
        return Math.sqrt(delta1 * delta1 + delta2 * delta2);
    }

    public static float wrapAngleTo180_float(float value) {
        if ((value %= 360.0f) >= 180.0f) {
            value -= 360.0f;
        }
        if (value < -180.0f) {
            value += 360.0f;
        }
        return value;
    }

    public static float fixRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = UtilAngle.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < - p_70663_3_) {
            var4 = - p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
}

