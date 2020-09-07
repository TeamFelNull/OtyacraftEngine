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
    public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderBgByIKSG(matrix, mouseX, mouseY, parTick);
    }

    //onDrag
    @Override
    protected void func_230983_a_(double mouseX, double mouseY, double p_230983_5_, double p_230983_7_) {
        this.onDragByIKSG(mouseX, mouseY, p_230983_5_, p_230983_7_);
    }

    //isHovered
    @Override
    public boolean func_230449_g_() {
        return isHoveredByIKSG();
    }

    //onClick
    @Override
    public void func_230982_a_(double mouseX, double mouseY) {
        onClickByIKSG(mouseX, mouseY);
    }


    //バッググラウンド描画
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.func_230431_b_(matrix, mouseX, mouseY, parTick);
    }

    //ドラッグ
    protected void onDragByIKSG(double mouseX, double mouseY, double p_230983_5_, double p_230983_7_) {
        super.func_230983_a_(mouseX, mouseY, p_230983_5_, p_230983_7_);
    }

    //カーソルを合わせてるか
    public boolean isHoveredByIKSG() {
        return super.func_230449_g_();
    }

    //クリックした際
    public void onClickByIKSG(double mouseX, double mouseY) {
        super.func_230982_a_(mouseX, mouseY);
    }

    //X
    public int getX() {
        return this.field_230690_l_;
    }

    //y
    public int getY() {
        return this.field_230691_m_;
    }

    //Xサイズ
    public int getXSize() {
        return this.field_230688_j_;
    }

    //Yサイズ
    public int getYSize() {
        return this.field_230689_k_;
    }

}
