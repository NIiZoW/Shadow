package fr.taeron.shadow.checks.combat;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.checks.CheatType;
import fr.taeron.shadow.checks.Check;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class WallHit
  extends Check
  implements Listener
{
  private HashMap<Player, Integer> wrongHit;
  public static ArrayList<Material> BlackList = new ArrayList<Material>();
  
  public WallHit()
  {
    this.wrongHit = new HashMap<Player, Integer>();
    setType(CheatType.Combat);
    setName("WallHit (a tapé un joueur alors qu'il visait un bloc)");
    setMaximumViolation(999999);
    setMaxPing(250);
    Bukkit.getPluginManager().registerEvents(this, Shadow.getInstance());
  }
  
  @EventHandler
  public void onWrongHit(EntityDamageByEntityEvent e)
  {
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    if (!(e.getDamager() instanceof Player)) {
      return;
    }
    Player p = (Player)e.getDamager();
    Player t = (Player)e.getEntity();
    double distance = p.getLocation().distance(t.getLocation());
    @SuppressWarnings("deprecation")
	Block b = p.getTargetBlock(null, 3);
    if ((b.isLiquid()) || (b.isEmpty())) {
      return;
    }
    if ((b.getLocation().distance(p.getLocation()) < distance) && (!BlackList.contains(b.getType())))
    {
      if (!this.wrongHit.containsKey(p))
      {
        this.wrongHit.put(p, Integer.valueOf(1));
      }
      else
      {
        this.wrongHit.put(p, Integer.valueOf(((Integer)this.wrongHit.get(p)).intValue() + 1));
        if (((Integer)this.wrongHit.get(p)).intValue() > 2) {
          this.wrongHit.remove(p);
        }
      }
      e.setCancelled(true);
    }
    else if (this.wrongHit.containsKey(p))
    {
      this.wrongHit.remove(p);
    }
  }
  
  @SuppressWarnings("deprecation")
public static void loadBlockList()
  {
    BlackList.add(Material.FENCE);
    BlackList.add(Material.SKULL);
    BlackList.add(Material.SKULL_ITEM);
    BlackList.add(Material.LONG_GRASS);
    BlackList.add(Material.REDSTONE_COMPARATOR);
    BlackList.add(Material.REDSTONE_COMPARATOR_OFF);
    BlackList.add(Material.REDSTONE_COMPARATOR_ON);
    BlackList.add(Material.REDSTONE_LAMP_OFF);
    BlackList.add(Material.REDSTONE_LAMP_ON);
    BlackList.add(Material.REDSTONE_TORCH_OFF);
    BlackList.add(Material.REDSTONE_TORCH_ON);
    BlackList.add(Material.REDSTONE_WIRE);
    BlackList.add(Material.SANDSTONE_STAIRS);
    BlackList.add(Material.SMOOTH_STAIRS);
    BlackList.add(Material.NETHER_BRICK_STAIRS);
    BlackList.add(Material.SPRUCE_WOOD_STAIRS);
    BlackList.add(Material.SIGN);
    BlackList.add(Material.SIGN_POST);
    BlackList.add(Material.SEEDS);
    BlackList.add(Material.SUGAR_CANE);
    BlackList.add(Material.SUGAR_CANE_BLOCK);
    BlackList.add(Material.SAPLING);
    BlackList.add(Material.BIRCH_WOOD_STAIRS);
    BlackList.add(Material.BRICK_STAIRS);
    BlackList.add(Material.COBBLESTONE_STAIRS);
    BlackList.add(Material.JUNGLE_WOOD_STAIRS);
    BlackList.add(Material.NETHER_BRICK_STAIRS);
    BlackList.add(Material.SMOOTH_STAIRS);
    BlackList.add(Material.WOOD_STAIRS);
    BlackList.add(Material.IRON_DOOR_BLOCK);
    BlackList.add(Material.IRON_DOOR);
    BlackList.add(Material.TRAP_DOOR);
    BlackList.add(Material.WOOD_DOOR);
    BlackList.add(Material.WOODEN_DOOR);
    BlackList.add(Material.STONE_SPADE);
    BlackList.add(Material.getMaterial(44));
    BlackList.add(Material.getMaterial(126));
    BlackList.add(Material.getMaterial(175));
  }
}
