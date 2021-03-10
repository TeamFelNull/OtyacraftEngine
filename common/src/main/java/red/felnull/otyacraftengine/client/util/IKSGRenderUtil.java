package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IKSGRenderUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<ResourceLocation, BakedModel> BAKED_MODELS = new HashMap<>();

    public static void drawPlayerFase(PoseStack psstack, String name, int x, int y) {
        psstack.pushPose();
        ResourceLocation plskin = IKSGTextureUtil.getPlayerSkinTexture(name);
        guiBindAndBlit(plskin, psstack, x, y, 8, 8, 8, 8, 64, 64);
        guiBindAndBlit(plskin, psstack, x, y, 40, 8, 8, 8, 64, 64);
        psstack.popPose();
    }

    public static void guiBindAndBlit(ResourceLocation location, PoseStack psstack, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight) {
        guiBindAndBlit(location, psstack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, 256, 256);
    }

    public static void guiBindAndBlit(ResourceLocation location, PoseStack psstack, int x, int y, float textureStartX, float textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSizeX, int textureSizeY) {
        psstack.pushPose();
        mc.getTextureManager().bind(location);
        GuiComponent.blit(psstack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
        psstack.popPose();
    }


    public static void matrixTranslatef16Divisions(PoseStack psstack, double x, double y, double z) {
        float pix = 1f / 16f;
        psstack.translate(pix * x, pix * y, pix * z);
    }

    public static void matrixScalf(PoseStack psstack, float allsclae) {
        psstack.scale(allsclae, allsclae, allsclae);
    }

    public static void matrixRotateDegreef(PoseStack psstack, float x, float y, float z) {
        matrixRotateDegreefX(psstack, x);
        matrixRotateDegreefY(psstack, y);
        matrixRotateDegreefZ(psstack, z);
    }

    public static void matrixRotateDegreefX(PoseStack psstack, float x) {
        psstack.mulPose(Vector3f.XP.rotationDegrees(x));
    }

    public static void matrixRotateDegreefY(PoseStack psstack, float y) {
        psstack.mulPose(Vector3f.YP.rotationDegrees(y));
    }

    public static void matrixRotateDegreefZ(PoseStack psstack, float z) {
        psstack.mulPose(Vector3f.ZP.rotationDegrees(z));
    }

    public static void matrixRotateHorizontal(PoseStack psstack, BlockState state) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        matrixRotateDirection(psstack, direction);
    }

    public static void matrixRotateDirection(PoseStack psstack, Direction direction) {
        if (direction == Direction.WEST) {
            matrixRotateDegreefY(psstack, 180);
            psstack.translate(-1f, 0f, -1f);
        } else if (direction == Direction.NORTH) {
            matrixRotateDegreefY(psstack, 90);
            psstack.translate(-1f, 0f, 0f);
        } else if (direction == Direction.SOUTH) {
            matrixRotateDegreefY(psstack, 270);
            psstack.translate(0f, 0f, -1f);
        }
    }

    public static ModelBakery getModelBakery() {
        return OEClientExpectPlatform.getModelBakery();
    }

    public static BakedModel getBakedModel(ResourceLocation location) {

        if (BAKED_MODELS.containsKey(location))
            return BAKED_MODELS.get(location);

        BakedModel model = getModelBakery().bake(location, BlockModelRotation.X0_Y0);
        BAKED_MODELS.put(location, model);
        return model;
    }

    public static void renderBlockBakedModel(BlockAndTintGetter getter, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, Random randomIn, long rand, int combinedOverlayIn) {
        BlockRenderDispatcher brd = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer bmr = brd.getModelRenderer();
        bmr.tesselateBlock(getter, model, state, pos, poseStack, consumer, checkSides, randomIn, rand, combinedOverlayIn);
    }

    public static void renderBlockBakedModel(BlockAndTintGetter getter, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, Random randomIn, int combinedOverlayIn) {
        renderBlockBakedModel(getter, model, state, pos, poseStack, consumer, false, randomIn, 0, combinedOverlayIn);
    }

    public static void renderBlockBakedModel(BakedModel model, PoseStack poseStack, VertexConsumer consumer, int combinedOverlayIn, BlockEntity tile) {
        renderBlockBakedModel(tile.getLevel(), model, tile.getBlockState(), tile.getBlockPos(), poseStack, consumer, tile.getLevel().random, combinedOverlayIn);
    }

}
