package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import red.felnull.otyacraftengine.api.event.client.InputEvent;
import red.felnull.otyacraftengine.client.gui.screen.TestScreen;
import red.felnull.otyacraftengine.client.keys.OEKeyMappings;

public class TestClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onKey(InputEvent.KeyInputEvent e) {
        if (e.getKey() == OEKeyMappings.TEST.getDefaultKey().getValue()) {
      //      mc.setScreen(new TestScreen());
        }
    }
}
