package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecify;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseImageWidget;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Function;

public abstract class FixedListWidget<E> extends OEBaseImageWidget {
    @NotNull
    private List<E> entryList;
    @NotNull
    private final Function<E, Component> entryName;
    @Nullable
    private final PressEntry<E> onPressEntry;
    private final boolean selectable;
    private final int entryShowCount;
    private boolean border;
    private float scrollAmount;
    private boolean canScroll;
    protected boolean isHoveredScrollBar;
    protected int hoveredNumber;
    @Nullable
    protected E selectedEntry;
    protected int selectedEntryIndex = -1;

    public FixedListWidget(int x, int y, int width, int height, @NotNull Component message, int entryShowCount, @NotNull List<E> entryList, @NotNull Function<E, Component> entryName, @Nullable PressEntry<E> onPressEntry, boolean selectable, FixedListWidget<E> old) {
        this(x, y, width, height, message, entryShowCount, entryList, entryName, onPressEntry, selectable, TextureSpecify.create(WIDGETS, 40, 34, 18, 42), old);
    }

    public FixedListWidget(int x, int y, int width, int height, @NotNull Component message, int entryShowCount, @NotNull List<E> entryList, @NotNull Function<E, Component> entryName, @Nullable PressEntry<E> onPressEntry, boolean selectable, @NotNull TextureSpecify texture, FixedListWidget<E> old) {
        super(x, y, width, height, "fixedListWidget", message, texture);
        this.entryShowCount = entryShowCount;
        this.entryList = entryList;
        this.entryName = entryName;
        this.onPressEntry = onPressEntry;
        this.selectable = selectable;
        copyValue(old);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mx, int my, float parTick) {
        if (this.visible) {
            this.hoveredNumber = (my - y) / getIndividualHeight();
            this.isHoveredScrollBar = mx >= this.x + getIndividualWidth() && my >= this.y && mx < this.x + this.width && my < this.y + this.height;
        }
        super.render(poseStack, mx, my, parTick);
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mx, int my, float parTick) {
        for (int i = 0; i < entryShowCount; i++) {
            int cn = getCurrentFirstEntryIndex() + i;
            if (cn >= entryList.size() || cn < 0)
                break;
            var e = entryList.get(cn);
            renderOneButton(poseStack, e, cn, i, x, y + getIndividualHeight() * i, mx, my, parTick, selectedEntry == entryList.get(cn));
        }
        renderScrollbar(poseStack, this.x + getIndividualWidth(), this.y, 9, height);
    }

