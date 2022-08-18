package dev.felnull.otyacraftenginetest.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.renderer.blockentity.AbstractBlockEntityRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.blockentity.TestRotedBlockEntity;
import dev.felnull.otyacraftenginetest.client.model.TestModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TestRotedRenderer extends AbstractBlockEntityRenderer<TestRotedBlockEntity> {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(OtyacraftEngineTest.MODID, "textures/gui/test.png");

    protected TestRotedRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestRotedBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        var model = TestModels.SEA_CHICKEN_MODEL.get();
        // OEModelUtils.getModel(Blocks.DIAMOND_BLOCK.defaultBlockState());
        // var vc = multiBufferSource.getBuffer(Sheets.solidBlockSheet());
        var vcc = ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.solidBlockSheet(), true, true);
        float rot = Mth.lerp(f, blockEntity.getRotedOld(), blockEntity.getRoted());
        poseStack.pushPose();

        OERenderUtils.poseCenterConsumer(poseStack, 0.5f, 0.5f, 0.5f, pose -> {
            OERenderUtils.poseRotateX(poseStack, rot);
            OERenderUtils.poseRotateY(poseStack, rot);
            OERenderUtils.poseRotateZ(poseStack, rot);
        });
        //VertexConsumer tvc = multiBufferSource.getBuffer(OERenderTypes.wave(TEST_TEXTURE));
        //OERenderUtils.renderColorfulSprite(poseStack, tvc, 1, 1, 0, 0, 1, 1, 1, 1, 0xFFFFFFFF, i, j);

        OERenderUtils.renderModel(poseStack, vcc, model, i, j);
        poseStack.popPose();
    }
}
