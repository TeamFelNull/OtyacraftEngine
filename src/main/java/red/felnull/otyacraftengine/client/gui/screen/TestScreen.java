package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import red.felnull.otyacraftengine.OtyacraftEngine;
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

    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
        //  ResourceLocation location = IKSGTextureUtil.getPictureImageURLTexture("https://i.ytimg.com/vi/6s4LioUWzjs/default.jpg");
        ResourceLocation location = IKSGTextureUtil.getReceiveTexture(new ResourceLocation(OtyacraftEngine.MODID, "test"), "test");
        IKSGRenderUtil.guiBindAndBlit(location, matrix, 0, 0, 0, 0, 100, 100, 100, 100);
    }
}
