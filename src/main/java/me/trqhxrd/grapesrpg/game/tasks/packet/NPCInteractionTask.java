package me.trqhxrd.grapesrpg.game.tasks.packet;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.utils.ClickType;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.NPC;
import me.trqhxrd.grapesrpg.api.utils.packet.PacketTask;
import me.trqhxrd.grapesrpg.api.utils.reflection.Reflection;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;

/**
 * This PacketTask will run all {@link me.trqhxrd.grapesrpg.api.objects.entity.npc.NPCTask}s, as soon as you click on an NPC.
 *
 * @author Trqhxrd
 */
public class NPCInteractionTask implements PacketTask {

    /**
     * This field contains the owner of the PacketReader, which has this Task added.
     */
    private final GrapesPlayer owner;

    /**
     * This constructor, will add the task to the players PacketReader.
     *
     * @param player The Player, who is supposed to add the task.
     */
    public NPCInteractionTask(GrapesPlayer player) {
        player.getPacketReader().addTask(this);
        this.owner = player;
    }

    /**
     * This method will be executed everytime, a player sends a packet.
     *
     * @param packet The packet, which was sent by the player.
     */
    @Override
    public void execute(Packet<?> packet) {
        if (packet instanceof PacketPlayInUseEntity) {
            PacketPlayInUseEntity p = ((PacketPlayInUseEntity) packet);
            @SuppressWarnings("ConstantConditions") int entityID = (Integer) Reflection.getValue(p, "a");
            PacketPlayInUseEntity.EnumEntityUseAction action = (PacketPlayInUseEntity.EnumEntityUseAction) Reflection.getValue(p, "action");
            EnumHand hand = (EnumHand) Reflection.getValue(p, "d");

            for (NPC npc : NPC.getNpcs()) {
                if (npc.getId() == entityID) {
                    ClickType type = hand == null ? ClickType.LEFT : ClickType.RIGHT;
                    npc.callTasks(owner, type);
                    break;
                }
            }
        }
    }
}
