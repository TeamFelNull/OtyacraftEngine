package dev.felnull.otyacraftengine.net;

import net.minecraft.network.FriendlyByteBuf;

public interface PacketMessage {
    public FriendlyByteBuf toFBB();
}
