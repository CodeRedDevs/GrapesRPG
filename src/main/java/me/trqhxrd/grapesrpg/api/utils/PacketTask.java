package me.trqhxrd.grapesrpg.api.utils;

import net.minecraft.server.v1_16_R3.Packet;

/**
 * A PacketTask will be executed, if a Player sends a Packet to the server.
 * The only requirement is, that you register the task using the {@link PacketReader#addTask(PacketTask)}-method.
 *
 * @author Trqhxrd
 */
public interface PacketTask {

    /**
     * This method will be executed everytime, a player sends a packet.
     *
     * @param packet The packet, which was sent by the player.
     */
    void execute(Packet<?> packet);
}
