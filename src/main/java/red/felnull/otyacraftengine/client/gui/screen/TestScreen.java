package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.ScrollBarSlider;

public class TestScreen extends IkisugiScreen {
    private ScrollBarSlider sc;

    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        sc = this.addWidgetByIKSG(new ScrollBarSlider(200, 10, 100, 1, 0, -100));
        sc.setShowRange(true);
    }

    @Override
    public void tickByIKSG() {
        System.out.println(sc.getValue());
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderDirtBackgroundByIKSG(0);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
    }
}
