package red.felnull.otyacraftengine.client.handler;

import me.shedaniel.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.event.client.ItemTooltipEvent;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;
import red.felnull.otyacraftengine.util.IKSGModUtil;

import java.util.List;

public class ClientHandler {
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register((mc, i, i1, i2, i3) -> {
            OEClientHooks.fireKeyInput(i, i1, i2, i3);
            return InteractionResult.SUCCESS;
        });
        ClientRawInputEvent.MOUSE_SCROLLED.register((mc, v) -> OEClientHooks.onMouseScroll(mc.mouseHandler, v) ? InteractionResult.FAIL : InteractionResult.PASS);
    }

    public static void onToolTip(ItemTooltipEvent e) {
        addModName(e.getItemStack(), e.getToolTip());
    }

    private static void addTagList(ItemTooltipEvent e) {
        //   IKSGTagUtil.addTagTooltip(e.getItemStack(), e.getToolTip());
    }

    private static void addModName(ItemStack stack, List<Component> list) {
        String id = IKSGModUtil.getItemAddModID(stack);
        String name = IKSGModUtil.getModName(id);
        Style style = Style.EMPTY.withColor(TextColor.fromRgb(api.getModColor(id)));
        list.add(new TextComponent(name).append(" ").append(IKSGModUtil.getModVersion(id)).setStyle(style));
    }

}
