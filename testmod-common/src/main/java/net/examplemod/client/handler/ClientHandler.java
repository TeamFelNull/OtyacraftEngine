package net.examplemod.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.otyacraftengine.client.event.OBJLoaderEvent;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import net.examplemod.ExampleMod;
import net.examplemod.client.gui.screen.TestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class ClientHandler {
    public static final KeyMapping TEST_KEY = new KeyMapping("key.test.test", GLFW.GLFW_KEY_J, "key.categories.test");

    public static void init() {
        KeyMappingRegistry.register(TEST_KEY);
        ClientRawInputEvent.KEY_PRESSED.register(ClientHandler::onKeyPressed);
        OBJLoaderEvent.LOAD_CHECK.register(ClientHandler::onObjLoadCheck);
    }

    private static EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        if (keyCode == OEClientUtils.getKey(TEST_KEY).getValue()) {
            client.setScreen(new TestScreen());
        }
        return EventResult.interruptDefault();
    }

    private static EventResult onObjLoadCheck(ResourceLocation location) {
        if (ExampleMod.MODID.equals(location.getNamespace()))
            return EventResult.interruptTrue();
        return EventResult.pass();
    }
}
