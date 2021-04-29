package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.inventory.IkisugiContainerMenu;

public abstract class IkisugiContainerScreen<T extends IkisugiContainerMenu> extends AbstractContainerScreen<T> implements IIkisugibleContainerScreen<T>, IInstructionBEScreen {
    public IkisugiContainerScreen(T abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    public BlockEntity getBlockEntity() {
        return getMinecraft().level.getBlockEntity(getBlockPos());
    }

    public BlockPos getBlockPos() {
        return getMenu().getPos();
    }

    @Override
    public BlockEntity getInstructionBlockEntity() {
        return getBlockEntity();
    }

    @Override
    public Screen getScreen() {
        return this;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        this.renderTooltip(poseStack, i, j);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        if (getBackGrandTextuer() != null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, getBackGrandTextuer());
            this.blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        }
    }

    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        boolean flag1 = super.mouseDragged(d, e, i, f, g);
        boolean flag2 = this.getFocused() != null && this.isDragging() && i == 0 && this.getFocused().mouseDragged(d, e, i, f, g);
        return flag1 & flag2;
    }

    protected abstract ResourceLocation getBackGrandTextuer();
}
