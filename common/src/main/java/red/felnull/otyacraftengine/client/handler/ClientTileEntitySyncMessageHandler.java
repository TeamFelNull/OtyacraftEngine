package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.blockentity.IClientSyncbleBlockEntity;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;

public class ClientTileEntitySyncMessageHandler implements IPacketMessageClientHandler<ClientTileEntitySyncMessage> {
    @Override
    public boolean reversiveMessage(ClientTileEntitySyncMessage message, ClientPacketListener handler) {

        Minecraft mc = Minecraft.getInstance();

        if (!mc.level.dimension().location().equals(message.dimension))
            return true;


        BlockEntity be = mc.level.getBlockEntity(message.pos);

        if (be == null)
            return true;

        ResourceLocation belocation = Registry.BLOCK_ENTITY_TYPE.getKey(be.getType());

        if (belocation == null || !belocation.equals(message.tileName))
            return true;

        if (be instanceof IClientSyncbleBlockEntity) {
            ((IClientSyncbleBlockEntity) be).clientSyncble(message.syncedData);
        }

        return true;
    }
}