    protected void renderScrollbar(PoseStack poseStack, int x, int y, int w, int h) {
        boolean hv = isScrollBarHovered() || isFocused();

        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x, y, texture.getU0() + (hv ? 9 : 0), texture.getV0(), 9, 3, texture.getTextureWidth(), texture.getTextureHeight());
        int bsct = (height - 6) / 16;
        for (int i = 0; i < bsct; i++) {
            OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x, y + 3 + (i * 16), texture.getU0() + (hv ? 9 : 0), texture.getV0() + 3, 9, 16, texture.getTextureWidth(), texture.getTextureHeight());
        }
        int bsam = (height - 6) % 16;
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x, y + 3 + (bsct * 16), texture.getU0() + (hv ? 9 : 0), texture.getV0() + 3, 9, bsam, texture.getTextureWidth(), texture.getTextureHeight());
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x, y + height - 3, texture.getU0() + (hv ? 9 : 0), texture.getV0() + 19, 9, 3, texture.getTextureWidth(), texture.getTextureHeight());

        int barHeight = getBarHeight();
        float barY = ((height - 2) - barHeight) * scrollAmount;

        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x + 1, y + 1 + barY, texture.getU0() + (hv ? 7 : 0), texture.getV0() + 22, 7, 3, texture.getTextureWidth(), texture.getTextureHeight());
        int ssct = (barHeight - 6) / 14;
        for (int i = 0; i < ssct; i++) {
            OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x + 1, y + 4 + (i * 14) + barY, texture.getU0() + (hv ? 7 : 0), texture.getV0() + 25, 7, 14, texture.getTextureWidth(), texture.getTextureHeight());
        }
        int ssam = (barHeight - 6) % 14;
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x + 1, y + 4 + (ssct * 14) + barY, texture.getU0() + (hv ? 7 : 0), texture.getV0() + 25, 7, ssam, texture.getTextureWidth(), texture.getTextureHeight());
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x + 1, y + 1 + barHeight - 3 + barY, texture.getU0() + (hv ? 7 : 0), texture.getV0() + 39, 7, 3, texture.getTextureWidth(), texture.getTextureHeight());
    }

    protected void renderOneButton(PoseStack poseStack, E item, int lnum, int bnum, int x, int y, int mx, int my, float parTick, boolean selected) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int k = this.getYImage(this.isEntryHovered(bnum));
        if (selected) k = 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, x, y, 0, 46 + k * 20, getIndividualWidth() / 2, getIndividualHeight());
        this.blit(poseStack, x + getIndividualWidth() / 2, y, 200 - getIndividualWidth() / 2, 46 + k * 20, getIndividualWidth() / 2, getIndividualHeight());
        this.renderBg(poseStack, mc, mx, my);

        int l = this.active ? 16777215 : 10526880;
        drawCenteredString(poseStack, mc.font, this.getMessage(lnum), this.x + this.width / 2, this.y + (this.height - 8) / 2, l | Mth.ceil(this.alpha * 255.0F) << 24);
        // drawCenteredString(poseStack, mc.font, this.getMessage(lnum), this.x + getIndividualWidth() / 2, y + (getIndividualHeight() - 8) / 2, l | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onPress() {

    }

    @Override
    public void playDownSound(@NotNull SoundManager soundManager) {
        boolean flg = isEntryHovered(hoveredNumber);
        boolean nflg = entryList.size() > hoveredNumber;
        if (!(flg && !nflg))
            super.playDownSound(soundManager);
    }

    @Override
    public void onClick(double mx, double my) {
        onPress((int) ((my - y) / getIndividualHeight()));
        super.onClick(mx, my);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int i, double f, double g) {
        scroll(my);
        return super.mouseDragged(mx, my, i, f, g);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int i) {
        canScroll = isScrollBarHovered();
        scroll(my);
        return super.mouseClicked(mx, my, i);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        this.setScrollAmount(this.getScrollAmount() - (float) f * ((float) entryShowCount / (float) height));
        return true;
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (this.active && this.visible && isHoveredOrFocused()) {
            if (i == GLFW.GLFW_KEY_UP) {
                this.setScrollAmount(this.getScrollAmount() - 0.3f * ((float) entryShowCount / (float) height));
                return true;
            } else if (i == GLFW.GLFW_KEY_DOWN) {
                this.setScrollAmount(this.getScrollAmount() + 0.3f * ((float) entryShowCount / (float) height));
                return true;
            }
        }
        return false;
    }

    public void onPress(int num) {
        int cn = getCurrentFirstEntryIndex() + num;
        if (cn < entryList.size() && isEntryHovered(num)) {
            var e = entryList.get(cn);
            if (selectable) {
                selectedEntry = e;
                selectedEntryIndex = cn;
            }
            if (onPressEntry != null)
                onPressEntry.onPressEntry(this, e);
        }
    }

    public void setSelectedEntry(int index) {
        if (index >= 0 && index < entryList.size()) {
            this.selectedEntry = entryList.get(index);
            this.selectedEntryIndex = index;
        } else {
            this.selectedEntry = null;
            this.selectedEntryIndex = -1;
        }
    }

    public void setSelectedEntry(@Nullable E selectedEntry) {
        if (selectedEntry == null || entryList.stream().anyMatch(n -> n == selectedEntry)) {
            this.selectedEntry = selectedEntry;
            if (selectedEntry == null)
                selectedEntryIndex = -1;
            else
                selectedEntryIndex = entryList.indexOf(selectedEntry);
        }
    }

    public @Nullable
    E getSelectedEntry() {
        if (entryList.stream().anyMatch(n -> n == selectedEntry))
            return selectedEntry;
        return null;
    }

    public int getSelectedEntryIndex() {
        if (getSelectedEntry() == null) return -1;
        return selectedEntryIndex;
    }

    protected int getCurrentFirstEntryIndex() {
        if (entryList.size() <= entryShowCount)
            return 0;
        return (int) ((entryList.size() - entryShowCount) * scrollAmount);
    }

    public boolean isEntryHovered(int lnum) {
        return this.isHovered && hoveredNumber == lnum && !isHoveredScrollBar;
    }

    public boolean isScrollBarHovered() {
        return this.isHovered && this.isHoveredScrollBar;
    }

    public Component getMessage(int index) {
        return entryName.apply(entryList.get(index));
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

    public int getBarHeight() {
        return Mth.clamp((height - 2) / ((entryList.size() / entryShowCount) + 1), 10, height - 2);
    }

    public float getScrollAmount() {
        return scrollAmount;
    }

    public void setScrollAmount(float scrollAmount) {
        this.scrollAmount = Mth.clamp(scrollAmount, 0f, 1f);
    }

    public int getIndividualHeight() {
        return height / entryShowCount;
    }

    public int getIndividualWidth() {
        return width - (9 + (border ? 0 : 1));
    }

    public @NotNull
    List<E> getEntryList() {
        return entryList;
    }

    public void setEntryList(@NotNull List<E> entryList) {
        this.entryList = entryList;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public void copyValue(@Nullable FixedListWidget<E> copyValue) {
        if (copyValue != null) {
            this.selectedEntry = copyValue.selectedEntry;
            this.scrollAmount = copyValue.scrollAmount;
            this.selectedEntryIndex = copyValue.selectedEntryIndex;
        }
    }

    public static interface PressEntry<E> {
        void onPressEntry(FixedListWidget<E> widget, E item);
    }
}
