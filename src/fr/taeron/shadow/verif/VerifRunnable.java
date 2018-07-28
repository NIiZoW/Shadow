package fr.taeron.shadow.verif;

import fr.taeron.shadow.utils.ItemBuilder;
import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.commands.VerifCommand;
import fr.taeron.shadow.player.APlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class VerifRunnable
extends BukkitRunnable {
    public void run() {
        for (Player verifier : VerifCommand.verifiers.keySet()) {
            if (verifier.getOpenInventory().getTopInventory() != null && verifier.getOpenInventory().getTopInventory().getTitle().startsWith("\u00a7cVerif >")) {
                String o = verifier.getOpenInventory().getTopInventory().getName().split("> ")[1];
                if (Bukkit.getPlayer((String)o) == null) continue;
                Player player = Bukkit.getPlayer((String)o);
                verifier.getOpenInventory().getTopInventory().setItem(0, player.getInventory().getHelmet());
                verifier.getOpenInventory().getTopInventory().setItem(1, player.getInventory().getChestplate());
                verifier.getOpenInventory().getTopInventory().setItem(2, player.getInventory().getLeggings());
                verifier.getOpenInventory().getTopInventory().setItem(3, player.getInventory().getBoots());
                APlayer wp = Shadow.getInstance().getPlayerManager().getByPlayer(player);
                ItemStack it = new ItemStack(Material.DIAMOND_BLOCK, wp.maxClicks > 64 ? 64 : wp.maxClicks);
                ItemMeta im = it.getItemMeta();
                im.setDisplayName(" ");
                im.setLore(Arrays.asList("\u00a7eMaximum de", "\u00a7eclicks: \u00a7f" + wp.maxClicks));
                it.setItemMeta(im);
                ItemStack it2 = new ItemStack(Material.GOLD_BLOCK, wp.clicks > 64 ? 64 : wp.clicks);
                ItemMeta im2 = it2.getItemMeta();
                im2.setDisplayName(" ");
                im2.setDisplayName("\u00a79Clics actuellement: " + wp.clicks);
                im.setLore(Arrays.asList("\u00a79Clics", "\u00a79actuellement: \u00a7f" + wp.maxClicks));
                it2.setItemMeta(im2);
                int ping = wp.ping;
                ItemStack it3 = new ItemStack(Material.IRON_BLOCK, ping > 64 ? 64 : ping);
                ItemMeta im3 = it3.getItemMeta();
                im3.setDisplayName("\u00a76Ping: \u00a7f" + ping);
                Long l = wp.Connexion;
                Long l2 = System.currentTimeMillis();
                long diffMs = l2 - l;
                long diffHours = diffMs / 3600000;
                if (diffHours != 0) {
                    long diffMins = diffMs / 3600000 % 60;
                    im3.setLore(Arrays.asList("\u00a76Connect\u00e9 depuis " + diffHours + " heures et " + diffMins + " minutes."));
                } else {
                    long diffSec = diffMs / 1000;
                    long min = diffSec / 60;
                    im3.setLore(Arrays.asList("\u00a76Connect\u00e9 depuis " + min + " minutes."));
                }
                it3.setItemMeta(im3);
                HashMap<CheatType, Integer> violations = wp.getViolations();
                int nombrealert = 0;
                Iterator<Integer> iterator = violations.values().iterator();
                while (iterator.hasNext()) {
                    int i = iterator.next();
                    nombrealert += i;
                }
                ItemStack it4 = new ItemStack(Material.REDSTONE_BLOCK, nombrealert > 64 ? 64 : nombrealert);
                ItemMeta im4 = it4.getItemMeta();
                im4.setDisplayName("\u00a7cNombre d'alertes: " + nombrealert);
                ArrayList<String> lore = new ArrayList<String>();
                for (CheatType s : violations.keySet()) {
                    lore.add("\u00a77- \u00a7c" + (Object)((Object)s) + ": " + violations.get((Object)s));
                }
                im4.setLore(lore);
                it4.setItemMeta(im4);
                ItemStack it5 = new ItemStack(Material.GOLD_BLOCK, wp.clicks2 > 64 ? 64 : wp.clicks2);
                ItemMeta im5 = it5.getItemMeta();
                im5.setDisplayName("\u00a7cClicks les 5 dernieres secondes:");
                im5.setLore(Arrays.asList("- " + wp.clicks2, "- " + wp.clicks3, "- " + wp.clicks4, "- " + wp.clicks5, "- " + wp.clicks6));
                it5.setItemMeta(im5);
                ItemBuilder setName = new ItemBuilder(Material.POTION).displayName("\u00a7dPotions");
                if (player.getActivePotionEffects().size() == 0) {
                    setName.lore(new String[]{"\u00a77Effets de potions: \u00a7dAucun"});
                } else {
                    String currentLore = "";
                    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                        setName.lore(new String[]{String.valueOf(String.valueOf(currentLore)) + "\u00a7d" + potionEffect.getType().getName() + " " + (potionEffect.getAmplifier() + 1) + " \u00a77(" + VerifRunnable.convertToFormat(potionEffect.getDuration() / 20) + ")"});
                        currentLore = "\u00a7d" + potionEffect.getType().getName() + " " + (potionEffect.getAmplifier() + 1) + " \u00a77(" + VerifRunnable.convertToFormat(potionEffect.getDuration() / 20) + ")\u00a77, ";
                    }
                }
                verifier.getOpenInventory().getTopInventory().setItem(5, it4);
                verifier.getOpenInventory().getTopInventory().setItem(6, it3);
                verifier.getOpenInventory().getTopInventory().setItem(7, it2);
                verifier.getOpenInventory().getTopInventory().setItem(8, it);
                verifier.getOpenInventory().getTopInventory().setItem(4, it5);
                verifier.getOpenInventory().getTopInventory().setItem(48, setName.build());
                CraftPlayer cp = (CraftPlayer)player;
                verifier.getOpenInventory().getTopInventory().setItem(47, new ItemBuilder(Material.INK_SACK, (int)Math.round(cp.getHealth())).data((short) 1).displayName("\u00a7cVie").lore(new String[]{"\u00a7cVie restante: \u00a7f" + (int)Math.round(cp.getHealth()) + " \u2764"}).build());
                verifier.getOpenInventory().getTopInventory().setItem(46, new ItemBuilder(Material.COOKED_BEEF, cp.getFoodLevel()).displayName("\u00a7eNourriture").lore(new String[]{"\u00a7eNourriture restante: \u00a7f" + ((CraftPlayer)player).getFoodLevel()}).build());
                int slot = 8;
                ItemStack[] contents = player.getInventory().getContents();
                int length = contents.length;
                int j = 0;
                while (j < length) {
                    ItemStack itemStack = contents[j];
                    verifier.getOpenInventory().getTopInventory().setItem(++slot, itemStack);
                    ++j;
                }
                continue;
            }
            VerifCommand.verifiers.remove((Object)verifier);
        }
    }

    private static String convertToFormat(long n) {
        if (n < 0) {
            return null;
        }
        return String.format("%02d:%02d", n / 60, n % 60);
    }
}

