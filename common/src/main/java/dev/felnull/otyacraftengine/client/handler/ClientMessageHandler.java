package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.blockentity.IClientSyncbleBlockEntity;
import dev.felnull.otyacraftengine.client.gui.screen.IInstructionBEScreen;
import dev.felnull.otyacraftengine.client.gui.screen.IInstructionItemScreen;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.Minecraft;

public class ClientMessageHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onTestMessage(OEPackets.TestMessage message, NetworkManager.PacketContext packetContext) {
        System.out.println(message.str);
        System.out.println(message.num);
    }

    public static void onBlockEntitySyncMessage(OEPackets.BlockEntitySyncMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (!message.blockEntityExistence.check(mc.level))
                return;
            var be = (IClientSyncbleBlockEntity) mc.level.getBlockEntity(message.blockEntityExistence.blockPos());
            be.onSync(message.syncedData);
        });
    }

    public static void onBlockEntityInstructionReturn(OEPackets.BlockEntityInstructionMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (!message.blockEntityExistence.check(mc.level))
                return;
            if (mc.screen instanceof IInstructionBEScreen insScreen && insScreen.getInstructionID().equals(message.instructionScreenID))
                insScreen.onInstructionReturn(message.name, message.num, message.data);
        });
    }

    public static void onItemInstructionReturn(OEPackets.ItemInstructionMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (!message.itemExistence.check(mc.player))
                return;
            if (mc.screen instanceof IInstructionItemScreen insScreen && insScreen.getInstructionID().equals(message.instructionScreenID))
                insScreen.onInstructionReturn(message.name, message.num, message.data);
        });
    }
}
