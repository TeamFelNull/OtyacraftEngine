package dev.felnull.otyacraftengine.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public class ServerMessageHandler {
    public static void onBlockEntityInstructionMessage(OEPackets.BlockEntityInstructionMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            var player = (ServerPlayer) packetContext.getPlayer();
            if (!player.getLevel().dimension().location().equals(message.dimension))
                return;

            var be = player.getLevel().getBlockEntity(message.pos);
            if (!(be instanceof IInstructionBlockEntity ibe) || !message.beName.equals(Registry.BLOCK_ENTITY_TYPE.getKey(be.getType())))
                return;

            if (ibe.canInstructionWith(player, message.name, message.num, message.data)) {
                CompoundTag retag = ibe.onInstruction(player, message.name, message.num, message.data);
                if (retag != null)
                    NetworkManager.sendToPlayer(player, OEPackets.BLOCK_ENTITY_INSTRUCTION_RETURN, new OEPackets.BlockEntityInstructionMessage(message.instructionScreenID, message.dimension, message.pos, message.beName, message.name, message.num, retag).toFBB());
            }
        });
    }
}
