package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.blockentity.IClientSyncbleBlockEntity;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;

public class ClientBlockEntitySyncMessageHandler implements IPacketMessageClientHandler<ClientTileEntitySyncMessage> {
    @Override
    public boolean reversiveMessage(ClientTileEntitySyncMessage message, ClientPacketListener handler) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null)
            return true;

        if (!mc.level.dimension().location().equals(message.dimension))
            return true;

        BlockEntity be = mc.level.getBlockEntity(message.pos);

        if (!(be instanceof IClientSyncbleBlockEntity))
            return true;

        ResourceLocation belocation = Registry.BLOCK_ENTITY_TYPE.getKey(be.getType());

        if (!message.beName.equals(belocation))
            return true;

        ((IClientSyncbleBlockEntity) be).clientSyncble(message.syncedData);

        return true;
    }
}

