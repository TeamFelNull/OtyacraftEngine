package net.examplemod.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import net.examplemod.client.gui.screen.TestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ClientHandler {
    public static final KeyMapping TEST_KEY = new KeyMapping("key.test.test", GLFW.GLFW_KEY_J, "key.categories.test");

    public static void init() {
        KeyMappingRegistry.register(TEST_KEY);
        ClientRawInputEvent.KEY_PRESSED.register(ClientHandler::onKeyPressed);
    }

    public static EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        if (keyCode == OEClientUtils.getKey(TEST_KEY).getValue()) {
            client.setScreen(new TestScreen());
        }
        return EventResult.interruptDefault();
    }
}
