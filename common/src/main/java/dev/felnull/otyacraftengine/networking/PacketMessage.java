package dev.felnull.otyacraftengine.networking;

import net.minecraft.network.FriendlyByteBuf;

public interface PacketMessage {
    public FriendlyByteBuf toFBB();
}
