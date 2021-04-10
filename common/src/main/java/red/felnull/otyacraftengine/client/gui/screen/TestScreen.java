package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.material.Fluids;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class TestScreen extends IkisugiScreen {
    public TestScreen() {
        super(new TextComponent("test"));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        IKSGRenderUtil.drawFluid(Fluids.WATER, poseStack, 0, 0, 128, 0, 0, 1, 1);
    }
}
