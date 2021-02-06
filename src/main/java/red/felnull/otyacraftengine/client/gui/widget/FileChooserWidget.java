package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class FileChooserWidget extends IkisugiWidget {
    private int moveRangeStartX;
    private int moveRangeStartY;
    private int moveRangeWidth;
    private int moveRangeHeight;
    private int clickX;
    private int clickY;
    private boolean isTBHovered;

    public FileChooserWidget(int x, int y, Screen pearentScreen) {
        this(x, y, 0, 0, pearentScreen.width, pearentScreen.height);
    }

    public FileChooserWidget(int x, int y, int moveRangeStartX, int moveRangeStartY, int moveRangeWidth, int moveRangeHeight) {
        super(x, y, 184, 119, new TranslationTextComponent("gui.narrate.filechooser"));
        this.moveRangeStartX = moveRangeStartX;
        this.moveRangeStartY = moveRangeStartY;
        this.moveRangeWidth = moveRangeWidth;
        this.moveRangeHeight = moveRangeHeight;
    }

    @Override
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {

        this.isTBHovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + 10;

        IKSGRenderUtil.guiBindAndBlit(OE_WIDGET, matrix, getX(), getY(), 0, 42, 184, 119);
    }

    @Override
    protected IFormattableTextComponent getNarrationMessage() {
        return new TranslationTextComponent("gui.narrate.window", this.getMessage());
    }

    @Override
    public void onClickByIKSG(double mouseX, double mouseY) {
        if (isTitleBarHovered()) {
            this.clickX = (int) mouseX - getX();
            this.clickY = (int) mouseY - getY();
        }
        super.onClickByIKSG(mouseX, mouseY);
    }

    @Override
    protected void onDragByIKSG(double mouseX, double mouseY, double p_230983_5_, double p_230983_7_) {

        int dx = (int) mouseX - clickX;
        int dy = (int) mouseY - clickY;

        int minX = moveRangeStartX;
        int minY = moveRangeStartY;

        int maxX = moveRangeStartX + moveRangeWidth;
        int maxY = moveRangeStartY + moveRangeHeight;

        if (isTitleBarHovered() && dx >= minX && dx + getXSize() <= maxX) {
            this.x = dx;
        }
        if (isTitleBarHovered() && dy >= minY && dy + getYSize() <= maxY) {
            this.y = dy;
        }
        super.onDragByIKSG(mouseX, mouseY, p_230983_5_, p_230983_7_);
    }

    protected boolean isTitleBarHovered() {
        return this.isTBHovered;
    }
}
