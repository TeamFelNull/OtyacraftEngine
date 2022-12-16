package dev.felnull.otyacraftengine.client.gui.components;


import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecify;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseImageWidget;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SwitchButton extends OEBaseImageWidget {
    @Nullable
    private final Consumer<SwitchButton> onPress;
    private boolean showLabel;
    private boolean enable;

    public SwitchButton(int x, int y, @NotNull Component message, @Nullable Consumer<SwitchButton> onPress, boolean showLabel) {
        this(x, y, message, onPress, showLabel, TextureSpecify.createRelative(WIDGETS, 40, 0, 40, 34));
    }

    public SwitchButton(int x, int y, @NotNull Component message, @Nullable Consumer<SwitchButton> onPress, boolean showLabel, @NotNull TextureSpecify texture) {
        super(x, y, 20, 10, "switchButton", message, texture);
        this.onPress = onPress;
        this.showLabel = showLabel;
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int i, int j, float f) {
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, getX(), getY(), texture.getU0() + (this.isHoveredOrFocused() ? 20 : 0), texture.getV0() + (this.enable ? 10 : 0), 20, 10, texture.getTextureWidth(), texture.getTextureHeight());
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, getX() + (this.enable ? (width - 8) : 0), getY() - 2, texture.getU0() + (this.isHoveredOrFocused() ? 8 : 0), texture.getV0() + 20, 8, 14, texture.getTextureWidth(), texture.getTextureHeight());
        this.renderBg(poseStack, mc, i, j);
        if (this.showLabel)
            drawTextBase(poseStack, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onPress() {
        this.enable = !this.enable;
        if (onPress != null)
            this.onPress.accept(this);
    }

    public boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
