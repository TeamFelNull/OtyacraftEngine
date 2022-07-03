package dev.felnull.otyacraftengine.networking;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.handler.ClientMessageHandler;
import dev.felnull.otyacraftengine.networking.existence.BlockEntityExistence;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OEPackets {
    public static final ResourceLocation BLOCK_ENTITY_SYNC = new ResourceLocation(OtyacraftEngine.MODID, "block_entity_sync");

    public static void init() {

    }

    public static void clientInit() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), BLOCK_ENTITY_SYNC, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onBlockEntitySyncMessage(new BlockEntitySyncMessage(friendlyByteBuf), packetContext));
    }

    public static record BlockEntitySyncMessage(BlockEntityExistence blockEntityExistence,
                                                CompoundTag syncedData) implements PacketMessage {
        private BlockEntitySyncMessage(FriendlyByteBuf bf) {
            this(BlockEntityExistence.read(bf), bf.readNbt());
        }

        @Override
        public FriendlyByteBuf toFBB(FriendlyByteBuf buf) {
            blockEntityExistence.write(buf);
            buf.writeNbt(syncedData);
            return buf;
        }
    }

}
