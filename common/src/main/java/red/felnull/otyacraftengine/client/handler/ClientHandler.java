package red.felnull.otyacraftengine.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.shedaniel.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.event.client.ColorHandlerEvent;
import red.felnull.otyacraftengine.api.event.client.OEClientEventHooks;
import red.felnull.otyacraftengine.api.event.client.RenderGuiItemDecorationsEvent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.item.IkisugiBucketItem;
import red.felnull.otyacraftengine.item.storage.IFluidTankItem;

public class ClientHandler {
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register((mc, i, i1, i2, i3) -> {
            OEClientEventHooks.fireKeyInput(i, i1, i2, i3);
            return InteractionResult.SUCCESS;
        });
        ClientRawInputEvent.MOUSE_SCROLLED.register((mc, v) -> OEClientEventHooks.onMouseScroll(mc.mouseHandler, v) ? InteractionResult.FAIL : InteractionResult.PASS);
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
}
