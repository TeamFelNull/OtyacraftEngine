package red.felnull.otyacraftengine.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ClientPlayerDataSyncMessage {
    public ResourceLocation location;
    public CompoundNBT tag;

    public ClientPlayerDataSyncMessage(ResourceLocation location, CompoundNBT tag) {
        this.location = location;
        this.tag = tag;
    }

    public static ClientPlayerDataSyncMessage decodeMessege(PacketBuffer buffer) {

        return new ClientPlayerDataSyncMessage(new ResourceLocation(buffer.readString()), buffer.readCompoundTag());
    }

    public static void encodeMessege(ClientPlayerDataSyncMessage messegeIn, PacketBuffer buffer) {
        buffer.writeString(messegeIn.location.toString());
        buffer.writeCompoundTag(messegeIn.tag);
    }
}
