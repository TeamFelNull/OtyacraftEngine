package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;

public class TestScreen extends IkisugiScreen {

    public TestScreen() {
        super(new StringTextComponent("test"));
    }

    @Override
    public void initByIKSG() {
        super.initByIKSG();
    }

    @Override
    public void tickByIKSG() {
        super.tickByIKSG();
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
        renderBackgroundByIKSG(matrix);
    }
}
