package red.felnull.otyacraftengine.client.gui.tooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.util.IKSGColorUtil;

public class TestClientTooltipComponent implements IkisugiClientTooltipComponent {
    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public int getWidth(Font font) {
        return 100;
    }

    @Override
    public void renderImage(Font font, int x, int y, PoseStack poseStack, ItemRenderer itemRenderer, int k, TextureManager textureManager) {
        IKSGRenderUtil.drawText(poseStack, "TEST", x, y + 10, 15728880);
    }

    @Override
    public void renderText(Font font, int i, int j, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
    font.drawInBatch("114514", (float)i, (float)j, -1, true, matrix4f, bufferSource, false, 0, 15728880);
    }

    @Override
    public void render(PoseStack poseStack, int x, int y) {
        //IKSGRenderUtil.drawPlayerFace(poseStack, "MoriMori_0317_jp", x, y);

    }
}
