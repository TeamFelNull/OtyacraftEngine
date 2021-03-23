package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;

public class TestScreen extends IkisugiScreen {
    public TestScreen() {
        super(new TextComponent("test"));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        IKSGRenderUtil.guiBindAndBlit(IKSGTextureUtil.getPlayerSkinTexture("MoriMori_0317_jp"), poseStack, 0, 0, 0, 0, i, j, i, j);
    }
}
