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
}
