package dev.felnull.otyacraftenginetest.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class TestModelsItemRenderer implements BEWLItemRenderer {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        poseStack.pushPose();
        var rnd = RandomSource.create(114514);

        /*for (ModelHolder model : TestModels.TEST_MODELS.getAllHolders()) {

            float rot = OEClientUtils.getParSecond((long) (rnd.nextFloat() * 30000)) * 360f;

            poseStack.pushPose();
            OERenderUtils.poseCenterConsumer(poseStack, 0.5f, 0.5f, 0.5f, pose -> {
                OERenderUtils.poseRotateX(poseStack, rnd.nextFloat() * 360f + rot);
                OERenderUtils.poseRotateY(poseStack, rnd.nextFloat() * 360f + rot);
                OERenderUtils.poseRotateZ(poseStack, rnd.nextFloat() * 360f + rot);
            });
            OERenderUtils.renderModel(poseStack, multiBufferSource.getBuffer(Sheets.solidBlockSheet()), model.get(), light, overlay);
            poseStack.popPose();
        }*/

        poseStack.popPose();
    }
}
