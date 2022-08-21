package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecify;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseImageWidget;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RadioButton extends OEBaseImageWidget {
    @Nullable
    private final Consumer<RadioButton> onPress;
    @NotNull
    private final Supplier<Set<RadioButton>> group;
    private boolean showLabel;
    private boolean selected;

    public RadioButton(int x, int y, @NotNull Component message, @Nullable Consumer<RadioButton> onPress, @NotNull Supplier<Set<RadioButton>> group, boolean showLabel) {
        this(x, y, 20, 20, message, onPress, group, showLabel, TextureSpecify.create(WIDGETS, 0, 0, 20, 20));
    }

    public RadioButton(int x, int y, int width, int height, @NotNull Component message, @Nullable Consumer<RadioButton> onPress, @NotNull Supplier<Set<RadioButton>> group, boolean showLabel, @NotNull TextureSpecify texture) {
        super(x, y, width, height, "radioButton", message, texture);
        this.onPress = onPress;
        this.group = group;
        this.showLabel = showLabel;
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int i, int j, float f) {
        OERenderUtils.drawTexture(texture.getTextureLocation(), poseStack, x, y, texture.getU0() + (this.isHoveredOrFocused() ? 20 : 0), texture.getV0() + (this.selected ? 20 : 0), texture.getU1(), texture.getV1(), texture.getTextureWidth(), texture.getTextureHeight());
        this.renderBg(poseStack, mc, i, j);
        if (this.showLabel)
            drawTextBase(poseStack, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onPress() {
        this.selected = true;
        for (RadioButton rdo : group.get()) {
            if (this != rdo)
                rdo.selected = false;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}