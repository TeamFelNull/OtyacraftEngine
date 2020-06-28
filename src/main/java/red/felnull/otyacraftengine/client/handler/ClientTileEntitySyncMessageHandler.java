package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.tileentity.IClientSyncbleTileEntity;

import java.util.function.Supplier;

public class ClientTileEntitySyncMessageHandler {

    public static void reversiveMessage(ClientTileEntitySyncMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        Minecraft mc = OtyacraftEngine.proxy.getMinecraft();
        if (mc.world.func_230315_m_().getClass().toString() != message.dim)
            return;

        TileEntity tileentity = mc.world.getTileEntity(message.pos);

        if (tileentity == null || !tileentity.getClass().toString().equals(message.tileName) || !(tileentity instanceof IClientSyncbleTileEntity))
            return;

        ((IClientSyncbleTileEntity) tileentity).receiveToClient(message.syncedData);

    }
}
