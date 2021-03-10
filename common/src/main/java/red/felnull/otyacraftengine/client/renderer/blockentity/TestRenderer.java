package red.felnull.otyacraftengine.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.blockentity.TestBlockEntity;
import red.felnull.otyacraftengine.client.util.IKSGBlockEntityRendererUtil;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class TestRenderer extends IkisugiBlockEntityRenderer<TestBlockEntity> {

    public static void init() {
        IKSGBlockEntityRendererUtil.registerRenderer(TestBlockEntity.TEST_BLOCKENTITY, TestRenderer::new);
    }

    public TestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int combinedOverlayIn) {
        VertexConsumer ivb = multiBufferSource.getBuffer(RenderType.solid());
        BakedModel model = IKSGRenderUtil.getBakedModel(new ResourceLocation(OtyacraftEngine.MODID, "block/test_block_or"));

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        IKSGRenderUtil.matrixRotateDegreefX(poseStack, blockEntity.currentValue);
        IKSGRenderUtil.matrixRotateDegreefY(poseStack, blockEntity.currentValue);
        IKSGRenderUtil.matrixRotateDegreefZ(poseStack, blockEntity.currentValue);
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        IKSGRenderUtil.renderBlockBakedModel(model, poseStack, ivb, combinedOverlayIn, blockEntity);
        poseStack.popPose();
    }


}
