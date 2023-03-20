package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureRegion;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseComponent;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class IconButton extends Button implements OEBaseComponent {
    @NotNull
    private TextureRegion texture;

    public IconButton(int x, int y, int width, int height, Component message, OnPress onPress, @NotNull TextureRegion texture) {
        this(x, y, width, height, message, onPress, Supplier::get, texture);
    }

    public IconButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration, @NotNull TextureRegion texture) {
        super(x, y, width, height, message, onPress, createNarration);
        this.texture = texture;
    }

    @Override
    public @NotNull TextureRegion getTexture() {
        return texture;
    }

    @Override
    public void setTexture(@NotNull TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public @Nullable String getWidgetTypeName() {
        return "iconButton";
    }

    @Override
    public void renderWidget(PoseStack poseStack, int i, int j, float f) {
        super.renderWidget(poseStack, i, j, f);
        renderIcon(poseStack, i, j, f);
    }

    protected void renderIcon(@NotNull PoseStack poseStack, int i, int j, float f) {

        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, getTexture().location());
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        OERenderUtils.blitFloat(poseStack, getX() + (width - texture.width()) / 2f, getY() + (height - texture.height()) / 2f, getTexture().u0(), getTexture().v0(), getTexture().uvWidth(), getTexture().uvHeight(), getTexture().width(), getTexture().height());

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
