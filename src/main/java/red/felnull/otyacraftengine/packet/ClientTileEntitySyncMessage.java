package red.felnull.otyacraftengine.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ClientTileEntitySyncMessage {
    public int dim;
    public BlockPos pos;
    public String tileName;
    public CompoundNBT syncedData;

    public ClientTileEntitySyncMessage(int dim, BlockPos pos, String tileName, CompoundNBT data) {
        this.dim = dim;
        this.pos = pos;
        this.tileName = tileName;
        this.syncedData = data;
    }

    public static ClientTileEntitySyncMessage decodeMessege(PacketBuffer buffer) {
        return new ClientTileEntitySyncMessage(buffer.readInt(), buffer.readBlockPos(), buffer.readString(32767), buffer.readCompoundTag());
    }

    public static void encodeMessege(ClientTileEntitySyncMessage messegeIn, PacketBuffer buffer) {
        buffer.writeInt(messegeIn.dim);
        buffer.writeBlockPos(messegeIn.pos);
        buffer.writeString(messegeIn.tileName);
        buffer.writeCompoundTag(messegeIn.syncedData);
    }
}
