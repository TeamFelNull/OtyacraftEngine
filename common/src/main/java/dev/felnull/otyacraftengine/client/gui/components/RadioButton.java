package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class RadioButton extends AbstractButton implements IOEBaseComponent {
    private final boolean showLabel;
    private final Supplier<RadioButton[]> group;
    private final ResourceLocation texture;
    private final int textureX;
    private final int textureY;
    private final int textureWidth;
    private final int textureHeight;
    private final int textureSizeWidth;
    private final int textureSizeHeight;
    private boolean selected;

    public RadioButton(int x, int y, int w, int h, Component title, boolean selected, boolean showLabel, Supplier<RadioButton[]> group) {
        this(x, y, w, h, title, selected, showLabel, group, WIDGETS, 0, 0, 20, 20, 256, 256);
    }

    public RadioButton(int x, int y, int w, int h, Component title, boolean selected, boolean showLabel, Supplier<RadioButton[]> group, ResourceLocation texture, int tx, int ty, int tw, int th, int tsX, int tsY) {
        super(x, y, w, h, title);
        this.selected = selected;
        this.showLabel = showLabel;
        this.group = group;
        this.texture = texture;
        this.textureX = tx;
        this.textureY = ty;
        this.textureWidth = tw;
        this.textureHeight = th;
        this.textureSizeWidth = tsX;
        this.textureSizeHeight = tsY;
    }

    @Override
    public void onPress() {
        this.selected = true;
        var rdos = this.group.get();
        for (RadioButton rdo : rdos) {
            if (this != rdo) {
                rdo.selected = false;
            }
        }
    }

    public boolean selected() {
        return this.selected;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                narrationElementOutput.add(NarratedElementType.USAGE, new TranslatableComponent("narration.checkbox.usage.focused"));
            } else {
                narrationElementOutput.add(NarratedElementType.USAGE, new TranslatableComponent("narration.checkbox.usage.hovered"));
            }
        }
    }

    @Override
    public void renderButton(PoseStack poseStack, int i, int j, float f) {
        OERenderUtil.drawTexture(texture, poseStack, x, y, textureX + (this.isFocused() ? 20 : 0), textureY + (this.selected ? 20 : 0), textureWidth, textureHeight, textureSizeWidth, textureSizeHeight);
        this.renderBg(poseStack, mc, i, j);
        if (this.showLabel) {
            drawString(poseStack, mc.font, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }
}
