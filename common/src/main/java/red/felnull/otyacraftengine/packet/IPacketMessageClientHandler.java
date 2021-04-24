package red.felnull.otyacraftengine.packet;

import net.minecraft.client.multiplayer.ClientPacketListener;

public interface IPacketMessageClientHandler<T extends IPacketMessage> {
    boolean reversiveMessage(T message, ClientPacketListener handler);
}
