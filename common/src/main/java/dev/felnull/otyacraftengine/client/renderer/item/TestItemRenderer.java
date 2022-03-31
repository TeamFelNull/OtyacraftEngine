package dev.felnull.otyacraftengine.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.motion.MotionManager;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionSwapper;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.item.TestItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TestItemRenderer implements BEWLItemRenderer {
    private static final MotionPoint START_POINT = new MotionPoint(0.0f, -0.050148662f, 0.0f, 0.0f, 0.0f, 0.0f, 0.475f, -0.8396927f, -0.5500001f, false, false, false);
    private static final MotionPoint END_POINT = new MotionPoint(-1.5375013f, 1.4543099f, -0.47641152f, 0.0f, 0.0f, 0.0f, 0.475f, -0.8396927f, -0.5500001f, false, false, false);
    private static final ResourceLocation TEST_MOTION = new ResourceLocation(OtyacraftEngine.MODID, "test_pat");
    private static final MotionSwapper TEST_SWAPPER = MotionSwapper.swapStartAndEnd(END_POINT, START_POINT);

    public static void init() {
        ItemRendererRegister.register(TestItem.TEST_ITEM, new TestItemRenderer());
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        //   Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.APPLE), transformType, light, overlay, poseStack, multiBufferSource, 0);
        if (Minecraft.getInstance().player != null) {
            poseStack.pushPose();
            var motion = MotionManager.getInstance().getMotion(TEST_MOTION);
            motion.pose(poseStack, OERenderUtil.getParSecond(3000));
            //motion.pose(poseStack, OERenderUtil.getParSecond(3000), TEST_SWAPPER);
            // END_POINT.getPose().pose(poseStack);

            // MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, 0.5f);

            var pos = new BlockPos(Minecraft.getInstance().player.position()).below();
            // var model = OERenderUtil.getBlockModel(Minecraft.getInstance().level.getBlockState(pos));
            // var model = OERenderUtil.getModel(new ModelResourceLocation(new ResourceLocation("apple"), "inventory"));
            var model = OEModelUtil.getModel(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));
            //  int col = BiomeColors.getAverageGrassColor(Minecraft.getInstance().level, pos);
            OERenderUtil.renderModel(poseStack, multiBufferSource.getBuffer(Sheets.cutoutBlockSheet()), model, light, overlay);
            poseStack.popPose();
        }
    }
}
