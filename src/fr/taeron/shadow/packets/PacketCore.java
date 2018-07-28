package fr.taeron.shadow.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import fr.taeron.shadow.Shadow;
import fr.taeron.shadow.packets.events.PacketBlockPlacementEvent;
import fr.taeron.shadow.packets.events.PacketEntityActionEvent;
import fr.taeron.shadow.packets.events.PacketHeldItemChangeEvent;
import fr.taeron.shadow.packets.events.PacketKeepAliveEvent;
import fr.taeron.shadow.packets.events.PacketPlayerEvent;
import fr.taeron.shadow.packets.events.PacketPlayerType;
import fr.taeron.shadow.packets.events.PacketSwingArmEvent;
import fr.taeron.shadow.packets.events.PacketUseEntityEvent;
import fr.taeron.shadow.player.APlayer;
import fr.taeron.shadow.utils.UtilServer;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class PacketCore {
    public Shadow shadow;

    public PacketCore(Shadow shadow) {
        this.shadow = shadow;
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.USE_ENTITY}){

            public void onPacketReceiving(PacketEvent event)
            {
              PacketContainer packet = event.getPacket();
              Player player = event.getPlayer();
              if (player == null) {
                return;
              }
              if ((packet.getHandle() instanceof PacketPlayInUseEntity))
              {
                PacketPlayInUseEntity packetNMS = (PacketPlayInUseEntity)packet.getHandle();
                if (packetNMS.c() == null) {
                  return;
                }
              }
              EnumWrappers.EntityUseAction type = (EnumWrappers.EntityUseAction)packet.getEntityUseActions().read(0);
              int entityId = ((Integer)packet.getIntegers().read(0)).intValue();
              Entity entity = null;
              for (Entity entityentity : UtilServer.getEntities(player.getWorld())) {
                if (entityentity.getEntityId() == entityId) {
                  entity = entityentity;
                }
              }
              Bukkit.getServer().getPluginManager().callEvent(new PacketUseEntityEvent(type, player, entity));
            }
          });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.POSITION_LOOK}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketPlayerEvent(player, (Double)event.getPacket().getDoubles().read(0), (Double)event.getPacket().getDoubles().read(1), (Double)event.getPacket().getDoubles().read(2), ((Float)event.getPacket().getFloat().read(0)).floatValue(), ((Float)event.getPacket().getFloat().read(1)).floatValue(), PacketPlayerType.POSLOOK));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.LOOK}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketPlayerEvent(player, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), ((Float)event.getPacket().getFloat().read(0)).floatValue(), ((Float)event.getPacket().getFloat().read(1)).floatValue(), PacketPlayerType.LOOK));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketPlayerEvent(player, (Double)event.getPacket().getDoubles().read(0), (Double)event.getPacket().getDoubles().read(1), (Double)event.getPacket().getDoubles().read(2), player.getLocation().getYaw(), player.getLocation().getPitch(), PacketPlayerType.POSITION));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.ENTITY_ACTION}){

            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketEntityActionEvent(player, (Integer)packet.getIntegers().read(1)));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.KEEP_ALIVE}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketKeepAliveEvent(player));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.ARM_ANIMATION}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketSwingArmEvent(event, player));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.HELD_ITEM_SLOT}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketHeldItemChangeEvent(event, player));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.BLOCK_PLACE}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketBlockPlacementEvent(event, player));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.FLYING}){

            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent((Event)new PacketPlayerEvent(player, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch(), PacketPlayerType.FLYING));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Server.KEEP_ALIVE}){

            public void onPacketSending(PacketEvent event)
            {
              Player player = event.getPlayer();
              if (player == null) {
                return;
              }
              APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(player);
              if (ap != null)
              {
                PacketContainer packet = event.getPacket();
                int pingKey = ((Integer)packet.getIntegers().read(0)).intValue();
                ap.pingKey = pingKey;
                ap.pingNanoSent = System.currentTimeMillis();
              }
            }
          });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, new PacketType[]{PacketType.Play.Client.KEEP_ALIVE}){

            public void onPacketReceiving(PacketEvent event)
            {
              Player player = event.getPlayer();
              if (player == null) {
                return;
              }
              APlayer ap = Shadow.getInstance().getPlayerManager().getByPlayer(player);
              if ((ap != null) && (ap.pingKey != 0))
              {
                PacketContainer packet = event.getPacket();
                int pingKey = ((Integer)packet.getIntegers().read(0)).intValue();
                if (ap.pingKey == pingKey) {
                  ap.ping = ((int)(System.currentTimeMillis() - ap.pingNanoSent));
                }
              }
            }
          });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.shadow, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Server.ENTITY_METADATA}){

            public void onPacketSending(final PacketEvent event) {
                try {
                    final Player observer = event.getPlayer();
                    final StructureModifier<Entity> entityModifer = (StructureModifier<Entity>)event.getPacket().getEntityModifier(observer.getWorld());
                    final Entity entity = (Entity)entityModifer.read(0);
                    if (entity != null && observer != entity && entity instanceof LivingEntity && (!(entity instanceof EnderDragon) || !(entity instanceof Wither)) && (entity.getPassenger() == null || entity.getPassenger() != observer)) {
                        event.setPacket(event.getPacket().deepClone());
                        final StructureModifier<List<WrappedWatchableObject>> watcher = (StructureModifier<List<WrappedWatchableObject>>)event.getPacket().getWatchableCollectionModifier();
                        for (final WrappedWatchableObject watch : watcher.read(0)) {
                            if (watch.getIndex() == 6 && (float)watch.getValue() > 0.0f) {
                                watch.setValue((Object)(new Random().nextInt((int)((CraftLivingEntity)entity).getMaxHealth()) + new Random().nextFloat()));
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}