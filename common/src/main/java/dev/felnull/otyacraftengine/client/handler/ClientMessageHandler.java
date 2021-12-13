package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.blockentity.IClientSyncbleBlockEntity;
import dev.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import dev.felnull.otyacraftengine.client.gui.screen.IInstructionBEScreen;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;

public class ClientMessageHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onTestMessage(OEPackets.TestMessage message, NetworkManager.PacketContext packetContext) {
        System.out.println(message.str);
        System.out.println(message.num);
    }

    public static void onBlockEntitySyncMessage(OEPackets.BlockEntitySyncMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (mc.level == null || !mc.level.dimension().location().equals(message.dimension))
                return;

            var be = mc.level.getBlockEntity(message.pos);
            if (!(be instanceof IClientSyncbleBlockEntity) || !message.beName.equals(Registry.BLOCK_ENTITY_TYPE.getKey(be.getType())))
                return;

            ((IClientSyncbleBlockEntity) be).onSync(message.syncedData);
        });
    }

    public static void onBlockEntityInstructionReturn(OEPackets.BlockEntityInstructionMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (mc.level == null || !mc.level.dimension().location().equals(message.dimension))
                return;

            var be = mc.level.getBlockEntity(message.pos);
            if (!(be instanceof IInstructionBlockEntity) || !message.beName.equals(Registry.BLOCK_ENTITY_TYPE.getKey(be.getType())))
                return;

            if (mc.screen instanceof IInstructionBEScreen insScreen && insScreen.getInstructionID().equals(message.instructionScreenID))
                insScreen.onInstructionReturn(message.name, message.num, message.data);
        });
    }


}
