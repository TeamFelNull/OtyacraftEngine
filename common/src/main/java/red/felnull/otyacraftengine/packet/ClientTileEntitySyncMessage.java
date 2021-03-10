package red.felnull.otyacraftengine.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;


public class ClientTileEntitySyncMessage implements IPacketMessage {
    public ResourceLocation dimension;
    public BlockPos pos;
    public ResourceLocation tileName;
    public CompoundTag syncedData;

    public ClientTileEntitySyncMessage() {

    }

    public ClientTileEntitySyncMessage(ResourceLocation dimension, BlockPos pos, ResourceLocation tileName, CompoundTag syncedData) {
        this.dimension = dimension;
        this.pos = pos;
        this.tileName = tileName;
        this.syncedData = syncedData;
    }


    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.dimension = new ResourceLocation(buffer.readUtf(32767));
        this.pos = buffer.readBlockPos();
        this.tileName = new ResourceLocation(buffer.readUtf(32767));
        this.syncedData = buffer.readNbt();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(dimension.toString());
        buffer.writeBlockPos(pos);
        buffer.writeUtf(tileName.toString());
        buffer.writeNbt(syncedData);
    }
}
