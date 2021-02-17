package red.felnull.otyacraftengine.client.handler;

import me.shedaniel.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.world.InteractionResult;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;

public class ClientHandler {

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register((mc, i, i1, i2, i3) -> {
            OEClientHooks.fireKeyInput(i, i1, i2, i3);
            return InteractionResult.SUCCESS;
        });
        ClientRawInputEvent.MOUSE_SCROLLED.register((mc, v) -> OEClientHooks.onMouseScroll(mc.mouseHandler, v) ? InteractionResult.FAIL : InteractionResult.PASS);
    }

}
