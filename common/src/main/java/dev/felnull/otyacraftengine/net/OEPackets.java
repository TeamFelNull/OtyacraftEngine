package dev.felnull.otyacraftengine.net;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.handler.ClientMessageHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OEPackets {
    public static final ResourceLocation TEST = new ResourceLocation(OtyacraftEngine.MODID, "test");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), TEST, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onTestMessage(new TestMessage(friendlyByteBuf), packetContext));
    }

    public static class TestMessage implements PacketMessage {
        public final String str;
        public final int num;

        public TestMessage(FriendlyByteBuf bf) {
            this(bf.readUtf(), bf.readInt());
        }

        public TestMessage(String str, int num) {
            this.str = str;
            this.num = num;
        }

        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUtf(str);
            buf.writeInt(num);
            return buf;
        }
    }
}
