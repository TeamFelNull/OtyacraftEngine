package red.felnull.otyacraftengine.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluids;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.blockentity.TestBlockEntity;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class TestRenderer extends IkisugiBlockEntityRenderer<TestBlockEntity> {

    public static void init() {
        IKSGClientUtil.registerBlockEntityRenderer(TestBlockEntity.TEST_BLOCKENTITY, TestRenderer::new);
    }

    public TestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    private static final ResourceLocation TEST_L = new ResourceLocation(OtyacraftEngine.MODID, "item/test_item");

    @Override
    public void render(TestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        float val = blockEntity.currentValue + (5 * Math.min(partialTicks, 1)); //IKSGRenderUtil.partialTicksMisalignment(blockEntity.currentValue, blockEntity.preCurrentValue, partialTicks);
        //   IKSGRenderUtil.matrixRotateDegreefX(poseStack, val);
        //     IKSGRenderUtil.matrixRotateDegreefY(poseStack, val);
        //     IKSGRenderUtil.matrixRotateDegreefZ(poseStack, val);
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        testRender(blockEntity, partialTicks, poseStack, multiBufferSource, combinedLightIn, combinedOverlayIn);
        poseStack.popPose();
    }

    private static final ResourceLocation BEACON_LOCATION = new ResourceLocation("textures/gui/container/beacon.png");

    private static void testRender(TestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int combinedOverlayIn) {
        float pix = 1f / 16f;
        //   IKSGRenderUtil.renderSpritePanel(BEACON_LOCATION, poseStack, multiBufferSource, -1, 0, 0, 0, 180, 0, pix * 10.5f, pix * 7.5f, 0, 0, 199, 122, 199, 122, combinedOverlayIn, combinedLightIn);
        IKSGRenderUtil.renderFluid(Fluids.LAVA, poseStack, multiBufferSource, false, 0.5d, pix * 1, pix * 1, pix * 3, pix * 14f, pix * 14f, combinedLightIn, combinedOverlayIn);
    }
}
