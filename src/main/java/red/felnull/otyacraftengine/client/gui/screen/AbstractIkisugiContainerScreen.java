package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import red.felnull.otyacraftengine.client.util.RenderUtil;

public abstract class AbstractIkisugiContainerScreen<T extends Container> extends IkisugiContainerScreen<T> {
    public AbstractIkisugiContainerScreen(T screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
    }

    public abstract ResourceLocation getBackGrandTextuer();

    @Override
    protected void drawGuiContainerBackgroundLayerByIKSG(MatrixStack matx, float partTick, int mouseX, int mouseY) {
        RenderUtil.matrixPush(matx);
        int xs = (this.getWidthByIKSG() - this.xSize) / 2;
        int ys = (this.getHeightByIKSG() - this.ySize) / 2;
        RenderUtil.guiBindAndBlit(getBackGrandTextuer(), matx, xs, ys, 0, 0, this.xSize, this.ySize, 256, 256);
        RenderUtil.matrixPop(matx);
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderBackgroundByIKSG(matrix);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
        this.renderHoveredToolTipByIKSG(matrix, mouseX, mouseY);
    }
}
