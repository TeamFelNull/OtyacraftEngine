package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.ReceiverEvent;
import red.felnull.otyacraftengine.api.event.ResponseEvent;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.data.ClientDataSender;
import red.felnull.otyacraftengine.client.util.RenderUtil;
import red.felnull.otyacraftengine.client.util.TextureUtil;
import red.felnull.otyacraftengine.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.data.WorldDataManager;
import red.felnull.otyacraftengine.item.IDetailedInfomationItem;
import red.felnull.otyacraftengine.util.ModUtil;
import red.felnull.otyacraftengine.util.TagHelper;

import java.util.Objects;

public class ClientHandler {
    private static Minecraft mc = Minecraft.getInstance();
    private static int loadingCont;

    /*
        @SubscribeEvent
        public static void onKey(InputEvent.KeyInputEvent e) {

            if (e.getKey() == OEKeyBindings.TEST.getKey().getKeyCode()) {
                if (mc.currentScreen != null && !(mc.currentScreen instanceof UnimplementedScreen)) {
                    mc.displayGuiScreen(new UnimplementedScreen(mc.currentScreen, new TranslationTextComponent("test(GUI)")));
                } else if (!(mc.currentScreen instanceof UnimplementedScreen)) {
                    mc.displayGuiScreen(new UnimplementedScreen(new TranslationTextComponent("test(NO_GUI)")));
                }
            }

            if (e.getKey() == OEKeyBindings.TEST.getKey().getKeyCode()) {
                mc.displayGuiScreen(new TestScreen());
            }
        }
    */
    private static void addDetailedInformation(ItemTooltipEvent e) {
        ItemStack stack = e.getItemStack();
        if (stack.getItem() instanceof IDetailedInfomationItem) {

            if (!InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), mc.gameSettings.keyBindSneak.getKeyBinding().getKey().getKeyCode())) {
                e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation.press", mc.gameSettings.keyBindSneak.func_238171_j_()).func_240699_a_(TextFormatting.WHITE));
                return;
            }
            e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation").func_240699_a_(TextFormatting.YELLOW));
            ((IDetailedInfomationItem) stack.getItem()).addDetailedInformation(e);
        }

    }

    private static void addTagList(ItemTooltipEvent e) {

        ItemStack stack = e.getItemStack();

        boolean itemtagflag = !TagHelper.getItemTags(stack).isEmpty();
        boolean blocktagflag = (stack.getItem() instanceof BlockItem && !TagHelper.getBlockTags(stack).isEmpty());
        boolean entitytagflag = (stack.getItem() instanceof SpawnEggItem && !Objects.requireNonNull(TagHelper.getEntityTags(stack)).isEmpty());

        if (!(itemtagflag || blocktagflag || entitytagflag))
            return;

        if (!InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), mc.gameSettings.keyBindSprint.getKeyBinding().getKey().getKeyCode())) {
            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.press", mc.gameSettings.keyBindSprint.func_238171_j_()).func_240699_a_(TextFormatting.WHITE));
            return;
        }

        if (itemtagflag) {

            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.item").func_240699_a_(TextFormatting.AQUA));
            TagHelper.getItemTags(stack).forEach(tags -> e.getToolTip().add(new StringTextComponent(tags.toString()).func_240699_a_(TextFormatting.GRAY)));
        }
        if (blocktagflag) {
            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.block").func_240699_a_(TextFormatting.AQUA));
            TagHelper.getBlockTags(stack).forEach(tags -> e.getToolTip().add(new StringTextComponent(tags.toString()).func_240699_a_(TextFormatting.GRAY)));
        }
        if (entitytagflag) {
            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.entitytype").func_240699_a_(TextFormatting.AQUA));
            Objects.requireNonNull(TagHelper.getEntityTags(stack)).forEach(tags -> e.getToolTip().add(new StringTextComponent(tags.toString()).func_240699_a_(TextFormatting.GRAY)));
        }

    }

    private static void addModName(ItemTooltipEvent e) {
        e.getToolTip().add(new StringTextComponent(ModUtil.getModName(ModUtil.getModID(e.getItemStack())) + " " + (ModUtil.getModVersion(ModUtil.getModID(e.getItemStack())).equals("Error!!") ? "" : ModUtil.getModVersion(ModUtil.getModID(e.getItemStack())))).func_240699_a_(ModUtil.getModColor(ModUtil.getModID(e.getItemStack()))));
    }

    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent e) {

        if (ClientConfig.ToolTipDetailedInformation.get())
            addDetailedInformation(e);

        if (ClientConfig.ToolTipTag.get())
            addTagList(e);

        if (ClientConfig.ToolTipModName.get())
            addModName(e);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        if (mc.player == null) {
            WorldDataManager.instance().unloadClient();
        }
        loadingCont++;
        if (loadingCont >= 4) {
            if (TextureUtil.loadingPaatune >= 3) {
                TextureUtil.loadingPaatune = 0;
            } else {
                TextureUtil.loadingPaatune++;
            }
            loadingCont = 0;
        }
    }

    private static final ResourceLocation CLIENT_RESPONSE = new ResourceLocation(OtyacraftEngine.MODID, "client_response");

    @SubscribeEvent
    public static void onServerResponse(ResponseEvent.Server e) {
        if (e.getLocation().equals(CLIENT_RESPONSE)) {
            if (e.getId() == 0) {
                ClientDataSender.response(e.getMessage());
            }
        } else if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"))) {
            if (e.getId() == 0) {
                ReceiveTextureLoder.instance().CLIENT_INDEX_UUID.put(e.getMessage(), e.getData().getString("index"));
            } else if (e.getId() == 1) {
                ReceiveTextureLoder.instance().updateTextuerClient(new ResourceLocation(e.getMessage()), e.getData().getString("name"));
            }
        }
    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent e) {
        RenderUtil.guiBindAndBlit(TextureUtil.getReceiveTexture(new ResourceLocation(OtyacraftEngine.MODID, "test"), "wa"), e.getMatrixStack(), 0, 0, 0, 0, 256, 256, 256, 256);
    }


    @SubscribeEvent
    public static void onReceiverData(ReceiverEvent.Client.Pos e) {
        if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"))) {
            ReceiveTextureLoder.instance().requestedTextuerReceive(e.getUuid(), e.getName());
        }
    }
}
