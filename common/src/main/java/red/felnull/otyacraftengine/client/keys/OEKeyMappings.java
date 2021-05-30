package red.felnull.otyacraftengine.client.keys;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

public class OEKeyMappings {
    public static final KeyMapping TEST = createKeyBind("test", GLFW.GLFW_KEY_M);

    public static void init(OtyacraftEngineAPI api) {
        if (api.isTestMode()) {
            KeyMappingRegistry.register(TEST);
        }
    }


    private static KeyMapping createKeyBind(String name, int keyID) {
        return new KeyMapping("key." + OtyacraftEngine.MODID + "." + name, keyID, "key.categories." + OtyacraftEngine.MODID);
    }
}
