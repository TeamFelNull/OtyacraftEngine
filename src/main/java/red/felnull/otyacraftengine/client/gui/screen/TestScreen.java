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
        //  IKSGRenderUtil.guiBindAndBlit(IKSGTextureUtil.getPictureImageURLTexture("https://cdn.discordapp.com/attachments/887769442019323924/888494682492010519/gabaana_dadhi.png"), matrix, 0, 0, 100, 100, 100, 100);

        IKSGRenderUtil.guiBindAndBlit(IKSGTextureUtil.getPictureImageURLTexture("https://cdn.discordapp.com/attachments/887769442019323924/893111745927856128/broken.gif"), matrix, 0, 0, 100, 100, 100, 100);
    }
}
