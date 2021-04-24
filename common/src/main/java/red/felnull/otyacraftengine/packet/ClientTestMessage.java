package red.felnull.otyacraftengine.packet;

import net.minecraft.network.FriendlyByteBuf;

public class ClientTestMessage implements IPacketMessage {
    public String name;

    public ClientTestMessage() {

    }

    public ClientTestMessage(String name) {
        this.name = name;
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.name = buffer.readUtf(32767);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.name);
    }
}
