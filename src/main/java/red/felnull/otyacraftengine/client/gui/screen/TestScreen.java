package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.LongButton;

public class TestScreen extends IkisugiScreen {
    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        this.addWidgetByIKSG(new LongButton(0, 0, 300, 400, new StringTextComponent("test"), button -> {
            System.out.println("test");
        }));

    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderDirtBackgroundByIKSG(0);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
    }
}
