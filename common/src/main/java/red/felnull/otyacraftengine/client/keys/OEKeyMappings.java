package red.felnull.otyacraftengine.client.keys;

import me.shedaniel.architectury.registry.KeyBindings;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

public class OEKeyMappings {
    public static final KeyMapping TEST = createKeyBind("test", GLFW.GLFW_KEY_M);

    public static void init(OtyacraftEngineAPI api) {
        if (api.isTestMode()) {
            KeyBindings.registerKeyBinding(TEST);
        }
    }


    private static KeyMapping createKeyBind(String name, int keyID) {
        return new KeyMapping("key." + OtyacraftEngine.MODID + "." + name, keyID, "key.categories." + OtyacraftEngine.MODID);
    }
}
