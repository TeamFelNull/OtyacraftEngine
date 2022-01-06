package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.Function;

public class FixedButtonsList<E> extends AbstractWidget implements IOEBaseComponent {
    private static final Minecraft mc = Minecraft.getInstance();
    private final List<E> list;
    private final Function<E, Component> listName;
    private final PressEntry<E> onPress;
    private final int xTexStart;
    private final int yTexStart;
    private final int wTex;
    private final int hTex;
    private final ResourceLocation resourceLocation;
    protected int num;
    protected int hoveredNumber;
    protected boolean isHoveredScrollBar;
    private float scrollAmount;
    private boolean canScroll;

    public FixedButtonsList(int x, int y, int w, int h, ResourceLocation resource, int tx, int ty, int tw, int th, int num, Component name, List<E> list, Function<E, Component> listName, PressEntry<E> onPressEntry) {
        super(x, y, w, h, name);
        this.list = list;
        this.listName = listName;
        this.num = num;
        this.onPress = onPressEntry;
        this.resourceLocation = resource;
        this.xTexStart = tx;
        this.yTexStart = ty;
        this.wTex = tw;
        this.hTex = th;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    public void render(PoseStack poseStack, int mx, int my, float parTick) {
        if (visible) {
            this.hoveredNumber = (my - y) / getOneButtonHeight();
            this.isHoveredScrollBar = mx >= this.x + getOneButtonWidth() && my >= this.y && mx < this.x + this.width && my < this.y + this.height;
        }
        super.render(poseStack, mx, my, parTick);
    }

    @Override
    public void renderButton(PoseStack poseStack, int mx, int my, float parTick) {
        for (int i = 0; i < num; i++) {
            int cn = getCurrentFirstNumber() + i;

            if (cn >= list.size() || cn < 0)
                break;

            renderOneButton(poseStack, getList().get(cn), cn, i, x, y + getOneButtonHeight() * i, mx, my, parTick);
        }

        renderScrollbar(poseStack, this.x + getOneButtonWidth(), this.y, 9, height);

    }

    protected void renderScrollbar(PoseStack poseStack, int x, int y, int w, int h) {

        boolean hv = isHoveredScrollBar();

        OERenderUtil.drawTexture(resourceLocation, poseStack, x, y, xTexStart + (hv ? 9 : 0), yTexStart, 9, 3, wTex, hTex);
        int bsct = (height - 6) / 16;
        for (int i = 0; i < bsct; i++) {
            OERenderUtil.drawTexture(resourceLocation, poseStack, x, y + 3 + (i * 16), xTexStart + (hv ? 9 : 0), yTexStart + 3, 9, 16, wTex, hTex);
        }
        int bsam = (height - 6) % 16;
        OERenderUtil.drawTexture(resourceLocation, poseStack, x, y + 3 + (bsct * 16), xTexStart + (hv ? 9 : 0), yTexStart + 3, 9, bsam, wTex, hTex);
        OERenderUtil.drawTexture(resourceLocation, poseStack, x, y + height - 3, xTexStart + (hv ? 9 : 0), yTexStart + 19, 9, 3, wTex, hTex);

        int barHeight = getBarHeight();
        float barY = ((height - 2) - barHeight) * scrollAmount;


        OERenderUtil.drawTexture(resourceLocation, poseStack, x + 1, y + 1 + barY, xTexStart + (hv ? 7 : 0), yTexStart + 22, 7, 3, wTex, hTex);
        int ssct = (barHeight - 6) / 14;
        for (int i = 0; i < ssct; i++) {
            OERenderUtil.drawTexture(resourceLocation, poseStack, x + 1, y + 4 + (i * 14) + barY, xTexStart + (hv ? 7 : 0), yTexStart + 25, 7, 14, wTex, hTex);
        }
        int ssam = (barHeight - 6) % 14;
        OERenderUtil.drawTexture(resourceLocation, poseStack, x + 1, y + 4 + (ssct * 14) + barY, xTexStart + (hv ? 7 : 0), yTexStart + 25, 7, ssam, wTex, hTex);
        OERenderUtil.drawTexture(resourceLocation, poseStack, x + 1, y + 1 + barHeight - 3 + barY, xTexStart + (hv ? 7 : 0), yTexStart + 39, 7, 3, wTex, hTex);
    }


    protected void renderOneButton(PoseStack poseStack, E item, int lnum, int bnum, int x, int y, int mx, int my, float parTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int k = this.getYImage(this.isHovered(bnum));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, x, y, 0, 46 + k * 20, getOneButtonWidth() / 2, getOneButtonHeight());
        this.blit(poseStack, x + getOneButtonWidth() / 2, y, 200 - getOneButtonWidth() / 2, 46 + k * 20, getOneButtonWidth() / 2, getOneButtonHeight());
        this.renderBg(poseStack, mc, mx, my);

        int l = this.active ? 16777215 : 10526880;
        drawCenteredString(poseStack, mc.font, this.getMessage(lnum), this.x + getOneButtonWidth() / 2, y + (getOneButtonHeight() - 8) / 2, l | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public List<E> getList() {
        return list;
    }

    public boolean isHovered(int lnum) {
        return isHovered() && hoveredNumber == lnum && !isHoveredScrollBar;
    }

    public Component getMessage(int index) {
        return listName.apply(list.get(index));
    }

    public int getOneButtonHeight() {
        return height / num;
    }

    public int getOneButtonWidth() {
        return width - 9;
    }

    public float getScrollAmount() {
        return scrollAmount;
    }

    public void setScrollAmount(float d) {
        this.scrollAmount = Mth.clamp(d, 0f, 1f);
    }

    @Override
    public void onClick(double mx, double my) {
        onPress((int) ((my - y) / getOneButtonHeight()));
    }

    public boolean isHoveredScrollBar() {
        return isHovered() && isHoveredScrollBar;
    }

    public void onPress(int num) {
        int cn = getCurrentFirstNumber() + num;

        if (cn < list.size() && isHovered(num))
            onPress.onPressEntry(this, list.get(cn), cn, num);
    }

    public int getBarHeight() {
        return Mth.clamp((height - 2) / ((list.size() / num) + 1), 10, height - 2);
    }

    protected int getCurrentFirstNumber() {


        if (list.size() <= num)
            return 0;

        return (int) ((list.size() - num) * scrollAmount);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int i) {
        canScroll = isHoveredScrollBar();
        scroll(my);
        return super.mouseClicked(mx, my, i);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int i, double f, double g) {
        scroll(my);
        return super.mouseDragged(mx, my, i, f, g);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        this.setScrollAmount(this.getScrollAmount() - (float) f * ((float) num / (float) height));
        return true;
    }

    public void scroll(double mouseY) {
        if (canScroll) {
            int cy = (int) (mouseY - this.y - 1 - getBarHeight() / 2);
            int sa = height - 2 - getBarHeight();
            if (sa > 0) {
                setScrollAmount((float) cy / (float) sa);
            }
        }
    }

    public static interface PressEntry<E> {
        void onPressEntry(FixedButtonsList<E> buttonsList, E item, int itemNum, int num);
    }
}
