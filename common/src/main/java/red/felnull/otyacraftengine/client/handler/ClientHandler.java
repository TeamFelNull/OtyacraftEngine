package red.felnull.otyacraftengine.client.handler;

import me.shedaniel.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.event.client.ItemTooltipEvent;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;
import red.felnull.otyacraftengine.util.IKSGFluidUtil;
import red.felnull.otyacraftengine.util.IKSGModUtil;
import red.felnull.otyacraftengine.util.IKSGTagUtil;

import java.util.ArrayList;
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
        addTagList(e.getItemStack(), e.getToolTip());
        addModName(e.getItemStack(), e.getToolTip());
    }

    private static void addTagList(ItemStack stack, List<Component> list) {
        Item item = stack.getItem();

        List<ResourceLocation> itemTags = IKSGTagUtil.getItemTags(item);
        List<ResourceLocation> blockTags = new ArrayList<>();

        if (item instanceof BlockItem) {
            blockTags.addAll(IKSGTagUtil.getBlockTags(((BlockItem) item).getBlock()));
        }


        Style tagtyle = Style.EMPTY.withColor(ChatFormatting.GRAY);
        itemTags.forEach(n -> list.add(new TextComponent("- ").append(n.toString()).setStyle(tagtyle)));
        blockTags.forEach(n -> list.add(new TextComponent("- ").append(n.toString()).setStyle(tagtyle)));

        if (IKSGFluidUtil.getFluidContained(stack).isPresent()) {
            list.add(IKSGFluidUtil.getFluidContained(stack).get().getName());
        }

    }

    private static void addModName(ItemStack stack, List<Component> list) {
        String id = IKSGModUtil.getModID(stack.getItem());
        Style style = Style.EMPTY.withColor(TextColor.fromRgb(api.getModColor(id)));
        list.add(new TextComponent(IKSGModUtil.getModName(id)).append(" ").append(IKSGModUtil.getModVersion(id)).setStyle(style));
    }

}
