package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.client.ReturnInstructionEvent;
import red.felnull.otyacraftengine.api.event.common.ReceiverEvent;
import red.felnull.otyacraftengine.api.event.common.ResponseEvent;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.data.ClientDataSendReservation;
import red.felnull.otyacraftengine.client.data.ClientDataSender;
import red.felnull.otyacraftengine.client.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.client.data.URLImageTextureLoder;
import red.felnull.otyacraftengine.client.gui.screen.IInstructionContainerScreen;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;
import red.felnull.otyacraftengine.data.WorldDataManager;
import red.felnull.otyacraftengine.handler.ServerHandler;
import red.felnull.otyacraftengine.item.IDetailedInfomationItem;
import red.felnull.otyacraftengine.util.IKSGModUtil;
import red.felnull.otyacraftengine.util.IKSGTagUtil;

import java.util.UUID;

public class ClientHandler {
    private static final ResourceLocation CLIENT_RESPONSE = new ResourceLocation(OtyacraftEngine.MODID, "client_response");
    private static Minecraft mc = Minecraft.getInstance();
    private static int loadingCont;
    private static int beaconCont;
    public static UUID CURRENTWORLDUUID;

    @SubscribeEvent
    public static void onInstructonReturn(ReturnInstructionEvent e) {
        if (mc.currentScreen instanceof IInstructionContainerScreen) {
            IInstructionContainerScreen ics = (IInstructionContainerScreen) mc.currentScreen;
            if (e.getPos().equals(ics.getInstrunctionPos())) {
                ics.instructionReturn(e.getName(), e.getData());
            }
        }
    }

/*
    @SubscribeEvent
    public static void onKey(InputEvent.KeyInputEvent e) {
        if (e.getKey() == OEKeyBindings.TEST.getKey().getKeyCode()) {
            mc.displayGuiScreen(new TestScreen());
        }
    }
*/
    private static void addDetailedInformation(ItemTooltipEvent e) {
        ItemStack stack = e.getItemStack();
        if (stack.getItem() instanceof IDetailedInfomationItem) {

            if (!IKSGClientUtil.isKeyInput(ClientConfig.ToolTipDetailedInformationKey.get(), true)) {
                e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation.press", IKSGClientUtil.getKeyBind(ClientConfig.ToolTipDetailedInformationKey.get()).func_238171_j_()).mergeStyle(TextFormatting.WHITE));
                return;
            }
            e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation").mergeStyle(TextFormatting.YELLOW));
            ((IDetailedInfomationItem) stack.getItem()).addDetailedInformation(e);
        }

    }

    private static void addTagList(ItemTooltipEvent e) {
        IKSGTagUtil.addTagTooltip(e.getItemStack(), e.getToolTip());
    }

    private static void addModName(ItemTooltipEvent e) {
        e.getToolTip().add(new StringTextComponent(IKSGModUtil.getModName(IKSGModUtil.getModID(e.getItemStack())) + " " + (IKSGModUtil.getModVersion(IKSGModUtil.getModID(e.getItemStack())).equals("Error!!") ? "" : IKSGModUtil.getModVersion(IKSGModUtil.getModID(e.getItemStack())))).mergeStyle(IKSGModUtil.getModColor(IKSGModUtil.getModID(e.getItemStack()))));
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        if (mc.player == null) {
            WorldDataManager.instance().unloadClient();
        }
        loadingCont++;
        if (loadingCont >= 4) {
            if (IKSGTextureUtil.loadingPaatune >= 3) {
                IKSGTextureUtil.loadingPaatune = 0;
            } else {
                IKSGTextureUtil.loadingPaatune++;
            }
            loadingCont = 0;
        }

        beaconCont++;
        if (beaconCont >= 60) {
            if (RenderHandler.beaconCount >= RenderHandler.beaconMaxCount) {
                RenderHandler.beaconCount = 0;
            } else {
                RenderHandler.beaconCount++;
            }
            beaconCont = 0;
        }

        ClientDataSendReservation.tick();
    }

    @SubscribeEvent
    public static void onServerResponse(ResponseEvent.Server e) {
        if (e.getLocation().equals(CLIENT_RESPONSE)) {
            if (e.getId() == 0) {
                ClientDataSender.response(e.getMessage());
            }
        } else if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"))) {
            if (e.getId() == 0) {
                ReceiveTextureLoder.instance().INDEX_UUID.put(e.getMessage(), e.getData().getString("index"));
            } else if (e.getId() == 1) {
                ReceiveTextureLoder.instance().updateTextuerClient(new ResourceLocation(e.getMessage()), e.getData().getString("name"));
            } else if (e.getId() == 2) {
                ReceiveTextureLoder.instance().setNotFind(e.getMessage());
            }
        } else if (e.getLocation().equals(ServerHandler.CLIENT_WORLDUUID_SYNC)) {
            if (e.getId() == 0) {
                CURRENTWORLDUUID = UUID.fromString(e.getMessage());
            } else if (e.getId() == 1) {
                CURRENTWORLDUUID = null;
            }
        }
    }

    @SubscribeEvent
    public static void onReceiverData(ReceiverEvent.Client.Pos e) {
        if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"))) {
            ReceiveTextureLoder.instance().requestedTextuerReceive(e.getUuid(), e.getName());
        }
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
    public static void onLogIn(WorldEvent.Load e) {
        if (e.getWorld().isRemote()) {
            ReceiveTextureLoder.instance().readClientIndex();
            ReceiveTextureLoder.instance().hashCheckRegularly(true);
            ReceiveTextureLoder.instance().LAST_UPDATE.clear();
            URLImageTextureLoder.instance().writeClientIndex();
        }
    }

    @SubscribeEvent
    public static void onLogOut(WorldEvent.Unload e) {
        if (e.getWorld().isRemote()) {
            ReceiveTextureLoder.instance().writeClientIndex();
            ReceiveTextureLoder.instance().LAST_UPDATE.clear();
            URLImageTextureLoder.instance().writeClientIndex();
        }
    }
}

