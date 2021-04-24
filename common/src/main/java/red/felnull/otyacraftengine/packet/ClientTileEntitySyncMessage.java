package red.felnull.otyacraftengine.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;


public class ClientTileEntitySyncMessage implements IPacketMessage {
    public ResourceLocation dimension;
    public BlockPos pos;
    public ResourceLocation beName;
    public CompoundTag syncedData;

    public ClientTileEntitySyncMessage() {

    }

    public ClientTileEntitySyncMessage(ResourceLocation dimension, BlockPos pos, ResourceLocation beName, CompoundTag syncedData) {
        this.dimension = dimension;
        this.pos = pos;
        this.beName = beName;
        this.syncedData = syncedData;
    }


    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.dimension = buffer.readResourceLocation();
        this.pos = buffer.readBlockPos();
        this.beName = buffer.readResourceLocation();
        this.syncedData = buffer.readNbt();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(dimension);
        buffer.writeBlockPos(pos);
        buffer.writeResourceLocation(beName);
        buffer.writeNbt(syncedData);
    }
}
