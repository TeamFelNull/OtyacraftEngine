package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.otyacraftengine.client.gui.screen.TestScreen;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class TestClientHandler {
    public static final KeyMapping TEST_KEY = new KeyMapping("test", GLFW.GLFW_KEY_J, "test");

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register(TestClientHandler::onKeyPressed);
        KeyMappingRegistry.register(TEST_KEY);
    }

    public static EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        if (keyCode == OEClientExpectPlatform.getKey(TEST_KEY).getValue()) {
            client.setScreen(new TestScreen());
        }
        return EventResult.interruptDefault();
    }
}
