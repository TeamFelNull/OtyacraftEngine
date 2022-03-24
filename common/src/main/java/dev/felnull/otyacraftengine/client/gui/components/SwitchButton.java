package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecifyLocation;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseImageWidget;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
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
        this(x, y, message, onPress, showLabel, new TextureSpecifyLocation(WIDGETS, 40, 0, 40, 34));
    }

    public SwitchButton(int x, int y, @NotNull Component message, @Nullable Consumer<SwitchButton> onPress, boolean showLabel, @NotNull TextureSpecifyLocation texture) {
        super(x, y, 20, 10, "switchButton", message, texture);
        this.onPress = onPress;
        this.showLabel = showLabel;
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int i, int j, float f) {
        OERenderUtil.drawTexture(texture.location(), poseStack, x, y, texture.x() + (this.isHoveredOrFocused() ? 20 : 0), texture.y() + (this.enable ? 10 : 0), 20, 10, texture.sizeWidth(), texture.sizeHeight());
        OERenderUtil.drawTexture(texture.location(), poseStack, x + (this.enable ? (width - 8) : 0), y - 2, texture.x() + (this.isHoveredOrFocused() ? 8 : 0), texture.y() + 20, 8, 14, texture.sizeWidth(), texture.sizeHeight());
        this.renderBg(poseStack, mc, i, j);
        if (this.showLabel)
            drawTextBase(poseStack, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
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
