package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.gui.widget.FileChooserWidget;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;

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
        IKSGRenderUtil.guiBindAndBlit(IKSGTextureUtil.getPlayerSkinTextureByUUID("81e56089-370c-4ed8-8ffb-df0635ae0762"), matrix, 0, 0, 0, 0, 256, 256);
    }
}
