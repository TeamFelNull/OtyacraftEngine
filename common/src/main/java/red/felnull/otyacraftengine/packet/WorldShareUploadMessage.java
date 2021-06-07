package red.felnull.otyacraftengine.packet;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class WorldShareUploadMessage implements IPacketMessage {
    public UUID id;
    public int num;
    public byte[] data;

    public WorldShareUploadMessage() {

    }

    public WorldShareUploadMessage(UUID id, int num, byte[] data) {
        this.id = id;
        this.num = num;
        this.data = data;
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.id = buffer.readUUID();
        this.num = buffer.readInt();
        this.data = buffer.readByteArray();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.id);
        buffer.writeInt(this.num);
        buffer.writeByteArray(this.data);
    }
}
