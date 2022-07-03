package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.blockentity.ClientSyncableBlockEntity;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.Minecraft;

public class ClientMessageHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onBlockEntitySyncMessage(OEPackets.BlockEntitySyncMessage message, NetworkManager.PacketContext packetContext) {
        /*packetContext.queue(() -> {
            if (!message.blockEntityExistence().check(mc.level)) return;
            var be = (ClientSyncableBlockEntity) mc.level.getBlockEntity(message.blockEntityExistence().blockPos());
            if (be != null)
                be.onSync(message.syncedData());
        });*/
    }
}
