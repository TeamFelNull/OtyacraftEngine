package red.felnull.otyacraftengine.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ServerDataSendMessage {
    public String uuid;
    public ResourceLocation location;
    public String name;
    public byte[] data;
    public int dataSize;
    public boolean frist;

    public ServerDataSendMessage(String uuid, byte[] data) {
        this(uuid, new ResourceLocation("d:d"), "", data, 0, false);
    }

    public ServerDataSendMessage(String uuid, ResourceLocation location, String name, byte[] data, int size, boolean frist) {
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.data = data;
        this.dataSize = size;
        this.frist = frist;
    }

    public static ClientDataSendMessage decodeMessege(PacketBuffer buffer) {
        return new ClientDataSendMessage(buffer.readString(32767), new ResourceLocation(buffer.readString(32767)), buffer.readString(32767), buffer.readByteArray(), buffer.readInt(), buffer.readBoolean());
    }

    public static void encodeMessege(ClientDataSendMessage messege, PacketBuffer buffer) {
        buffer.writeString(messege.uuid);
        buffer.writeString(messege.location.toString());
        buffer.writeString(messege.name);
        buffer.writeByteArray(messege.data);
        buffer.writeInt(messege.dataSize);
        buffer.writeBoolean(messege.frist);
    }
}
