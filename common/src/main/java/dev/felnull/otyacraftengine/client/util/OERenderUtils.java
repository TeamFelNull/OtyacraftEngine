package dev.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 描画関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OERenderUtils {
    private static final Minecraft mc = Minecraft.getInstance();

    /**
     * PoseStackを16分の１単位で移動する
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param z         Z
     */
    public static void poseTrans16(@NotNull PoseStack poseStack, double x, double y, double z) {
        float pix = 1f / 16f;
        poseStack.translate(pix * x, pix * y, pix * z);
    }

    /**
     * PoseStackをすべてのスケールを設定する
     *
     * @param poseStack PoseStack
     * @param scale     すべてのスケール
     */
    public static void poseScaleAll(@NotNull PoseStack poseStack, float scale) {
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
    public static void poseRotateAll(@NotNull PoseStack poseStack, float x, float y, float z) {
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
    public static void poseRotateX(@NotNull PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.XP.rotationDegrees(angle));
    }

    /**
     * PoseStackのY角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateY(@NotNull PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));
    }

    /**
     * PoseStackのZ角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateZ(@NotNull PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(angle));
    }

    /**
     * PoseStackの方向のブロックステートに設定する
     *
     * @param poseStack PoseStack
     * @param state     角度
     * @param roted     回転ずれ
     */
    public static void poseRotateHorizontalState(@NotNull PoseStack poseStack, @NotNull BlockState state, int roted) {
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
    public static void poseRotateDirection(@NotNull PoseStack poseStack, @NotNull Direction direction, int roted) {
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
     * 中心でposeを変更する
     *
     * @param poseStack         PoseStack
     * @param centerX           中心X
     * @param centerY           中心Y
     * @param centerZ           中心Z
     * @param poseStackConsumer 中心での処理
     */
    public static void poseCenterConsumer(@NotNull PoseStack poseStack, float centerX, float centerY, float centerZ, @NotNull Consumer<PoseStack> poseStackConsumer) {
        poseStack.translate(centerX, centerY, centerZ);
        poseStackConsumer.accept(poseStack);
        poseStack.translate(-centerX, -centerY, -centerZ);
    }

    /**
     * GUI上でテクスチャを描画する
     *
     * @param location      テクスチャ
     * @param poseStack     PoseStack
     * @param x             X
     * @param y             Y
     * @param u0            テクスチャの開始地点X
     * @param v0            テクスチャの開始地点Y
     * @param u1            テクスチャの終了地点X
     * @param v1            テクスチャの終了地点Y
     * @param textureWidth  テクスチャの横サイズ
     * @param textureHeight テクスチャの縦サイズ
     */
    public static void drawTexture(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight) {
        setPreDraw(location);
        blitFloat(poseStack, x, y, u0, v0, u1, v1, textureWidth, textureHeight);
    }

    /**
     * GUI上でテクスチャを描画する
     * テクスチャサイズは256x256
     *
     * @param location  テクスチャ
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param u0        テクスチャの開始地点X
     * @param v0        テクスチャの開始地点Y
     * @param u1        テクスチャの終了地点X
     * @param v1        テクスチャの終了地点Y
     */
    public static void drawTexture(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float u1, float v1) {
        drawTexture(location, poseStack, x, y, u0, v0, u1, v1, 256, 256);
    }

    /**
     * テクスチャ描画前に呼び出し
     *
     * @param location テクスチャ
     */
    public static void setPreDraw(@NotNull ResourceLocation location) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    /**
     * 半透明で描画のための呼び出し
     *
     * @param location テクスチャ
     * @param draw     描画処理
     */
    public static void setAndDrawAlpha(@NotNull ResourceLocation location, Runnable draw) {
        setPreDraw(location);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        draw.run();
        RenderSystem.disableBlend();
    }

    /**
     * GUI上で半透明テクスチャを描画する
     *
     * @param location      テクスチャ
     * @param poseStack     PoseStack
     * @param x             X
     * @param y             Y
     * @param u0            テクスチャの開始地点X
     * @param v0            テクスチャの開始地点Y
     * @param u1            テクスチャの終了地点X
     * @param v1            テクスチャの終了地点Y
     * @param textureWidth  テクスチャの横サイズ
     * @param textureHeight テクスチャの縦サイズ
     */
    public static void drawTextureAlpha(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight) {
        setAndDrawAlpha(location, () -> blitFloat(poseStack, x, y, u0, v0, u1, v1, textureWidth, textureHeight));
    }

    /**
     * GUI上で半透明テクスチャを描画する
     * テクスチャサイズは256x256
     *
     * @param location  テクスチャ
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param u0        テクスチャの開始地点X
     * @param v0        テクスチャの開始地点Y
     * @param u1        テクスチャの終了地点X
     * @param v1        テクスチャの終了地点Y
     */
    public static void drawTextureAlpha(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float u1, float v1) {
        drawTextureAlpha(location, poseStack, x, y, u0, v0, u1, v1, 256, 256);
    }

    /**
     * 指定済みテクスチャとシェーダーで描画
     *
     * @param poseStack     PoseStack
     * @param x             X
     * @param y             Y
     * @param u0            テクスチャの開始地点X
     * @param v0            テクスチャの開始地点Y
     * @param u1            テクスチャの終了地点X
     * @param v1            テクスチャの終了地点Y
     * @param textureWidth  テクスチャの横サイズ
     * @param textureHeight テクスチャの縦サイズ
     */
    public static void blitFloat(@NotNull PoseStack poseStack, float x, float y, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight) {
        Matrix4f matrix4f = poseStack.last().pose();
        float ry = x + u1;
        float rh = y + v1;
        float ru0 = u0 / textureWidth;
        float ru1 = (u0 + u1) / textureWidth;
        float rv0 = v0 / textureHeight;
        float rv1 = (v0 + v1) / textureHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, rh, 0).uv(ru0, rv1).endVertex();
        bufferBuilder.vertex(matrix4f, ry, rh, 0).uv(ru1, rv1).endVertex();
        bufferBuilder.vertex(matrix4f, ry, y, 0).uv(ru1, rv0).endVertex();
        bufferBuilder.vertex(matrix4f, x, y, 0).uv(ru0, rv0).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    /**
     * 指定済みテクスチャとシェーダーで描画
     * テクスチャサイズは256x256
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param u0        テクスチャの開始地点X
     * @param v0        テクスチャの開始地点Y
     * @param u1        テクスチャの終了地点X
     * @param v1        テクスチャの終了地点Y
     */
    public static void blitFloat(@NotNull PoseStack poseStack, float x, float y, float u0, float v0, float u1, float v1) {
        blitFloat(poseStack, x, y, u0, v0, u1, v1, 256, 256);
    }

    /**
     * GUI上を塗りつぶす
     * intの場合はGuiComponent.fillを推奨
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param width     幅
     * @param height    高さ
     * @param color     塗りつぶし色
     */
    public static void drawFill(@NotNull PoseStack poseStack, float x, float y, float width, float height, int color) {
        innerFill(poseStack.last().pose(), x, y, width, height, color);
    }

    private static void innerFill(Matrix4f matrix4f, float x, float y, float w, float h, int coler) {
        float n;
        if (x < w) {
            n = x;
            x = w;
            w = n;
        }

        if (y < h) {
            n = y;
            y = h;
            h = n;
        }

        float a = (float) (coler >> 24 & 255) / 255.0F;
        float r = (float) (coler >> 16 & 255) / 255.0F;
        float g = (float) (coler >> 8 & 255) / 255.0F;
        float b = (float) (coler & 255) / 255.0F;

        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, x, h, 0.0F).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix4f, w, h, 0.0F).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix4f, w, y, 0.0F).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix4f, x, y, 0.0F).color(r, g, b, a).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /**
     * モデルを描画する
     *
     * @param poseStack       PoseStack
     * @param vertexConsumer  VertexConsumer
     * @param bakedModel      BakedModel
     * @param combinedLight   CombinedLight
     * @param combinedOverlay CombinedOverlay
     */
    public static void renderModel(PoseStack poseStack, VertexConsumer vertexConsumer, @NotNull BakedModel bakedModel, int combinedLight, int combinedOverlay) {
        Objects.requireNonNull(bakedModel);
        var bmr = mc.getBlockRenderer().getModelRenderer();
        bmr.renderModel(poseStack.last(), vertexConsumer, null, bakedModel, 1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay);
    }

    /**
     * モデルを描画する
     *
     * @param poseStack       PoseStack
     * @param vertexConsumer  VertexConsumer
     * @param bakedModel      BakedModel
     * @param combinedLight   CombinedLight
     * @param combinedOverlay CombinedOverlay
     * @param color           色
     */
    public static void renderModel(PoseStack poseStack, VertexConsumer vertexConsumer, @NotNull BakedModel bakedModel, int combinedLight, int combinedOverlay, int color) {
        Objects.requireNonNull(bakedModel);
        var bmr = mc.getBlockRenderer().getModelRenderer();
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        bmr.renderModel(poseStack.last(), vertexConsumer, null, bakedModel, r, g, b, combinedLight, combinedOverlay);
    }

    public static void renderTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int combinedLightIn, int combinedOverlayIn) {
        renderSprite(poseStack, multiBufferSource.getBuffer(RenderType.entityCutout(location)), width, height, u0, v0, u1, v1, textureWidth, textureHeight, combinedLightIn, combinedOverlayIn);
    }

    public static void renderColorfulTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int color, int combinedLightIn, int combinedOverlayIn) {
        renderColorfulSprite(poseStack, multiBufferSource.getBuffer(RenderType.entityCutout(location)), width, height, u0, v0, u1, v1, textureWidth, textureHeight, color, combinedLightIn, combinedOverlayIn);
    }

    public static void renderSprite(PoseStack poseStack, VertexConsumer vertexConsumer, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int combinedLightIn, int combinedOverlayIn) {
        renderColorfulSprite(poseStack, vertexConsumer, width, height, u0, v0, u1, v1, textureWidth, textureHeight, 0xFFFFFFFF, combinedLightIn, combinedOverlayIn);
    }

    public static void renderColorfulSprite(PoseStack poseStack, VertexConsumer vertexConsumer, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int color, int combinedLightIn, int combinedOverlayIn) {
        float wst = u0 / textureWidth;
        float wft = u1 / textureWidth + wst;
        float hst = v0 / textureHeight;
        float hft = v1 / textureHeight + hst;

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        PoseStack.Pose pose = poseStack.last();
        vertex(vertexConsumer, pose, 0, 0, 0, wst, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vertexConsumer, pose, width, 0, 0, wft, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vertexConsumer, pose, width, height, 0, wft, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vertexConsumer, pose, 0, height, 0, wst, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
    }

    private static void vertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float r, float g, float b, float a, int combinedOverlayIn, int combinedLightIn) {
        builder.vertex(pose.pose(), x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(combinedOverlayIn).uv2(combinedLightIn).normal(pose.normal(), 0f, 0f, 0f).endVertex();
    }
}
