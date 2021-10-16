package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.felnull.otyacraftengine.client.util.ClientUtilInit;
import net.minecraft.client.player.LocalPlayer;

public class ClientHandler {
    public static void init() {
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(ClientHandler::onQuit);
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(ClientHandler::onJoin);
    }

    public static void onJoin(LocalPlayer localPlayer) {
        ClientUtilInit.clear();
    }

    public static void onQuit(LocalPlayer localPlayer) {
        ClientUtilInit.clear();
    }
}