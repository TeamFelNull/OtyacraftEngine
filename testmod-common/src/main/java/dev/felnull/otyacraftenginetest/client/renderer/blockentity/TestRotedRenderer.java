package dev.felnull.otyacraftenginetest.client.renderer.blockentity;


import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.renderer.blockentity.AbstractBlockEntityRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.blockentity.TestRotedBlockEntity;
import dev.felnull.otyacraftenginetest.client.model.TestModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.UUID;

public class TestRotedRenderer extends AbstractBlockEntityRenderer<TestRotedBlockEntity> {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(OtyacraftEngineTest.MODID, "textures/gui/test.png");
    private static final Minecraft mc = Minecraft.getInstance();
    private static final UUID morimoriUUID = UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03");
    private static final String morimoriName = "MoriMori_0317_jp";

    protected TestRotedRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestRotedBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        var model = TestModels.SEA_CHICKEN_MODEL.get();
        // OEModelUtils.getModel(Blocks.DIAMOND_BLOCK.defaultBlockState());
        // var vc = multiBufferSource.getBuffer(Sheets.solidBlockSheet());

        float rot = Mth.lerp(f, blockEntity.getRotedOld(), blockEntity.getRoted());
        poseStack.pushPose();

        OERenderUtils.poseCenterConsumer(poseStack, 0.5f, 0.5f, 0.5f, pose -> {
            OERenderUtils.poseRotateX(poseStack, rot);
            OERenderUtils.poseRotateY(poseStack, rot);
            OERenderUtils.poseRotateZ(poseStack, rot);
        });
        //VertexConsumer tvc = multiBufferSource.getBuffer(OERenderTypes.wave(TEST_TEXTURE));
        //OERenderUtils.renderColorfulSprite(poseStack, tvc, 1, 1, 0, 0, 1, 1, 1, 1, 0xFFFFFFFF, i, j);

        var vcc = ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.solidBlockSheet(), true, true);
        //var vcc = multiBufferSource.getBuffer(OERenderTypes.wave(TextureAtlas.LOCATION_BLOCKS));//  var vcc = ItemRenderer.getFoilBufferDirect(multiBufferSource, OERenderTypes.wave(TextureAtlas.LOCATION_BLOCKS), true, true);
        OERenderUtils.renderModel(poseStack, vcc, model, light, overlay);
        // testPlayerFace(poseStack, multiBufferSource, light, overlay);
        poseStack.popPose();
        // testPlayerFace(poseStack, multiBufferSource, light, overlay);
    }

    private static void testRenderFont(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        OERenderUtils.renderFontSprite(Component.literal("ikisugi"), 0, 0, 0xFFFFFFFF, false, poseStack, multiBufferSource, false, 0, light);
    }

    private static void testPlayerFace(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        OERenderUtils.renderPlayerFaceSprite(poseStack, multiBufferSource, "MoriMori_0317_jp", 1, light, overlay);
    }
}

