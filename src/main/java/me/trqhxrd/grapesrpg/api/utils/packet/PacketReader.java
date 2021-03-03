package me.trqhxrd.grapesrpg.api.utils.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PacketReader {

    public static final String DECODER_NAME = "grapes";
    private final Set<PacketTask> tasks = new HashSet<>();
    private final GrapesPlayer player;

    public PacketReader(GrapesPlayer player) {
        this(player, false);
    }

    public PacketReader(GrapesPlayer player, boolean inject) {
        this.player = player;
        if (inject) this.inject();
    }

    public void inject() {
        ((CraftPlayer) player.getWrappedObject())
                .getHandle()
                .playerConnection
                .networkManager
                .channel
                .pipeline()
                .addAfter("decoder", DECODER_NAME, new MessageToMessageDecoder<Packet<?>>() {
                    @Override
                    protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) throws Exception {
                        list.add(packet);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (Grapes.getGrapes().isEnabled()) {
                                    synchronized (tasks) {
                                        tasks.forEach(t -> t.execute(packet));
                                    }
                                }
                            }
                        }.runTaskLater(Grapes.getGrapes(), 0);
                    }
                });
    }

    public void uninject() {
        Channel channel = ((CraftPlayer) player.getWrappedObject()).getHandle().playerConnection.networkManager.channel;
        if (channel.pipeline().get(DECODER_NAME) != null) channel.pipeline().remove(DECODER_NAME);
    }

    public void addTask(PacketTask task) {
        synchronized (tasks) {
            tasks.add(task);
        }
    }
}
