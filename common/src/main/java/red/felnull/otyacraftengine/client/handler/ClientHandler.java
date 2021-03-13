package red.felnull.otyacraftengine.client.handler;

import me.shedaniel.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.ItemLike;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.event.client.ColorHandlerEvent;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;
import red.felnull.otyacraftengine.item.IkisugiBucketItem;

public class ClientHandler {
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register((mc, i, i1, i2, i3) -> {
            OEClientHooks.fireKeyInput(i, i1, i2, i3);
            return InteractionResult.SUCCESS;
        });
        ClientRawInputEvent.MOUSE_SCROLLED.register((mc, v) -> OEClientHooks.onMouseScroll(mc.mouseHandler, v) ? InteractionResult.FAIL : InteractionResult.PASS);
    }

    public static void onItemColor(ColorHandlerEvent.Item e) {
        ItemColors c = e.getItemColors();
        ItemLike[] bucketLinks = Registry.ITEM.stream().filter(n -> n instanceof IkisugiBucketItem).filter(n -> ((IkisugiBucketItem) n).isColoring()).toArray(ItemLike[]::new);
        c.register((itemStack, i) -> i == 1 ? ((IkisugiBucketItem) itemStack.getItem()).getFluidColor() : -1, bucketLinks);
    }
}
