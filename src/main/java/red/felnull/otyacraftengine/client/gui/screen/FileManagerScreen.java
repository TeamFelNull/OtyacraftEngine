package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import red.felnull.otyacraftengine.client.gui.widget.ScrollBarSlider;

public class FileManagerScreen extends IkisugiScreen {

    private ScrollBarSlider test;

    public FileManagerScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void initByIKSG() {
        this.test = this.addWidgetByIKSG(new ScrollBarSlider(getWidthByIKSG() / 2, 10, 100, 10, 0, -30));
        this.test.setShowRange(true);
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderDirtBackgroundByIKSG(0);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
    }
}
