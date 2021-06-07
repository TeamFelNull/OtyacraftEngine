package red.felnull.otyacraftengine.client.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.event.SimpleMessageEvent;
import red.felnull.otyacraftengine.api.event.TickEvent;
import red.felnull.otyacraftengine.api.event.WorldEvent;
import red.felnull.otyacraftengine.api.event.client.ColorHandlerEvent;
import red.felnull.otyacraftengine.api.event.client.OEClientEventHooks;
import red.felnull.otyacraftengine.api.event.client.RenderGuiItemDecorationsEvent;
import red.felnull.otyacraftengine.client.data.WorldShareManager;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;
import red.felnull.otyacraftengine.item.IkisugiBucketItem;
import red.felnull.otyacraftengine.item.storage.IFluidTankItem;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

public class ClientHandler {
    private static final Gson GSON = new Gson();
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {

        ClientRawInputEvent.KEY_PRESSED.register((mc, i, i1, i2, i3) -> {
            OEClientEventHooks.fireKeyInput(i, i1, i2, i3);
            return EventResult.interruptTrue();
        });

        ClientRawInputEvent.MOUSE_SCROLLED.register((mc, v) -> OEClientEventHooks.onMouseScroll(mc.mouseHandler, v) ? EventResult.interruptFalse() : EventResult.interruptDefault());
    }

    public static void onSimpleMessage(SimpleMessageEvent.Client e) {
        if (e.getLocation().equals(new ResourceLocation(OtyacraftEngine.MODID, "worldsharedownload"))) {
            WorldShareManager manager = WorldShareManager.getInstance();
            CompoundTag tag = e.getData();
            if (e.getId() == 0) {
                manager.onDownloadStartSuccess(tag.getUUID("Id"));
            } else if (e.getId() == 1) {
                manager.onDownloadStartError(tag.getUUID("Id"), tag.getString("Exception"));
            }
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent e) {
        WorldShareManager.getInstance().tick();
    }

    public static void onItemColor(ColorHandlerEvent.Item e) {
        ItemColors c = e.getItemColors();
        ItemLike[] bucketLinks = Registry.ITEM.stream().filter(n -> n instanceof IkisugiBucketItem).filter(n -> ((IkisugiBucketItem) n).isColoring()).toArray(ItemLike[]::new);
        c.register((itemStack, i) -> i == 1 ? ((IkisugiBucketItem) itemStack.getItem()).getFluidColor() : -1, bucketLinks);
    }

    public static void onRenderGuiItemDecorations(RenderGuiItemDecorationsEvent e) {
        ItemStack stack = e.getItemStack();
        if (stack.isEmpty())
            return;

        boolean damegeBar = stack.isBarVisible();

        if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankBarVisible(stack)) {
            ((IFluidTankItem) stack.getItem()).getFluidTank(stack).ifPresent(n -> {
                drawBackGroundBar(e.getXPosition() + 2, e.getYPosition() + 11 + (damegeBar ? 0 : 2));
                RenderSystem.disableDepthTest();
                if (!n.isEmpty()) {
                    PoseStack poseStack = new PoseStack();
                    float f = 1f / 13f;
                    IKSGRenderUtil.drawFluid(n.getFluid(), poseStack, e.getXPosition() + 2, e.getYPosition() + 11 + (damegeBar ? 0 : 2), 13, 0, 0, (float) n.getAmountPercent(), f);
                }
                RenderSystem.enableDepthTest();
            });
        }
    }

    private static void drawBackGroundBar(int x, int y) {
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        fillRect(bufferBuilder, x, y, 13, 2, 0, 0, 0, 255);
        RenderSystem.enableBlend();
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
    }

    private static void fillRect(BufferBuilder bufferBuilder, int i, int j, int k, int l, int m, int n, int o, int p) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(i, j, 0.0D).color(m, n, o, p).endVertex();
        bufferBuilder.vertex(i, j + l, 0.0D).color(m, n, o, p).endVertex();
        bufferBuilder.vertex(i + k, j + l, 0.0D).color(m, n, o, p).endVertex();
        bufferBuilder.vertex(i + k, j, 0.0D).color(m, n, o, p).endVertex();
        bufferBuilder.end();
        BufferUploader.end(bufferBuilder);
    }

    public static void onLogIn(WorldEvent.Load e) {
        if (e.getWorld().isClientSide()) {
            IKSGTextureUtil.URL_TEXTURES_INDEX.clear();
            try {
                File cashIndex = IKSGPathUtil.getOtyacraftEngineDataPath().resolve("urltexturecashindex.json").toFile();

                if (cashIndex.exists()) {
                    JsonObject index = GSON.fromJson(new FileReader(cashIndex), JsonObject.class);
                    index.entrySet().forEach(n -> {
                        try {
                            IKSGTextureUtil.URL_TEXTURES_INDEX.put(n.getKey(), n.getValue().getAsString());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void onLogOut(WorldEvent.Unload e) {
        if (e.getWorld().isClientSide()) {
            File cashIndex = IKSGPathUtil.getOtyacraftEngineDataPath().resolve("urltexturecashindex.json").toFile();
            JsonObject index = new JsonObject();
            IKSGTextureUtil.URL_TEXTURES_INDEX.forEach(index::addProperty);
            try {
                IKSGPathUtil.getOtyacraftEngineDataPath().toFile().mkdirs();
                Files.writeString(cashIndex.toPath(), index.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            IKSGTextureUtil.URL_TEXTURES_INDEX.clear();
        }
    }
}
