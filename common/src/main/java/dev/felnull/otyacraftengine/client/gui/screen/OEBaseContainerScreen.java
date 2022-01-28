package dev.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.inventory.OEBaseContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public abstract class OEBaseContainerScreen<T extends OEBaseContainerMenu> extends AbstractContainerScreen<T> implements IInstructionBEScreen {
    private static final Minecraft mc = Minecraft.getInstance();
    protected final UUID id = UUID.randomUUID();
    protected int bgTextureWidth = 256;
    protected int bgTextureHeight = 256;

    public OEBaseContainerScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    public BlockEntity getBlockEntity() {
        return mc.level.getBlockEntity(getBlockPos());
    }

    public BlockPos getBlockPos() {
        return getMenu().getPos();
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        this.renderTooltip(poseStack, i, j);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        if (getBackGrandTexture() != null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, getBackGrandTexture());
            blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight, bgTextureWidth, bgTextureHeight);
        }
    }

    protected abstract ResourceLocation getBackGrandTexture();

    @Override
    public void instruction(String name, int num, CompoundTag data) {
        IInstructionBEScreen.instructionBlockEntity(this, getBlockEntity(), name, num, data);
    }

    @Override
    public void onInstructionReturn(String name, int num, CompoundTag data) {

    }

    @Override
    public UUID getInstructionID() {
        return id;
    }

    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        boolean flag1 = super.mouseDragged(d, e, i, f, g);
        boolean flag2 = this.getFocused() != null && this.isDragging() && i == 0 && this.getFocused().mouseDragged(d, e, i, f, g);
        return flag1 & flag2;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        for (GuiEventListener child : children()) {
            if (child instanceof EditBox editBox) {
                editBox.tick();
            }
        }
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (i == 256)
            mc.player.closeContainer();

        for (GuiEventListener child : children()) {
            if (child instanceof EditBox editBox) {
                if (editBox.keyPressed(i, j, k) || editBox.canConsumeInput())
                    return true;
            }
        }

        return super.keyPressed(i, j, k);
    }
}