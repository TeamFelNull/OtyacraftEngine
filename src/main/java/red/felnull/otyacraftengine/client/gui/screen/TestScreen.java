package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.FileChooserWidget;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class TestScreen extends IkisugiScreen {

    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        super.initByIKSG();
        addWidgetByIKSG(new FileChooserWidget(5, 5, this));
    }

    @Override
    public void tickByIKSG() {
        super.tickByIKSG();
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        renderBackgroundByIKSG(matrix);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
        IKSGRenderUtil.drawPlayerFaseByUUID(matrix, "0f286fc2-0c86-42d5-8518-c306cad74f03", 0, 0);
    }
}
