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

    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
        // ResourceLocation location = IKSGTextureUtil.getPictureImageURLTexture("https://www.dropbox.com/s/ibf9whj1bxnxy9a/2020-06-14_23.39.54.png?dl=1");
        //   ResourceLocation location = IKSGTextureUtil.getReceiveTexture(new ResourceLocation(OtyacraftEngine.MODID, "test"), "test");
        //  IKSGRenderUtil.guiBindAndBlit(location, matrix, 0, 0, 0, 0, 100, 100, 100, 100);
    }
}
