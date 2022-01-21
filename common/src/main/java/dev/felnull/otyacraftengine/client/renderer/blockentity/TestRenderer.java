package dev.felnull.otyacraftengine.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.felnull.otyacraftengine.blockentity.TestBlockEntity;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class TestRenderer extends AbstractBlockEntityRenderer<TestBlockEntity> {

    public TestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        // OERenderUtil.renderTextSprite(poseStack, multiBufferSource, new TextComponent("TEST"), 0, 0, 0, 1f, 0, 0, 0xFF0000FF, i);
        //     Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.APPLE), ItemTransforms.TransformType.FIXED, i, j, poseStack, multiBufferSource, 0);
        //      var vc = multiBufferSource.getBuffer(RenderType.lines());
        //      LevelRenderer.renderVoxelShape(poseStack, vc, Blocks.ANVIL.defaultBlockState().getShape(blockEntity.getLevel(), blockEntity.getBlockPos()), 0, 0, 0, 0, 0, 0, 0);
        var model = OERenderUtil.getBlockModel(Blocks.ANVIL.defaultBlockState());
        var vc = multiBufferSource.getBuffer(RenderType.solid());
        poseStack.pushPose();
        float rot = Mth.lerp(f, blockEntity.getOldRoted(), blockEntity.getRoted());
        poseStack.translate(0.5f, 0, 0.5f);
        OERenderUtil.poseRotateY(poseStack, rot);
        poseStack.translate(-0.5f, 0, -0.5f);
        OERenderUtil.renderModel(poseStack, vc, model, i, j);
        poseStack.popPose();
    }

    public static void init() {
        BlockEntityRendererRegistry.register(TestBlockEntity.TEST_BLOCKENTITY, TestRenderer::new);
    }
}
