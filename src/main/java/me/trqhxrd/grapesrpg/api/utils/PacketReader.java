package me.trqhxrd.grapesrpg.api.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.trqhxrd.grapesrpg.api.common.GrapesPlayer;
import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PacketReader {

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
        ((CraftPlayer) player.getSpigotPlayer())
                .getHandle()
                .playerConnection
                .networkManager
                .channel
                .pipeline()
                .addAfter("decoder", "grapes", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) throws Exception {
                list.add(packet);
                synchronized (tasks) {
                    tasks.forEach(t -> t.execute(packet));
                }
            }
        });
    }

    public void uninject() {

    }

    public void addTask(PacketTask task) {
        synchronized (tasks) {
            tasks.add(task);
        }
    }
}
