package fr.taeron.shadow.utils;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class InventoryUtils
{
  public static final int DEFAULT_INVENTORY_WIDTH = 9;
  public static final int MINIMUM_INVENTORY_HEIGHT = 1;
  public static final int MINIMUM_INVENTORY_SIZE = 9;
  public static final int MAXIMUM_INVENTORY_HEIGHT = 6;
  public static final int MAXIMUM_INVENTORY_SIZE = 54;
  public static final int MAXIMUM_SINGLE_CHEST_SIZE = 27;
  public static final int MAXIMUM_DOUBLE_CHEST_SIZE = 54;
  
  public static ItemStack[] deepClone(ItemStack[] origin)
  {
    Preconditions.checkNotNull(origin, "Origin cannot be null");
    ItemStack[] cloned = new ItemStack[origin.length];
    for (int i = 0; i < origin.length; i++)
    {
      ItemStack next = origin[i];
      cloned[i] = (next == null ? null : next.clone());
    }
    return cloned;
  }
  
  public static int getSafestInventorySize(int initialSize)
  {
    return (initialSize + 8) / 9 * 9;
  }
  
  @SuppressWarnings("deprecation")
public static void removeItem(Inventory inventory, Material type, short data, int quantity)
  {
    ItemStack[] contents = inventory.getContents();
    boolean compareDamage = type.getMaxDurability() == 0;
    for (int i = quantity; i > 0; i--)
    {
      ItemStack[] array = contents;
      int length = array.length;
      int j = 0;
      while (j < length)
      {
        ItemStack content = array[j];
        if ((content != null) && (content.getType() == type) && ((!compareDamage) || (content.getData().getData() == data)))
        {
          if (content.getAmount() <= 1)
          {
            inventory.removeItem(new ItemStack[] { content });
            break;
          }
          content.setAmount(content.getAmount() - 1);
          break;
        }
        j++;
      }
    }
  }
  
  @SuppressWarnings("deprecation")
public static int countAmount(Inventory inventory, Material type, short data)
  {
    ItemStack[] contents = inventory.getContents();
    boolean compareDamage = type.getMaxDurability() == 0;
    int counter = 0;
    ItemStack[] arrayOfItemStack1;
    int j = (arrayOfItemStack1 = contents).length;
    for (int i = 0; i < j; i++)
    {
      ItemStack item = arrayOfItemStack1[i];
      if ((item != null) && (item.getType() == type) && ((!compareDamage) || (item.getData().getData() == data))) {
        counter += item.getAmount();
      }
    }
    return counter;
  }
  
  public static boolean isEmpty(Inventory inventory)
  {
    return isEmpty(inventory, true);
  }
  
  public static boolean isEmpty(Inventory inventory, boolean checkArmour)
  {
    boolean result = true;
    ItemStack[] array;
    array = inventory.getContents();
    ItemStack[] arrayOfItemStack1;
    int j = (arrayOfItemStack1 = array).length;
    for (int i = 0; i < j; i++)
    {
      ItemStack content = arrayOfItemStack1[i];
      if ((content != null) && (content.getType() != Material.AIR))
      {
        result = false;
        break;
      }
    }
    if (!result) {
      return false;
    }
    if ((checkArmour) && ((inventory instanceof PlayerInventory)))
    {
      ItemStack[] array2;
      array2 = ((PlayerInventory)inventory).getArmorContents();
      ItemStack[] arrayOfItemStack2;
      int m = (arrayOfItemStack2 = array2).length;
      for (int k = 0; k < m; k++)
      {
        ItemStack content2 = arrayOfItemStack2[k];
        if ((content2 != null) && (content2.getType() != Material.AIR))
        {
          result = false;
          break;
        }
      }
    }
    return result;
  }
  
  public static boolean clickedTopInventory(InventoryDragEvent event)
  {
    InventoryView view = event.getView();
    Inventory topInventory = view.getTopInventory();
    if (topInventory == null) {
      return false;
    }
    boolean result = false;
    Set<Map.Entry<Integer, ItemStack>> entrySet = event.getNewItems().entrySet();
    int size = topInventory.getSize();
    for (Map.Entry<Integer, ItemStack> entry : entrySet) {
      if (((Integer)entry.getKey()).intValue() < size)
      {
        result = true;
        break;
      }
    }
    return result;
  }
}
