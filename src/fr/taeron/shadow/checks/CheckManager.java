
package fr.taeron.shadow.checks;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.Check;
import fr.taeron.shadow.checks.combat.Criticals;
import fr.taeron.shadow.checks.combat.HeadSnap;
import fr.taeron.shadow.checks.combat.ReachA;
import fr.taeron.shadow.checks.combat.ReachB;
import fr.taeron.shadow.checks.combat.ReachD;
import fr.taeron.shadow.checks.combat.Regen;
import fr.taeron.shadow.checks.combat.WallHit;
import fr.taeron.shadow.checks.misc.MorePackets;
import fr.taeron.shadow.checks.misc.TabComplete;
import fr.taeron.shadow.checks.movement.CherrySpeed;
import fr.taeron.shadow.checks.movement.Glide;
import fr.taeron.shadow.checks.movement.HFly;
import fr.taeron.shadow.checks.movement.NoFall;
import fr.taeron.shadow.checks.movement.SpeedA;
import fr.taeron.shadow.checks.movement.Step;
import fr.taeron.shadow.checks.movement.VFly;
import fr.taeron.shadow.utils.InventoryUtils;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CheckManager
implements Listener {
    private ArrayList<Check> checks = new ArrayList<Check>();

    public CheckManager() {
        this.load();
    }

    public void load() {
        this.checks.add(new Criticals());
        this.checks.add(new ReachA());
        this.checks.add(new ReachB());
        this.checks.add(new ReachD());
        this.checks.add(new Regen());
        this.checks.add(new MorePackets());
        this.checks.add(new CherrySpeed());
        this.checks.add(new Glide());
        this.checks.add(new HFly());
        this.checks.add(new NoFall());
        this.checks.add(new SpeedA());
        this.checks.add(new Step());
        this.checks.add(new VFly());
        this.checks.add(new TabComplete());
        this.checks.add(new HeadSnap());
        this.checks.add(new WallHit());
    }

    public boolean exists(Check c) {
        return this.checks.contains(c);
    }

    public boolean exists(String s) {
        for (Check ch : this.checks) {
            if (!ch.getCheckName().equalsIgnoreCase(s)) continue;
            return true;
        }
        return false;
    }

    public boolean isCheckEnabled(Check c) {
        if (!this.exists(c)) {
            return false;
        }
        for (Check ch : this.checks) {
            if (ch.getCheckName() != c.getCheckName()) continue;
            return ch.isEnabled();
        }
        return false;
    }

    public Check getCheck(Check c) {
        return this.getCheck(c.getCheckName());
    }

    public Check getCheck(String s) {
        for (Check ch : this.checks) {
            if (!ch.getCheckName().equalsIgnoreCase(s)) continue;
            return ch;
        }
        return null;
    }

    public ArrayList<Check> getChecks() {
        return this.checks;
    }

    public void disableCheck(Check c) {
        if (!this.exists(c)) {
            return;
        }
        this.getCheck(c).setEnabled(false);
    }

    public void disableCheck(String s) {
        if (!this.exists(s)) {
            return;
        }
        this.getCheck(s).setEnabled(false);
    }

    public void enableCheck(Check c) {
        if (!this.exists(c)) {
            return;
        }
        this.getCheck(c).setEnabled(true);
    }

    public void enableCheck(String s) {
        if (!this.exists(s)) {
            return;
        }
        this.getCheck(s).setEnabled(true);
    }

    public Check getCheckWithRelativeName(String s) {
        return this.getCheck(s.replace("\u00a7c\u00a7l", "").replace("\u00a7a\u00a7l", ""));
    }

    public void openCheckManagerGui(Player p) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, (int)InventoryUtils.getSafestInventorySize((int)this.checks.size()), (String)(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + " \u00a7aChecks:"));
        int i = 0;
        while (i < this.checks.size()) {
            ItemStack item = this.checks.get(i).isEnabled() ? new fr.taeron.shadow.utils.ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 5).displayName("\u00a7a\u00a7l" + this.checks.get(i).getCheckName()).build() : new fr.taeron.shadow.utils.ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 14).displayName("\u00a7c\u00a7l" + this.checks.get(i).getCheckName()).build();
            inv.setItem(i, item);
            ++i;
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void guiClick(InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equalsIgnoreCase(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + " \u00a7aChecks:")) {
            return;
        }
        if (e.getSlotType() != InventoryType.SlotType.CONTAINER) {
            e.setCancelled(true);
            return;
        }
        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (!e.getCurrentItem().hasItemMeta()) {
            e.setCancelled(true);
            return;
        }
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) {
            e.setCancelled(true);
            return;
        }
        e.setCancelled(true);
        Player p = (Player)e.getWhoClicked();
        String s = e.getCurrentItem().getItemMeta().getDisplayName();
        if (this.getCheckWithRelativeName(s) == null) {
            p.sendMessage("\u00a7cLe check '" + s + "' n'existe pas.");
            p.closeInventory();
            return;
        }
        Check c = this.getCheckWithRelativeName(s);
        if (c.isEnabled()) {
            p.performCommand("check disable " + c.getCheckName().replace(" ", "_"));
            e.getInventory().setItem(e.getSlot(), new fr.taeron.shadow.utils.ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 5).displayName("\u00a7c\u00a7l" + c.getCheckName()).build());
            p.updateInventory();
            return;
        }
        p.performCommand("check enable " + c.getCheckName().replace(" ", "_"));
        e.getInventory().setItem(e.getSlot(), new fr.taeron.shadow.utils.ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 14).displayName("\u00a7a\u00a7l" + c.getCheckName()).build());
        p.updateInventory();
    }
}

