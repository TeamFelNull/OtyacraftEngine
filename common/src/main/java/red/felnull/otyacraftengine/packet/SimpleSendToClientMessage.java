package red.felnull.otyacraftengine.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SimpleSendToClientMessage implements IPacketMessage {
    public ResourceLocation location;
    public int id;
    public CompoundTag data;

    public SimpleSendToClientMessage() {

    }

    public SimpleSendToClientMessage(ResourceLocation location, int id, CompoundTag data) {
        this.location = location;
        this.id = id;
        this.data = data;
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.location = buffer.readResourceLocation();
        this.id = buffer.readInt();
        this.data = buffer.readNbt();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.location);
        buffer.writeInt(this.id);
        buffer.writeNbt(this.data);
    }
}
