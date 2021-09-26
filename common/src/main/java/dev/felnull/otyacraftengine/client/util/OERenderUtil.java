package dev.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 描画系を簡易に実装させる
 *
 * @author MORIMORI0317
 * @since 1.0
 */
@Environment(EnvType.CLIENT)
public class OERenderUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<ResourceLocation, BakedModel> BAKED_MODELS = new HashMap<>();

    /**
     * 文字スプライトを描画
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param text              文字
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     */
    public static void renderCenterTextSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, Component text, float x, float y, float z, float size, float textX, float textY) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
        mc.font.drawInBatch(text, ((float) -mc.font.width(text) / 2f) + textX, -mc.font.lineHeight + textY, 0, false, poseStack.last().pose(), multiBufferSource, false, 0, 0);
        poseStack.popPose();
    }

    /**
     * 文字スプライトを描画
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param text              文字
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     */
    public static void renderTextSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, Component text, float x, float y, float z, float size, float textX, float textY) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
        mc.font.drawInBatch(text, textX, -mc.font.lineHeight + textY, 0, false, poseStack.last().pose(), multiBufferSource, false, 0, 0);
        poseStack.popPose();
    }

    /**
     * 中央ぞろえ文字描画
     *
     * @param poseStack PoseStack
     * @param text      文字
     * @param x         中央X
     * @param y         Y
     * @param color     色(SRGB)
     * @since 2.0
     */
    public static void drawCenterText(PoseStack poseStack, Component text, float x, float y, int color) {
        mc.font.draw(poseStack, text, x - ((float) mc.font.width(text) / 2f), y, color);
    }

    /**
     * 文字描画
     *
     * @param poseStack PoseStack
     * @param text      文字
     * @param x         X
     * @param y         Y
     * @param color     色(SRGB)
     * @since 2.0
     */
    public static void drawText(PoseStack poseStack, Component text, float x, float y, int color) {
        mc.font.draw(poseStack, text, x, y, color);
    }


    /**
     * 中央ぞろえ文字描画
     *
     * @param poseStack PoseStack
     * @param str       文字
     * @param x         中央X
     * @param y         Y
     * @param color     色(SRGB)
     * @since 2.0
     */
    public static void drawCenterText(PoseStack poseStack, String str, float x, float y, int color) {
        mc.font.draw(poseStack, str, x - ((float) mc.font.width(str) / 2f), y, color);
    }

    /**
     * 文字描画
     *
     * @param poseStack PoseStack
     * @param str       文字
     * @param x         X
     * @param y         Y
     * @param color     色(SRGB)
     * @since 2.0
     */
    public static void drawText(PoseStack poseStack, String str, float x, float y, int color) {
        mc.font.draw(poseStack, str, x, y, color);
    }

    /**
     * GUI上でUUIDから取得したプレイヤーの顔を描画する
     *
     * @param poseStack PoseStack
     * @param uuid      プレイヤーUUID
     * @param x         X
     * @param y         Y
     * @since 2.0
     */
  /*  public static void drawPlayerFace(PoseStack poseStack, UUID uuid, float x, float y) {
        drawPlayerFace(poseStack, uuid, x, y, 8);
    }*/

    /**
     * GUI上でUUIDから取得したプレイヤーの顔を描画する
     * サイズ変更可
     *
     * @param poseStack PoseStack
     * @param uuid      プレイヤーUUID
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @since 2.0
     */
  /*  public static void drawPlayerFace(PoseStack poseStack, UUID uuid, float x, float y, float size) {
        poseStack.pushPose();
        float sc = size / 8f;
        ResourceLocation plskin = OETextureUtil.getPlayerSkinTexture(uuid);
        drawTexture(plskin, poseStack, x, y, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        drawTexture(plskin, poseStack, x, y, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        poseStack.popPose();
    }
*/

    /**
     * GUI上で名前から取得したプレイヤーの顔を描画する
     *
     * @param poseStack PoseStack
     * @param name      プレイヤー名
     * @param x         X
     * @param y         Y
     */
   /* public static void drawPlayerFace(PoseStack poseStack, String name, float x, float y) {
        drawPlayerFace(poseStack, name, x, y, 8);
    }*/

    /**
     * GUI上で名前から取得したプレイヤーの顔を描画する
     * サイズ変更可
     *
     * @param poseStack PoseStack
     * @param name      プレイヤー名
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @since 2.0
     */
   /* public static void drawPlayerFace(PoseStack poseStack, String name, float x, float y, float size) {
        poseStack.pushPose();
        float sc = size / 8f;
        ResourceLocation plskin = IKSGTextureUtil.getPlayerSkinTexture(name);
        drawTexture(plskin, poseStack, x, y, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        drawTexture(plskin, poseStack, x, y, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        poseStack.popPose();
    }*/

    /**
     * GUI上でテクスチャを描画する
     *
     * @param location            テクスチャ
     * @param poseStack           PoseStack
     * @param x                   X
     * @param y                   Y
     * @param textureStartX       テクスチャの開始地点X
     * @param textureStartY       テクスチャの開始地点Y
     * @param textureFinishWidth  テクスチャの終了地点X
     * @param textureFinishHeight テクスチャの終了地点Y
     */
    public static void drawTexture(ResourceLocation location, PoseStack poseStack, float x, float y, float textureStartX, float textureStartY, float textureFinishWidth, float textureFinishHeight) {
        drawTexture(location, poseStack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, 256f, 256f);
    }

    /**
     * GUI上でテクスチャを描画する
     *
     * @param location            テクスチャ
     * @param poseStack           PoseStack
     * @param x                   X
     * @param y                   Y
     * @param textureStartX       テクスチャの開始地点X
     * @param textureStartY       テクスチャの開始地点Y
     * @param textureFinishWidth  テクスチャの終了地点X
     * @param textureFinishHeight テクスチャの終了地点Y
     * @param textureSizeX        テクスチャの横サイズ
     * @param textureSizeY        テクスチャの縦サイズ
     */
    public static void drawTexture(ResourceLocation location, PoseStack poseStack, float x, float y, float textureStartX, float textureStartY, float textureFinishWidth, float textureFinishHeight, float textureSizeX, float textureSizeY) {
        poseStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, location);
        fBlit(poseStack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
        poseStack.popPose();
    }

    /**
     * GUI上でテクスチャを着色して描画する
     *
     * @param location            テクスチャ
     * @param poseStack           PoseStack
     * @param x                   X
     * @param y                   Y
     * @param textureStartX       テクスチャの開始地点X
     * @param textureStartY       テクスチャの開始地点Y
     * @param textureFinishWidth  テクスチャの終了地点X
     * @param textureFinishHeight テクスチャの終了地点Y
     * @param color               色(SRGB)
     * @since 2.0
     */
    public static void drawColorTexture(ResourceLocation location, PoseStack poseStack, float x, float y, float textureStartX, float textureStartY, float textureFinishWidth, float textureFinishHeight, int color) {
        //       drawColorTexture(location, poseStack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, 256, 256, color);
    }

    /**
     * GUI上でテクスチャを着色して描画する
     *
     * @param location            テクスチャ
     * @param poseStack           PoseStack
     * @param x                   X
     * @param y                   Y
     * @param textureStartX       テクスチャの開始地点X
     * @param textureStartY       テクスチャの開始地点Y
     * @param textureFinishWidth  テクスチャの終了地点X
     * @param textureFinishHeight テクスチャの終了地点Y
     * @param textureSizeX        テクスチャの横サイズ
     * @param textureSizeY        テクスチャの縦サイズ
     * @param color               色(SRGB)
     * @since 2.0
     */
    public static void drawColorTexture(ResourceLocation location, PoseStack poseStack, float x, float y, float textureStartX, float textureStartY, float textureFinishWidth, float textureFinishHeight, float textureSizeX, float textureSizeY, int color) {
   /*     float r = (float) IKSGColorUtil.getRed(color) / 255f;
        float g = (float) IKSGColorUtil.getGreen(color) / 255f;
        float b = (float) IKSGColorUtil.getBlue(color) / 255f;
        float a = (float) IKSGColorUtil.getAlpha(color) / 255f;
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(r, g, b, a);
        RenderSystem.setShaderTexture(0, location);
        fBlit(poseStack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        poseStack.popPose();*/
    }
    private static void fBlit(PoseStack poseStack, float ix, float iy, float tsx, float tsy, float tw, float th, float tssx, float tssy) {
        Matrix4f matrix4f = poseStack.last().pose();
        float x = ix;
        float y = ix + tw;
        float w = iy;
        float h = iy + th;
        float u1 = tsx / tssx;
        float u2 = (tsx + tw) / tssx;
        float v1 = tsy / tssy;
        float v2 = (tsy + th) / tssy;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, h, 0).uv(u1, v2).endVertex();
        bufferBuilder.vertex(matrix4f, y, h, 0).uv(u2, v2).endVertex();
        bufferBuilder.vertex(matrix4f, y, w, 0).uv(u2, v1).endVertex();
        bufferBuilder.vertex(matrix4f, x, w, 0).uv(u1, v1).endVertex();
        bufferBuilder.end();
        BufferUploader.end(bufferBuilder);
    }

    /**
     * PoseStackを16分の１単位で移動する
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param z         Z
     */
    public static void poseTrans16(PoseStack poseStack, double x, double y, double z) {
        float pix = 1f / 16f;
        poseStack.translate(pix * x, pix * y, pix * z);
    }

    /**
     * PoseStackをすべてのスケールを設定する
     *
     * @param poseStack PoseStack
     * @param scale     すべてのスケール
     */
    public static void poseScaleAll(PoseStack poseStack, float scale) {
        poseStack.scale(scale, scale, scale);
    }

    /**
     * PoseStackの角度をそれぞれ設定する
     *
     * @param poseStack PoseStack
     * @param x         X角度
     * @param y         Y角度
     * @param z         Z角度
     */
    public static void poseRotateAll(PoseStack poseStack, float x, float y, float z) {
        poseRotateX(poseStack, x);
        poseRotateY(poseStack, y);
        poseRotateZ(poseStack, z);
    }

    /**
     * PoseStackのX角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateX(PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.XP.rotationDegrees(angle));
    }

    /**
     * PoseStackのY角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateY(PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));
    }

    /**
     * PoseStackのZ角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateZ(PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(angle));
    }

    /**
     * PoseStackの方向のブロックステートに設定する
     *
     * @param poseStack PoseStack
     * @param state     角度
     * @param roted     回転ずれ
     */
    public static void poseRotateHorizontalState(PoseStack poseStack, BlockState state, int roted) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        poseRotateDirection(poseStack, direction, roted);
    }

    /**
     * PoseStackをDirectionの方向にする
     *
     * @param poseStack PoseStack
     * @param direction 方向
     * @param roted     回転ずれ
     */
    public static void poseRotateDirection(PoseStack poseStack, Direction direction, int roted) {
        for (int i = 0; i < roted; i++) {
            direction = direction.getClockWise();
        }
        if (direction == Direction.WEST) {
            poseRotateY(poseStack, 180);
            poseStack.translate(-1f, 0f, -1f);
        } else if (direction == Direction.NORTH) {
            poseRotateY(poseStack, 90);
            poseStack.translate(-1f, 0f, 0f);
        } else if (direction == Direction.SOUTH) {
            poseRotateY(poseStack, 270);
            poseStack.translate(0f, 0f, -1f);
        }
    }

    /**
     * ModelBakeryを取得
     *
     * @return ModelBakery
     * @since 2.0
     */
  /*  public static ModelBakery getModelBakery() {
        return OEClientExpectPlatform.getModelBakery();
    }*/

    /**
     * BakedModelを取得
     *
     * @param location ID
     * @return BakedModel
     */
 /*   public static BakedModel getBakedModel(ResourceLocation location) {

        if (BAKED_MODELS.containsKey(location))
            return BAKED_MODELS.get(location);

        BakedModel model = getModelBakery().bake(location, BlockModelRotation.X0_Y0);
        BAKED_MODELS.put(location, model);
        return model;
    }*/

    /**
     * ブロック状態でBakedModelを描画する
     *
     * @param getter            BlockAndTintGetter
     * @param model             描画するモデル
     * @param state             ブロックステート
     * @param pos               場所
     * @param poseStack         PoseStack
     * @param consumer          VertexConsumer
     * @param checkSides        checkSides
     * @param randomIn          Random
     * @param rand              rand
     * @param combinedOverlayIn combinedOverlayIn
     */
    public static void renderBlockBakedModel(BlockAndTintGetter getter, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, Random randomIn, long rand, int combinedOverlayIn) {
        BlockRenderDispatcher brd = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer bmr = brd.getModelRenderer();
        bmr.tesselateBlock(getter, model, state, pos, poseStack, consumer, checkSides, randomIn, rand, combinedOverlayIn);
    }

    /**
     * ブロック状態でBakedModelを描画する
     *
     * @param getter            BlockAndTintGetter
     * @param model             描画するモデル
     * @param state             ブロックステート
     * @param pos               場所
     * @param poseStack         PoseStack
     * @param consumer          VertexConsumer
     * @param randomIn          Random
     * @param combinedOverlayIn combinedOverlayIn
     */
    public static void renderBlockBakedModel(BlockAndTintGetter getter, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, Random randomIn, int combinedOverlayIn) {
        renderBlockBakedModel(getter, model, state, pos, poseStack, consumer, false, randomIn, 0, combinedOverlayIn);
    }

    /**
     * ブロック状態でBakedModelを描画する
     *
     * @param model             描画するモデル
     * @param poseStack         PoseStack
     * @param consumer          VertexConsumer
     * @param combinedOverlayIn combinedOverlayIn
     * @param blockentity       ブロックエンティティ
     */
    public static void renderBlockBakedModel(BakedModel model, PoseStack poseStack, VertexConsumer consumer, int combinedOverlayIn, BlockEntity blockentity) {
        renderBlockBakedModel(blockentity.getLevel(), model, blockentity.getBlockState(), blockentity.getBlockPos(), poseStack, consumer, blockentity.getLevel().random, combinedOverlayIn);
    }

    /*
    Mth.lerp
        public static float partialTicksMisalignment(float val, float prevVal, float partialTicks) {
            RemotePlayer pl=null;
            return val + (prevVal - val) * 1;
        }
    */

    /**
     * BakedModelを取得
     *
     * @param modelResourceLocation ID;
     * @return BakedModel
     * @since 2.0
     */
    public static BakedModel getBlockBakedModel(ModelResourceLocation modelResourceLocation) {
        BlockRenderDispatcher blockrendererdispatcher = mc.getBlockRenderer();
        ModelManager modelmanager = blockrendererdispatcher.getBlockModelShaper().getModelManager();
        return modelmanager.getModel(modelResourceLocation);
    }

    /**
     * BakedModelを描画
     *
     * @param poseStack       PoseStack
     * @param vertexConsumer  VertexConsumer
     * @param state           ブロックステート
     * @param bakedModel      モデル
     * @param combinedLight   CombinedLight
     * @param combinedOverlay CombinedOverlay
     * @since 2.0
     */
    public static void renderBakedModel(PoseStack poseStack, VertexConsumer vertexConsumer, BlockState state, BakedModel bakedModel, int combinedLight, int combinedOverlay) {
        BlockRenderDispatcher brd = mc.getBlockRenderer();
        ModelBlockRenderer bmr = brd.getModelRenderer();
        bmr.renderModel(poseStack.last(), vertexConsumer, state, bakedModel, 1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay);
    }

    /**
     * 着色してBakedModelを描画
     * モデルファイルのテクスチャ指定にtintindexが必要、詳しくは葉ブロックのモデルjson参照
     *
     * @param poseStack       PoseStack
     * @param vertexConsumer  VertexConsumer
     * @param state           ブロックステート
     * @param bakedModel      モデル
     * @param combinedLight   CombinedLight
     * @param combinedOverlay CombinedOverlay
     * @param color           色
     * @since 2.0
     */
    public static void renderColorBakedModel(PoseStack poseStack, VertexConsumer vertexConsumer, BlockState state, BakedModel bakedModel, int combinedLight, int combinedOverlay, int color) {
        BlockRenderDispatcher brd = mc.getBlockRenderer();
        ModelBlockRenderer bmr = brd.getModelRenderer();
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        bmr.renderModel(poseStack.last(), vertexConsumer, state, bakedModel, r, g, b, combinedLight, combinedOverlay);
    }

    /**
     * テクスチャからレンダータイプ取得
     *
     * @param locationIn テクスチャ
     * @return レンダータイプ
     * @since 2.0
     */
    public static RenderType getTextureRenderType(ResourceLocation locationIn) {
        return RenderType.text(locationIn);
    }

    /**
     * UUIDから取得したプレイヤーの顔スプライトを描画する
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param uuid              プレイヤーUUID
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderPlayerFaceSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, UUID uuid, float x, float y, float z, float pitch, float yaw, float roll, float size, int combinedLightIn, int combinedOverlayIn) {
        float sc = size / 8f;
        //      renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(uuid), poseStack, multiBufferSource, x, y, z, pitch, yaw, roll, size, size, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        //       renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(uuid), poseStack, multiBufferSource, x, y, z + Mth.EPSILON, pitch, yaw, roll, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
    }

    /**
     * 名前から取得したプレイヤーの顔スプライトを描画する
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param name              プレイヤー名
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderPlayerFaceSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, String name, float x, float y, float z, float pitch, float yaw, float roll, float size, int combinedLightIn, int combinedOverlayIn) {
        float sc = size / 8f;
        ////     renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(name), poseStack, multiBufferSource, x, y, z, pitch, yaw, roll, size, size, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        //      renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(name), poseStack, multiBufferSource, x, y, z + Mth.EPSILON, pitch, yaw, roll, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
    }

    /**
     * UUIDから取得したプレイヤーの顔スプライトを両面に描画する
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param uuid              プレイヤーUUID
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderPlayerFaceSpriteSides(PoseStack poseStack, MultiBufferSource multiBufferSource, UUID uuid, float x, float y, float z, float pitch, float yaw, float roll, float size, int combinedLightIn, int combinedOverlayIn) {
        float sc = size / 8f;
        //    renderTextureSpriteSides(IKSGTextureUtil.getPlayerSkinTexture(uuid), poseStack, multiBufferSource, x, y, z, pitch, yaw, roll, size, size, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        //     renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(uuid), poseStack, multiBufferSource, x, y, z + Mth.EPSILON, pitch, yaw, roll, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        //     renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(uuid), poseStack, multiBufferSource, x + 1f, y, z - (Mth.EPSILON * 2), pitch, yaw + 180, roll, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
    }

    /**
     * 名前から取得したプレイヤーの顔スプライトを両面に描画する
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param name              プレイヤー名
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderPlayerFaceSpriteSides(PoseStack poseStack, MultiBufferSource multiBufferSource, String name, float x, float y, float z, float pitch, float yaw, float roll, float size, int combinedLightIn, int combinedOverlayIn) {
        float sc = size / 8f;
        //   renderTextureSpriteSides(IKSGTextureUtil.getPlayerSkinTexture(name), poseStack, multiBufferSource, x, y, z, pitch, yaw, roll, size, size, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        //    renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(name), poseStack, multiBufferSource, x, y, z + Mth.EPSILON, pitch, yaw, roll, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        //    renderTextureSprite(IKSGTextureUtil.getPlayerSkinTexture(name), poseStack, multiBufferSource, x + 1f, y, z - (Mth.EPSILON * 2), pitch, yaw + 180, roll, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
    }

    /**
     * テクスチャスプライトを描画する
     *
     * @param location          テクスチャ
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param w                 幅
     * @param h                 高さ
     * @param texStartX         テクスチャ開始X
     * @param texStartY         テクスチャ開始Y
     * @param texFinishX        テクスチャ終了X
     * @param texFinishY        テクスチャ終了Y
     * @param texSizeW          テクスチャサイズ幅
     * @param texSizeH          テクスチャサイズ高さ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     */
    public static void renderTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, float texSizeW, float texSizeH, int combinedLightIn, int combinedOverlayIn) {
        renderColorTextureSprite(location, poseStack, multiBufferSource, x, y, z, 1f, 1f, 1f, 1f, pitch, yaw, roll, w, h, texStartX, texStartY, texFinishX, texFinishY, texSizeW, texSizeH, combinedLightIn, combinedOverlayIn);
    }

    /**
     * 着色してテクスチャスプライトを描画する
     *
     * @param location          テクスチャ
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param r                 赤色
     * @param g                 緑色
     * @param b                 青色
     * @param a                 透明度
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param w                 幅
     * @param h                 高さ
     * @param texStartX         テクスチャ開始X
     * @param texStartY         テクスチャ開始Y
     * @param texFinishX        テクスチャ終了X
     * @param texFinishY        テクスチャ終了Y
     * @param texSizeW          テクスチャサイズ幅
     * @param texSizeH          テクスチャサイズ高さ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderColorTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float r, float g, float b, float a, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, float texSizeW, float texSizeH, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseRotateY(poseStack, yaw);
        poseRotateX(poseStack, pitch);
        poseRotateZ(poseStack, roll);
        VertexConsumer vc = multiBufferSource.getBuffer(getTextureRenderType(location));
        float wst = texStartX / texSizeW;
        float wft = texFinishX / texSizeW + wst;
        float hst = texStartY / texSizeH;
        float hft = texFinishY / texSizeH + hst;
        PoseStack.Pose pose = poseStack.last();
        vertex(vc, pose, 0, 0, 0, wst, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vc, pose, w, 0, 0, wft, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vc, pose, w, h, 0, wft, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vc, pose, 0, h, 0, wst, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
        poseStack.popPose();
    }


    /**
     * テクスチャスプライトを両面に描画する
     *
     * @param location          テクスチャ
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param pitch             角度pitch
     * @param yaw               角度yaw
     * @param roll              角度roll
     * @param w                 幅
     * @param h                 高さ
     * @param texStartX         テクスチャ開始X
     * @param texStartY         テクスチャ開始Y
     * @param texFinishX        テクスチャ終了X
     * @param texFinishY        テクスチャ終了Y
     * @param texSizeW          テクスチャサイズ幅
     * @param texSizeH          テクスチャサイズ高さ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     */
    public static void renderTextureSpriteSides(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, float texSizeW, float texSizeH, int combinedLightIn, int combinedOverlayIn) {
        renderTextureSprite(location, poseStack, multiBufferSource, x, y, z, pitch, yaw, roll, w, h, texStartX, texStartY, texFinishX, texFinishY, texSizeW, texSizeH, combinedLightIn, combinedOverlayIn);
        renderTextureSprite(location, poseStack, multiBufferSource, x + 1f, y, z - Mth.EPSILON, pitch, yaw + 180, roll, w, h, texStartX, texStartY, texFinishX, texFinishY, texSizeW, texSizeH, combinedLightIn, combinedOverlayIn);
    }


    @Deprecated
    private static void renderTextureSpriteOld(TextureAtlasSprite sprite, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float r, float g, float b, float a, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, int combinedOverlayIn, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseRotateY(poseStack, yaw);
        poseRotateX(poseStack, pitch);
        poseRotateZ(poseStack, roll);
        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.text(sprite.atlas().location()));

        PoseStack.Pose pose = poseStack.last();
        vertex(vc, pose, 0, 0, 0, texStartX, texStartY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vc, pose, w, 0, 0, texFinishX, texStartY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vc, pose, w, h, 0, texFinishX, texFinishY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vc, pose, 0, h, 0, texStartX, texFinishY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        poseStack.popPose();
    }

    private static void vertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float r, float g, float b, float a, int combinedOverlayIn, int combinedLightIn) {
        builder.vertex(pose.pose(), x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(combinedOverlayIn).uv2(combinedLightIn).normal(pose.normal(), 0f, 0f, 0f).endVertex();
    }

    /**
     * 液体を描画する
     *
     * @param fluid             液体
     * @param getter            BlockAndTintGetter
     * @param pos               BlockPos
     * @param poseStack         PoseStack
     * @param mbs               MultiBufferSource
     * @param parsent           液体の満たされてる量
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param w                 幅
     * @param h                 高さ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderFluid(Fluid fluid, BlockAndTintGetter getter, BlockPos pos, PoseStack poseStack, MultiBufferSource mbs, double parsent, float x, float y, float z, float w, float h, int combinedLightIn, int combinedOverlayIn) {
        //   if (fluid instanceof IIkisugibleFluid) {
        //        if (getter != null && pos != null)
        //             renderFluid(fluid, ((IIkisugibleFluid) fluid).getProperties().getWorldColor(getter, pos), poseStack, mbs, parsent, x, y, z, w, h, combinedLightIn, combinedOverlayIn);
        //         else
        //            renderFluid(fluid, poseStack, mbs, parsent, x, y, z, w, h, combinedLightIn, combinedOverlayIn);
        //    }
    }

    /**
     * 液体を描画する
     *
     * @param fluid             液体
     * @param poseStack         PoseStack
     * @param mbs               MultiBufferSource
     * @param parsent           液体の満たされてる量
     * @param x                 X
     * @param y                 Y
     * @param z                 Z
     * @param w                 幅
     * @param h                 高さ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderFluid(Fluid fluid, PoseStack poseStack, MultiBufferSource mbs, double parsent, float x, float y, float z, float w, float h, int combinedLightIn, int combinedOverlayIn) {
        //     if (fluid instanceof IIkisugibleFluid) {
        //         renderFluid(fluid, ((IIkisugibleFluid) fluid).getProperties().getColor(), poseStack, mbs, parsent, x, y, z, w, h, combinedLightIn, combinedOverlayIn);
        //      }
    }

    private static void renderFluid(Fluid fluid, int color, PoseStack poseStack, MultiBufferSource mbs, double parsent, float x, float y, float z, float w, float h, int combinedLightIn, int combinedOverlayIn) {
   /*     ResourceLocation location = ((IIkisugibleFluid) fluid).getProperties().getStillTexture();
        TextureAtlasSprite sprite = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);
        float r = (float) IKSGColorUtil.getRed(color) / 255f;
        float g = (float) IKSGColorUtil.getGreen(color) / 255f;
        float b = (float) IKSGColorUtil.getBlue(color) / 255f;
        float a = 1;//(float) IKSGColorUtil.getAlpha(color) / 255f;

        float hight = (float) (h * parsent);

        IKSGRenderUtil.renderTextureSpriteOld(sprite, poseStack, mbs, x, y + hight, z + w, r, g, b, a, -90, 0, 0, w, w, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * h), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderTextureSpriteOld(sprite, poseStack, mbs, x, y, z, r, g, b, a, -270, 0, 0, w, w, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * h), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderTextureSpriteOld(sprite, poseStack, mbs, x + w, y, z, r, g, b, a, 0, 180, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderTextureSpriteOld(sprite, poseStack, mbs, x, y, z, r, g, b, a, 0, 270, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderTextureSpriteOld(sprite, poseStack, mbs, x + w, y, z + w, r, g, b, a, 0, 90, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderTextureSpriteOld(sprite, poseStack, mbs, x, y, z + w, r, g, b, a, 0, 0, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);
  */
    }

    /**
     * リンゴを描画するだけ
     * テスト用
     *
     * @param poseStack         PoseStack
     * @param mbs               MultiBufferSource
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     * @since 2.0
     */
    public static void renderApple(PoseStack poseStack, MultiBufferSource mbs, int combinedLightIn, int combinedOverlayIn) {
        mc.getItemRenderer().renderStatic(new ItemStack(Items.APPLE), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, poseStack, mbs, 0);
    }

    /**
     * GUI上に液体を描画する
     *
     * @param fluid     液体
     * @param getter    BlockAndTintGetter
     * @param pos       BlockPos
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @param startX    開始位置X
     * @param startY    開始位置Y
     * @param endX      終了位置X
     * @param endY      終了位置Y
     * @since 2.0
     */
    public static void drawFluid(Fluid fluid, BlockAndTintGetter getter, BlockPos pos, PoseStack poseStack, float x, float y, float size, float startX, float startY, float endX, float endY) {
      /*  if (fluid instanceof IIkisugibleFluid) {
            if (getter != null && pos != null)
                drawFluid(fluid, ((IIkisugibleFluid) fluid).getProperties().getWorldColor(getter, pos), poseStack, x, y, size, startX, startY, endX, endY);
            else
                drawFluid(fluid, poseStack, x, y, size, startX, startY, endX, endY);
        }*/
    }

    /**
     * @param fluid     液体
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @param startX    開始位置X
     * @param startY    開始位置Y
     * @param endX      終了位置X
     * @param endY      終了位置Y
     * @since 2.0
     */
    public static void drawFluid(Fluid fluid, PoseStack poseStack, float x, float y, float size, float startX, float startY, float endX, float endY) {
       /*if (fluid instanceof IIkisugibleFluid) {
            drawFluid(fluid, ((IIkisugibleFluid) fluid).getProperties().getColor(), poseStack, x, y, size, startX, startY, endX, endY);
        }*/
    }

    /**
     * @param fluid     液体
     * @param color     色
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @param startX    開始位置X
     * @param startY    開始位置Y
     * @param endX      終了位置X
     * @param endY      終了位置Y
     * @since 2.0
     */
    public static void drawFluid(Fluid fluid, int color, PoseStack poseStack, float x, float y, float size, float startX, float startY, float endX, float endY) {
     //   ResourceLocation location = ((IIkisugibleFluid) fluid).getProperties().getStillTexture();
    //    drawBlockAtlasColorTexture(location, poseStack, x, y, size, startX, startY, endX, endY, color);
    }

    /**
     * GUI上にブロックアトラスを描画する
     *
     * @param location  テクスチャID
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @param startX    開始位置X
     * @param startY    開始位置Y
     * @param endX      終了位置X
     * @param endY      終了位置Y
     * @since 2.0
     */
    public static void drawBlockAtlasTexture(ResourceLocation location, PoseStack poseStack, float x, float y, float size, float startX, float startY, float endX, float endY) {
        poseStack.pushPose();
        TextureAtlasSprite sprite = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);
        float brit = size / 16f;
        float bariW = 16384 / (float) sprite.getWidth() * brit;
        float bariH = 16384 / (float) sprite.getHeight() * brit;
        drawTexture(InventoryMenu.BLOCK_ATLAS, poseStack, x, y, sprite.getU(16 * startY) * bariW, sprite.getV(16 * startX) * bariH, sprite.getU(16d * endX) * bariW - sprite.getU(16 * startY) * bariW, sprite.getV(16d * endY) * bariH - sprite.getV(16 * startX) * bariH, bariW, bariH);
        poseStack.popPose();
    }

    /**
     * GUI上に着色したブロックアトラスを描画する
     *
     * @param location  テクスチャID
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @param startX    開始位置X
     * @param startY    開始位置Y
     * @param endX      終了位置X
     * @param endY      終了位置Y
     * @param color     色
     * @since 2.0
     */
    public static void drawBlockAtlasColorTexture(ResourceLocation location, PoseStack poseStack, float x, float y, float size, float startX, float startY, float endX, float endY, int color) {
        poseStack.pushPose();
        TextureAtlasSprite sprite = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);
        float brit = size / 16f;
        float bariW = 16384 / (float) sprite.getWidth() * brit;
        float bariH = 16384 / (float) sprite.getHeight() * brit;
     //   drawColorTexture(InventoryMenu.BLOCK_ATLAS, poseStack, x, y, sprite.getU(16 * startY) * bariW, sprite.getV(16 * startX) * bariH, sprite.getU(16d * endX) * bariW - sprite.getU(16 * startY) * bariW, sprite.getV(16d * endY) * bariH - sprite.getV(16 * startX) * bariH, bariW, bariH, color);
        poseStack.popPose();
    }
}