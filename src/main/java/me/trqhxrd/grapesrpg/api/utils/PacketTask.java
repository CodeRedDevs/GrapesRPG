package me.trqhxrd.grapesrpg.api.utils;

import net.minecraft.server.v1_16_R3.Packet;

public interface PacketTask {
    void execute(Packet<?> packet);
}
