package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecifyLocation;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseWidget;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IconButton extends OEBaseWidget {
    @NotNull
    private final Consumer<IconButton> onPress;
    @NotNull
    protected TextureSpecifyLocation iconTexture;

    public IconButton(int x, int y, int width, int height, @NotNull Component message, @NotNull TextureSpecifyLocation iconTexture, @NotNull Consumer<IconButton> onPress) {
        super(x, y, width, height, "button", message);
        this.onPress = onPress;
        this.iconTexture = iconTexture;
    }

    @Override
    public void onPress() {
        onPress.accept(this);
    }

    public @NotNull TextureSpecifyLocation getIconTexture() {
        return iconTexture;
    }

    public void setIconTexture(@NotNull TextureSpecifyLocation iconTexture) {
        this.iconTexture = iconTexture;
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int k = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, 0, 46 + k * 20, this.width / 2, this.height);
        this.blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
        this.renderBg(poseStack, minecraft, i, j);
        renderIcon(poseStack, i, j, f);
    }

    protected void renderIcon(@NotNull PoseStack poseStack, int i, int j, float f) {
        OERenderUtil.drawTexture(poseStack, x + (width - iconTexture.width()) / 2f, y + (height - iconTexture.height()) / 2f, iconTexture);
    }
}
