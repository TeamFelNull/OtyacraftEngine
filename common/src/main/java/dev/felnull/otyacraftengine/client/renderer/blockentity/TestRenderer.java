package dev.felnull.otyacraftengine.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.blockentity.TestBlockEntity;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
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
        var model = OEModelUtil.getModel(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));//OERenderUtil.getBlockModel(Blocks.STONE.defaultBlockState());
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());
        poseStack.pushPose();
        float rot = Mth.lerp(f, blockEntity.getOldRoted(), blockEntity.getRoted());

        if (Minecraft.getInstance().level.getBlockState(blockEntity.getBlockPos().below()).getBlock() == Blocks.ANVIL) {
            rot = blockEntity.getRoted();
        } else if (Minecraft.getInstance().level.getBlockState(blockEntity.getBlockPos().below()).getBlock() == Blocks.DIAMOND_BLOCK) {
            rot = Mth.lerp(f, blockEntity.getRoted() - 3, blockEntity.getRoted());
        }
        poseStack.translate(0.5f, 0.5f, 0.5f);
        OERenderUtil.poseRotateX(poseStack, rot);
        OERenderUtil.poseRotateY(poseStack, rot);
        OERenderUtil.poseRotateZ(poseStack, rot);

        poseStack.translate(-0.5f, -0.5f, -0.5f);
        OERenderUtil.renderModel(poseStack, vc, model, i, j);
        poseStack.popPose();
    }

    public static void init() {
        BlockEntityRendererRegistry.register(TestBlockEntity.TEST_BLOCKENTITY.get(), TestRenderer::new);
    }
}
