package red.felnull.otyacraftengine.client.handler;

import me.shedaniel.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.event.client.ItemTooltipEvent;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
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
        List<ResourceLocation> fulidTags = new ArrayList<>();
        List<ResourceLocation> entityTags = new ArrayList<>();

        if (item instanceof BlockItem) {
            blockTags.addAll(IKSGTagUtil.getBlockTags(((BlockItem) item).getBlock()));
        }

        IKSGFluidUtil.getFluidContained(stack).ifPresent(fluidStack -> fulidTags.addAll(IKSGTagUtil.getFluidTags(fluidStack.getFluid())));

        if (item instanceof SpawnEggItem) {
            entityTags.addAll(IKSGTagUtil.getEntityTypeTags(((SpawnEggItem) item).getType(stack.getTag())));
        }

        if (itemTags.isEmpty() && blockTags.isEmpty() && entityTags.isEmpty() && fulidTags.isEmpty())
            return;

        if (!IKSGClientUtil.isKeyInput(Minecraft.getInstance().options.keySprint)) {
            list.add(new TranslatableComponent("tooltip.tag.press", Minecraft.getInstance().options.keySprint.getTranslatedKeyMessage()).withStyle(ChatFormatting.WHITE));
            return;
        }

        Style tagtyle = Style.EMPTY.withColor(ChatFormatting.GRAY);
        Style taginfotyle = Style.EMPTY.withColor(ChatFormatting.AQUA);

        if (!itemTags.isEmpty()) {
            list.add(new TranslatableComponent("tooltip.tag.item").setStyle(taginfotyle));
            itemTags.forEach(n -> list.add(new TextComponent("- ").append(n.toString()).setStyle(tagtyle)));
        }

        if (!blockTags.isEmpty()) {
            list.add(new TranslatableComponent("tooltip.tag.block").setStyle(taginfotyle));
            blockTags.forEach(n -> list.add(new TextComponent("- ").append(n.toString()).setStyle(tagtyle)));
        }

        if (!fulidTags.isEmpty()) {
            list.add(new TranslatableComponent("tooltip.tag.fluid").setStyle(taginfotyle));
            fulidTags.forEach(n -> list.add(new TextComponent("- ").append(n.toString()).setStyle(tagtyle)));
        }

        if (!entityTags.isEmpty()) {
            list.add(new TranslatableComponent("tooltip.tag.entitytype").setStyle(taginfotyle));
            entityTags.forEach(n -> list.add(new TextComponent("- ").append(n.toString()).setStyle(tagtyle)));
        }
    }

    private static void addModName(ItemStack stack, List<Component> list) {
        String id = IKSGModUtil.getModID(stack.getItem());
        Style style = Style.EMPTY.withColor(TextColor.fromRgb(api.getModColor(id)));
        list.add(new TextComponent(IKSGModUtil.getModName(id)).append(" ").append(IKSGModUtil.getModVersion(id)).setStyle(style));
    }

}
