package red.felnull.otyacraftengine.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ClientTileEntitySyncMessage {
    public String dim;
    public BlockPos pos;
    public String tileName;
    public CompoundNBT syncedData;

    public ClientTileEntitySyncMessage(String dim, BlockPos pos, String tileName, CompoundNBT data) {
        this.dim = dim;
        this.pos = pos;
        this.tileName = tileName;
        this.syncedData = data;
    }

    public static ClientTileEntitySyncMessage decodeMessege(PacketBuffer buffer) {
        return new ClientTileEntitySyncMessage(buffer.readString(32767), buffer.readBlockPos(), buffer.readString(32767), buffer.readCompoundTag());
    }

    public static void encodeMessege(ClientTileEntitySyncMessage messegeIn, PacketBuffer buffer) {
        buffer.writeString(messegeIn.dim);
        buffer.writeBlockPos(messegeIn.pos);
        buffer.writeString(messegeIn.tileName);
        buffer.writeCompoundTag(messegeIn.syncedData);
    }
}
