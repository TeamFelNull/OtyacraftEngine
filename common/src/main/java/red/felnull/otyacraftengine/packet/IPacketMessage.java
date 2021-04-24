package red.felnull.otyacraftengine.packet;

import net.minecraft.network.FriendlyByteBuf;

public interface IPacketMessage {
    void decode(FriendlyByteBuf buffer);

    void encode(FriendlyByteBuf buffer);
}
