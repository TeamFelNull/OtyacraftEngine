package dev.felnull.otyacraftengine.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.felnull.otyacraftengine.blockentity.TestBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class TestRenderer implements BlockEntityRenderer<TestBlockEntity> {
    public TestRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(TestBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        //     Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.APPLE), ItemTransforms.TransformType.FIXED, i, j, poseStack, multiBufferSource, 0);
        //      var vc = multiBufferSource.getBuffer(RenderType.lines());
        //      LevelRenderer.renderVoxelShape(poseStack, vc, Blocks.ANVIL.defaultBlockState().getShape(blockEntity.getLevel(), blockEntity.getBlockPos()), 0, 0, 0, 0, 0, 0, 0);
    }

    public static void init() {
        BlockEntityRendererRegistry.register(TestBlockEntity.TEST_BLOCKENTITY, TestRenderer::new);
    }
}
