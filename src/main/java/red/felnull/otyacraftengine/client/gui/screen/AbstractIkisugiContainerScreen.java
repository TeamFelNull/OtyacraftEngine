package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public abstract class AbstractIkisugiContainerScreen<T extends Container> extends IkisugiContainerScreen<T> {
    public AbstractIkisugiContainerScreen(T screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
    }

    public abstract ResourceLocation getBackGrandTextuer();

    @Override
    protected void drawGuiContainerBackgroundLayerByIKSG(MatrixStack matx, float partTick, int mouseX, int mouseY) {
        IKSGRenderUtil.matrixPush(matx);
        int xs = (this.getWidthByIKSG() - this.xSize) / 2;
        int ys = (this.getHeightByIKSG() - this.ySize) / 2;
        IKSGRenderUtil.guiBindAndBlit(getBackGrandTextuer(), matx, xs, ys, 0, 0, this.xSize, this.ySize, 256, 256);
        IKSGRenderUtil.matrixPop(matx);
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderBackgroundByIKSG(matrix);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
        this.renderHoveredToolTipByIKSG(matrix, mouseX, mouseY);
    }
    @Override
    public boolean keyPressedByIKSG(int keyCode, int scanCode, int modifiers) {

        if (keyCode == 256) {
            this.minecraft.player.closeScreen();
        }

        boolean cane = false;

        for (Widget widget : this.buttons) {
            if (widget instanceof TextFieldWidget) {
                boolean keyp = !widget.keyPressed(keyCode, scanCode, modifiers);
                boolean cap = !((TextFieldWidget) widget).canWrite();
                if (!keyp)
                    continue;
                if (keyp && !cap)
                    return true;
            }
        }

        return super.keyPressedByIKSG(keyCode, scanCode, modifiers);
    }

}
