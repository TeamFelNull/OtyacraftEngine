package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;

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
        IKSGRenderUtil.guiBindAndBlit(IKSGTextureUtil.getPictureImageURLTexture("https://media.forgecdn.net/attachments/311/242/2020-09-03_18h18_43.png"), matrix, 0, 0, 0, 0, 30, 30, 30, 30);
    }
}
