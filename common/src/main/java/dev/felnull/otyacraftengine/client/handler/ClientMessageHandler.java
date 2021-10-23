package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.net.OEPackets;

public class ClientMessageHandler {
    public static void onTestMessage(OEPackets.TestMessage message, NetworkManager.PacketContext packetContext) {
        System.out.println(message.str);
        System.out.println(message.num);
    }
}
