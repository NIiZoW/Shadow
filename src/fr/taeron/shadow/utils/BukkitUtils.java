
package fr.taeron.shadow.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class BukkitUtils {
    public static final String STRAIGHT_LINE_DEFAULT;
    private static final ImmutableMap<Object, Object> CHAT_DYE_COLOUR_MAP;
    private static final List<PotionEffectType> DEBUFF_TYPES;
    private static final String STRAIGHT_LINE_TEMPLATE;

    static {
        DEBUFF_TYPES = new ArrayList<PotionEffectType>();
        STRAIGHT_LINE_TEMPLATE = String.valueOf(ChatColor.STRIKETHROUGH.toString()) + Strings.repeat((String)"-", (int)256);
        STRAIGHT_LINE_DEFAULT = STRAIGHT_LINE_TEMPLATE.substring(0, 55);
        CHAT_DYE_COLOUR_MAP = ImmutableMap.builder().put((Object)ChatColor.AQUA, (Object)DyeColor.LIGHT_BLUE).put((Object)ChatColor.BLACK, (Object)DyeColor.BLACK).put((Object)ChatColor.BLUE, (Object)DyeColor.LIGHT_BLUE).put((Object)ChatColor.DARK_AQUA, (Object)DyeColor.CYAN).put((Object)ChatColor.DARK_BLUE, (Object)DyeColor.BLUE).put((Object)ChatColor.DARK_GRAY, (Object)DyeColor.GRAY).put((Object)ChatColor.DARK_GREEN, (Object)DyeColor.GREEN).put((Object)ChatColor.DARK_PURPLE, (Object)DyeColor.PURPLE).put((Object)ChatColor.DARK_RED, (Object)DyeColor.RED).put((Object)ChatColor.GOLD, (Object)DyeColor.ORANGE).put((Object)ChatColor.GRAY, (Object)DyeColor.SILVER).put((Object)ChatColor.GREEN, (Object)DyeColor.LIME).put((Object)ChatColor.LIGHT_PURPLE, (Object)DyeColor.MAGENTA).put((Object)ChatColor.RED, (Object)DyeColor.RED).put((Object)ChatColor.WHITE, (Object)DyeColor.WHITE).put((Object)ChatColor.YELLOW, (Object)DyeColor.YELLOW).build();
    }

    public static int countColoursUsed(String id, boolean ignoreDuplicates) {
        ChatColor[] values = ChatColor.values();
        ArrayList<Character> charList = new ArrayList<Character>(values.length);
        ChatColor[] arrchatColor = values;
        int n = arrchatColor.length;
        int n2 = 0;
        while (n2 < n) {
            ChatColor colour = arrchatColor[n2];
            charList.add(Character.valueOf(colour.getChar()));
            ++n2;
        }
        int count = 0;
        HashSet<ChatColor> found = new HashSet<ChatColor>();
        int i = 1;
        while (i < id.length()) {
            if (charList.contains(Character.valueOf(id.charAt(i))) && id.charAt(i - 1) == '&' && (found.add(ChatColor.getByChar((char)id.charAt(i))) || ignoreDuplicates)) {
                ++count;
            }
            ++i;
        }
        return count;
    }

    public static List<String> getCompletions(String[] args, List<String> input) {
        return BukkitUtils.getCompletions(args, input, 80);
    }

    public static List<String> getCompletions(String[] args, List<String> input, int limit) {
        Preconditions.checkNotNull((Object)args);
        Preconditions.checkArgument((boolean)(args.length != 0));
        String argument = args[args.length - 1];
        return input.stream().filter(string2 -> string2.regionMatches(true, 0, argument, 0, argument.length())).limit(limit).collect(Collectors.toList());
    }

    public static String getDisplayName(CommandSender sender) {
        Preconditions.checkNotNull((Object)sender);
        return sender instanceof Player ? ((Player)sender).getDisplayName() : sender.getName();
    }

    public static long getIdleTime(Player player) {
        Preconditions.checkNotNull((Object)player);
        long idleTime = ((CraftPlayer)player).getHandle().x();
        return idleTime > 0 ? MinecraftServer.ar() - idleTime : 0;
    }

    public static DyeColor toDyeColor(ChatColor colour) {
        return (DyeColor)CHAT_DYE_COLOUR_MAP.get((Object)colour);
    }

    @SuppressWarnings("deprecation")
	public static Player getFinalAttacker(EntityDamageEvent ede, boolean ignoreSelf) {
        Player attacker = null;
        if (ede instanceof EntityDamageByEntityEvent) {
            LivingEntity shooter;
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)ede;
            Entity damager = event.getDamager();
            if (event.getDamager() instanceof Player) {
                attacker = (Player)damager;
            } else if (event.getDamager() instanceof Projectile && (shooter = ((Projectile)damager).getShooter()) instanceof Player) {
                attacker = (Player)shooter;
            }
            if (attacker != null && ignoreSelf && event.getEntity().equals((Object)attacker)) {
                attacker = null;
            }
        }
        return attacker;
    }

    public static Player playerWithNameOrUUID(String string) {
        if (string == null) {
            return null;
        }
        return JavaUtils.isUUID(string) ? Bukkit.getPlayer((UUID)UUID.fromString(string)) : Bukkit.getPlayer((String)string);
    }

    @Deprecated
    public static OfflinePlayer offlinePlayerWithNameOrUUID(String string) {
        if (string == null) {
            return null;
        }
        return JavaUtils.isUUID(string) ? Bukkit.getOfflinePlayer((UUID)UUID.fromString(string)) : Bukkit.getOfflinePlayer((String)string);
    }

    public static boolean isWithinX(Location location, Location other, double distance) {
        if (location.getWorld().equals((Object)other.getWorld()) && Math.abs(other.getX() - location.getX()) <= distance && Math.abs(other.getZ() - location.getZ()) <= distance) {
            return true;
        }
        return false;
    }

    public static Location getHighestLocation(Location origin) {
        return BukkitUtils.getHighestLocation(origin, null);
    }

    public static Location getHighestLocation(Location origin, Location def) {
        Preconditions.checkNotNull((Object)origin, (Object)"The location cannot be null");
        Location cloned = origin.clone();
        World world = cloned.getWorld();
        int x = cloned.getBlockX();
        int y = world.getMaxHeight();
        int z = cloned.getBlockZ();
        while (y > origin.getBlockY()) {
            Block block;
            if ((block = world.getBlockAt(x, --y, z)).isEmpty()) continue;
            Location next = block.getLocation();
            next.setPitch(origin.getPitch());
            next.setYaw(origin.getYaw());
            return next;
        }
        return def;
    }

    public static boolean isDebuff(PotionEffectType type) {
        return DEBUFF_TYPES.contains((Object)type);
    }

    public static boolean isDebuff(PotionEffect potionEffect) {
        return BukkitUtils.isDebuff(potionEffect.getType());
    }

    public static boolean isDebuff(ThrownPotion thrownPotion) {
        for (PotionEffect effect : thrownPotion.getEffects()) {
            if (!BukkitUtils.isDebuff(effect)) continue;
            return true;
        }
        return false;
    }

    public static void loadDebuffs() {
        DEBUFF_TYPES.add(PotionEffectType.BLINDNESS);
        DEBUFF_TYPES.add(PotionEffectType.CONFUSION);
        DEBUFF_TYPES.add(PotionEffectType.HARM);
        DEBUFF_TYPES.add(PotionEffectType.HUNGER);
        DEBUFF_TYPES.add(PotionEffectType.POISON);
        DEBUFF_TYPES.add(PotionEffectType.SATURATION);
        DEBUFF_TYPES.add(PotionEffectType.SLOW);
        DEBUFF_TYPES.add(PotionEffectType.SLOW_DIGGING);
        DEBUFF_TYPES.add(PotionEffectType.WEAKNESS);
        DEBUFF_TYPES.add(PotionEffectType.WITHER);
    }
}

