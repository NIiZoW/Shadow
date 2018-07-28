/*
 * Decompiled with CFR 0_118.
 */
package fr.taeron.shadow.utils;

import fr.taeron.shadow.utils.UtilMath;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilTime {
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static long nowlong() {
        return System.currentTimeMillis();
    }

    public static String when(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return sdf.format(time);
    }

    public static long a(String a) {
        if (a.endsWith("s")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 1000;
        }
        if (a.endsWith("m")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 60000;
        }
        if (a.endsWith("h")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 3600000;
        }
        if (a.endsWith("d")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 86400000;
        }
        if (a.endsWith("m")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 2592000000L;
        }
        if (a.endsWith("y")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 31104000000L;
        }
        return -1;
    }

    public static String date() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static String getTime(int time) {
        Date timeDiff = new Date();
        timeDiff.setTime(time * 1000);
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        String eventTimeDisplay = timeFormat.format(timeDiff);
        return eventTimeDisplay;
    }

    public static String since(long epoch) {
        return "Took " + UtilTime.convertString(System.currentTimeMillis() - epoch, 1, TimeUnit.FIT) + ".";
    }

    public static double convert(long time, int trim, TimeUnit type) {
        if (type == TimeUnit.FIT) {
            type = time < 60000 ? TimeUnit.SECONDS : (time < 3600000 ? TimeUnit.MINUTES : (time < 86400000 ? TimeUnit.HOURS : TimeUnit.DAYS));
        }
        if (type == TimeUnit.DAYS) {
            return UtilMath.trim(trim, (double)time / 8.64E7);
        }
        if (type == TimeUnit.HOURS) {
            return UtilMath.trim(trim, (double)time / 3600000.0);
        }
        if (type == TimeUnit.MINUTES) {
            return UtilMath.trim(trim, (double)time / 60000.0);
        }
        if (type == TimeUnit.SECONDS) {
            return UtilMath.trim(trim, (double)time / 1000.0);
        }
        return UtilMath.trim(trim, time);
    }

    public static String MakeStr(long time) {
        return UtilTime.convertString(time, 1, TimeUnit.FIT);
    }

    public static String MakeStr(long time, int trim) {
        return UtilTime.convertString(time, trim, TimeUnit.FIT);
    }

    public static String convertString(long time, int trim, TimeUnit type) {
        if (time == -1) {
            return "Permanent";
        }
        if (type == TimeUnit.FIT) {
            type = time < 60000 ? TimeUnit.SECONDS : (time < 3600000 ? TimeUnit.MINUTES : (time < 86400000 ? TimeUnit.HOURS : TimeUnit.DAYS));
        }
        if (type == TimeUnit.DAYS) {
            return String.valueOf(String.valueOf(String.valueOf(UtilMath.trim(trim, (double)time / 8.64E7)))) + " Days";
        }
        if (type == TimeUnit.HOURS) {
            return String.valueOf(String.valueOf(String.valueOf(UtilMath.trim(trim, (double)time / 3600000.0)))) + " Hours";
        }
        if (type == TimeUnit.MINUTES) {
            return String.valueOf(String.valueOf(String.valueOf(UtilMath.trim(trim, (double)time / 60000.0)))) + " Minutes";
        }
        if (type == TimeUnit.SECONDS) {
            return String.valueOf(String.valueOf(String.valueOf(UtilMath.trim(trim, (double)time / 1000.0)))) + " Seconds";
        }
        return String.valueOf(String.valueOf(String.valueOf(UtilMath.trim(trim, time)))) + " Milliseconds";
    }

    public static boolean elapsed(long from, long required) {
        if (System.currentTimeMillis() - from > required) {
            return true;
        }
        return false;
    }

    public static long elapsed(long starttime) {
        return System.currentTimeMillis() - starttime;
    }

    public static long left(long start, long required) {
        return required + start - System.currentTimeMillis();
    }

    public static enum TimeUnit {
        FIT("FIT", 0), 
        DAYS("DAYS", 1), 
        HOURS("HOURS", 2), 
        MINUTES("MINUTES", 3), 
        SECONDS("SECONDS", 4), 
        MILLISECONDS("MILLISECONDS", 5);
        

        private TimeUnit(String string2, int n2) {
        }
    }

}

