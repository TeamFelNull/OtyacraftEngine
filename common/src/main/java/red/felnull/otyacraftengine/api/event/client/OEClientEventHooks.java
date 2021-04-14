package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import red.felnull.otyacraftengine.api.event.OEEvent;
import red.felnull.otyacraftengine.api.event.OEEventBus;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;

import java.util.List;

public class OEClientEventHooks {

    public static ItemTooltipEvent onItemTooltip(ItemStack itemStack, Player player, List<Component> list, TooltipFlag flags) {
        ItemTooltipEvent event = new ItemTooltipEvent(itemStack, player, list, flags);
        OEEventBus.post(event);
        return event;
    }

    public static void fireMouseInput(int button, int action, int mods) {
        OEEventBus.post(new InputEvent.MouseInputEvent(button, action, mods));
    }

    public static void fireKeyInput(int key, int scanCode, int action, int modifiers) {
        OEEventBus.post(new InputEvent.KeyInputEvent(key, scanCode, action, modifiers));
    }

    public static boolean onMouseScroll(MouseHandler mouseHelper, double scrollDelta) {
        OEEvent event = new InputEvent.MouseScrollEvent(scrollDelta, mouseHelper.isLeftPressed(), OEClientExpectPlatform.isMiddlePressed(mouseHelper), mouseHelper.isRightPressed(), mouseHelper.xpos(), mouseHelper.ypos());
        return OEEventBus.post(event);
    }

    public static boolean onRawMouseClicked(int button, int action, int mods) {
        return OEEventBus.post(new InputEvent.RawMouseEvent(button, action, mods));
    }

    public static void onBlockColorsInit(BlockColors blockColors) {
        OEEventBus.post(new ColorHandlerEvent.Block(blockColors));
    }

    public static void onItemColorsInit(ItemColors itemColors, BlockColors blockColors) {
        OEEventBus.post(new ColorHandlerEvent.Item(itemColors, blockColors));
    }

    public static boolean onRenderGuiItemDecorationss(ItemRenderer itemRenderer, Font font, ItemStack itemStack, int xPosition, int yPosition, String string) {
        return OEEventBus.post(new RenderGuiItemDecorationsEvent(itemRenderer, font, itemStack, xPosition, yPosition, string));
    }
}
