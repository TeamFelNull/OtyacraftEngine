package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import red.felnull.otyacraftengine.OtyacraftEngine;

public class IkisugiWidget extends Widget {
    public static final ResourceLocation OE_WIDGET = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/widgets.png");

    public IkisugiWidget(int x, int y, int sizeX, int sizeY, ITextComponent text) {
        super(x, y, sizeX, sizeY, text);
    }

    //renderBg
    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderBgByIKSG(matrix, mouseX, mouseY, parTick);
    }

    //onDrag
    @Override
    protected void onDrag(double mouseX, double mouseY, double p_230983_5_, double p_230983_7_) {
        this.onDragByIKSG(mouseX, mouseY, p_230983_5_, p_230983_7_);
    }

    //isHovered
    @Override
    public boolean isHovered() {
        return isHoveredByIKSG();
    }

    //onClick
    @Override
    public void onClick(double mouseX, double mouseY) {
        onClickByIKSG(mouseX, mouseY);
    }


    //バッググラウンド描画
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.renderButton(matrix, mouseX, mouseY, parTick);
    }

    //ドラッグ
    protected void onDragByIKSG(double mouseX, double mouseY, double p_230983_5_, double p_230983_7_) {
        super.onDrag(mouseX, mouseY, p_230983_5_, p_230983_7_);
    }

    //カーソルを合わせてるか
    public boolean isHoveredByIKSG() {
        return super.isHovered;
    }

    //クリックした際
    public void onClickByIKSG(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
    }

    //X
    public int getX() {
        return this.x;
    }

    //y
    public int getY() {
        return this.y;
    }

    //Xサイズ
    public int getXSize() {
        return this.width;
    }

    //Yサイズ
    public int getYSize() {
        return this.height;
    }

}
