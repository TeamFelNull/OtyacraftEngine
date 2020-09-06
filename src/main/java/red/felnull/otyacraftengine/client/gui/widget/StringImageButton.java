package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class StringImageButton extends ChangeableImageButton {
    private boolean shadwString;
    private boolean sizeAdjustment;
    protected int strColor;
    private float scale = 1.0f;

    public StringImageButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn, ITextComponent p_i232261_12_) {
        this(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, 256, 256, onPressIn, p_i232261_12_);
    }

    public StringImageButton(int p_i232261_1_, int p_i232261_2_, int p_i232261_3_, int p_i232261_4_, int p_i232261_5_, int p_i232261_6_, int p_i232261_7_, ResourceLocation p_i232261_8_, int p_i232261_9_, int p_i232261_10_, Button.IPressable p_i232261_11_, ITextComponent p_i232261_12_) {
        super(p_i232261_1_, p_i232261_2_, p_i232261_3_, p_i232261_4_, p_i232261_5_, p_i232261_6_, p_i232261_7_, p_i232261_8_, p_i232261_9_, p_i232261_10_, p_i232261_11_, p_i232261_12_);
        this.shadwString = true;
        this.sizeAdjustment = false;
        this.strColor = -1;
    }


    public void setSizeAdjustment(boolean sizeAdjustment) {
        this.sizeAdjustment = sizeAdjustment;
    }

    public void setShadwString(boolean shadwString) {
        this.shadwString = shadwString;
    }

    public void setStringColor(int strColor) {
        this.strColor = strColor;
    }

    public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float parTic) {
        super.func_230431_b_(matrix, mouseX, mouseY, parTic);
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        int j = strColor == -1 ? getFGColor() | MathHelper.ceil(this.field_230695_q_ * 255.0F) << 24 : strColor;
        IKSGRenderUtil.matrixPush(matrix);
        int x = this.field_230690_l_ + this.field_230688_j_ / 2;
        int y = this.field_230691_m_ + (this.field_230689_k_ - 8) / 2;
        float size = getScale();
        IKSGRenderUtil.matrixPush(matrix);
        IKSGRenderUtil.matrixScalf(matrix, size);
        IKSGRenderUtil.drawCenterString(fontrenderer, matrix, this.func_230458_i_(), x, y, j, shadwString);
        IKSGRenderUtil.matrixPop(matrix);
        IKSGRenderUtil.matrixPop(matrix);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
