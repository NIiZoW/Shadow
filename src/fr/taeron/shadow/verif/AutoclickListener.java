package fr.taeron.shadow.verif;

import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class AutoclickListener
implements Listener {
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            Player player = e.getPlayer();
            if (player.getItemInHand().getType() == Material.FISHING_ROD) {
                return;
            }
            APlayer wp = Shadow.getInstance().getPlayerManager().getByPlayer(player);
            if ((player.getLocation().distance(player.getTargetBlock(null, 10).getLocation()) < 5.7D) &&
                      (player.isSneaking())) {
            	wp.clicks -= 1;
            }
            if (wp.clicks + 1 > wp.maxClicks) {
                wp.maxClicks = wp.clicks + 1;
            }
            APlayer aPlayer = wp;
            ++aPlayer.clicks;
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            APlayer wp = Shadow.getInstance().getPlayerManager().getByPlayer(player);
            wp.lastBlockInteraction = System.currentTimeMillis() + 5000;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player t;
        if (e.getClickedInventory() != null && e.getClickedInventory().getTitle().startsWith("\u00a7cVerif >")) {
            e.setCancelled(true);
        }
        Player p = (Player)e.getWhoClicked();
        if (e.getSlotType() != InventoryType.SlotType.CONTAINER) {
            return;
        }
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (!e.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith("\u00a7cBan Manuel")) {
            t = Bukkit.getPlayer((String)e.getClickedInventory().getTitle().replace("\u00a7cVerif > ", ""));
            if (t == null) {
                p.sendMessage("\u00a7cCe joueur n'est plus connect\u00e9.");
                p.closeInventory();
                return;
            }
            t.kickPlayer(String.valueOf(String.valueOf(Shadow.getInstance().getPrefix())) + " \u00a7fMerci d'avoir jou\u00e9.");
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("ban " + t.getName() + " 120d -s [OBEY] Cheating (Ban Manuel par " + p.getName() + ")"));
        }
 //   }
  //  @SuppressWarnings("deprecation")
  //  @EventHandler
 //   public void fixBugFalseAutoclick(PlayerInteractEvent e){
  //  if(e.getAction()==Action.LEFT_CLICK_AIR){
   //     	        Player player = e.getPlayer();
     //               APlayer wp = Shadow.getInstance().getPlayerManager().getByPlayer(player);
    //    	        if (player.isSneaking()) {
        	//          if (player.getLocation().distance(player.getTargetBlock(null, 10).getLocation()) < 5.7D) {
      	            	//player.sendMessage("blocks :" + player.getLocation().distance(player.getTargetBlock(null, 10).getLocation()));
        	//            	wp.clicks -= 1;

        	 //         }}
}
    }
//}

