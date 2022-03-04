package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecifyLocation;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseImageWidget;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RadioButtonV2 extends OEBaseImageWidget {
    @Nullable
    private final Consumer<RadioButtonV2> onPress;
    @NotNull
    private final Supplier<Set<RadioButtonV2>> group;
    private boolean showLabel;
    private boolean checked;

    public RadioButtonV2(int x, int y, @NotNull Component message, @Nullable Consumer<RadioButtonV2> onPress, @NotNull Supplier<Set<RadioButtonV2>> group, boolean showLabel) {
        this(x, y, 20, 20, message, onPress, group, showLabel, new TextureSpecifyLocation(WIDGETS, 0, 0, 20, 20));
    }

    public RadioButtonV2(int x, int y, int width, int height, @NotNull Component message, @Nullable Consumer<RadioButtonV2> onPress, @NotNull Supplier<Set<RadioButtonV2>> group, boolean showLabel, @NotNull TextureSpecifyLocation texture) {
        super(x, y, width, height, "radio", message, texture);
        this.onPress = onPress;
        this.group = group;
        this.showLabel = showLabel;
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int i, int j, float f) {
        OERenderUtil.drawTexture(texture.location(), poseStack, x, y, texture.x() + (this.isHoveredOrFocused() ? 20 : 0), texture.y() + (this.checked ? 20 : 0), texture.width(), texture.height(), texture.sizeWidth(), texture.sizeHeight());
        this.renderBg(poseStack, mc, i, j);
        if (this.showLabel)
            drawTextBase(poseStack, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onPress() {
        this.checked = true;
        for (RadioButtonV2 rdo : group.get()) {
            if (this != rdo)
                rdo.checked = false;
        }
        if (onPress != null)
            this.onPress.accept(this);
    }

    public boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
