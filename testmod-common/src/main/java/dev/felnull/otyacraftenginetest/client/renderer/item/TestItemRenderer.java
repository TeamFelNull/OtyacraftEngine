package dev.felnull.otyacraftenginetest.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftenginetest.client.model.TestModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class TestItemRenderer implements BEWLItemRenderer {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        poseStack.pushPose();
        float rot = 360f * OEClientUtils.getParSecond(3000);

        OERenderUtils.poseCenterConsumer(poseStack, 0.5f, 0.5f, 0.5f, pose -> {
            OERenderUtils.poseRotateX(poseStack, rot);
            OERenderUtils.poseRotateY(poseStack, rot);
            OERenderUtils.poseRotateZ(poseStack, rot);
        });

        var model = TestModels.SEA_CHICKEN_MODEL.get();
        var vc = multiBufferSource.getBuffer(Sheets.solidBlockSheet());
        OERenderUtils.renderModel(poseStack, vc, model, light, overlay);

        poseStack.popPose();
    }
}
