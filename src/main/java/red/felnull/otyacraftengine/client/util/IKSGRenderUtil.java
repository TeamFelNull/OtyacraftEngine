package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import red.felnull.otyacraftengine.util.IKSGStringUtil;
import red.felnull.otyacraftengine.util.IKSGStyles;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class IKSGRenderUtil {

    private static final Map<ResourceLocation, IBakedModel> BAKED_MODELS = new HashMap<ResourceLocation, IBakedModel>();
    private static final Map<String, Integer> MISALIGNEDS = new HashMap<>();
    private static final Map<String, Long> MISALIGNEDS_LASTTIMES = new HashMap<>();

    private static final Minecraft mc = Minecraft.getInstance();


    public static void drawPlayerFaseByUUID(MatrixStack matx, String uuid, int x, int y) {
        matrixPush(matx);
        ResourceLocation plskin = IKSGTextureUtil.getPlayerSkinTextureByUUID(uuid);
        guiBindAndBlit(plskin, matx, x, y, 8, 8, 8, 8, 64, 64);
        guiBindAndBlit(plskin, matx, x, y, 40, 8, 8, 8, 64, 64);
        matrixPop(matx);
    }

    public static void drawPlayerFase(MatrixStack matx, String name, int x, int y) {
        matrixPush(matx);
        ResourceLocation plskin = IKSGTextureUtil.getPlayerSkinTexture(name);
        guiBindAndBlit(plskin, matx, x, y, 8, 8, 8, 8, 64, 64);
        guiBindAndBlit(plskin, matx, x, y, 40, 8, 8, 8, 64, 64);
        matrixPop(matx);
    }

    public static void guiBindAndBlit(ResourceLocation location, MatrixStack matx, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight) {
        guiBindAndBlit(location, matx, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, 256);
    }

    public static void guiBindAndBlit(ResourceLocation location, MatrixStack matx, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSize) {
        guiBindAndBlit(location, matx, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSize, textureSize);
    }

    public static void guiBindAndBlit(ResourceLocation location, MatrixStack matx, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSizeX, int textureSizeY) {
        matrixPush(matx);
        mc.getTextureManager().bindTexture(location);
        guiBlit(matx, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
        matrixPop(matx);
    }

    public static void guiBlit(MatrixStack matx, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSizeX, int textureSizeY) {
        AbstractGui.blit(matx, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
    }

    public static void matrixTranslatef(MatrixStack ms, float x, float y, float z) {
        ms.translate(x, y, z);
    }

    public static void matrixTranslatef16Divisions(MatrixStack ms, float x, float y, float z) {
        float pix = 1f / 16f;
        matrixTranslatef(ms, pix * x, pix * y, pix * z);
    }

    public static void matrixScalf(MatrixStack ms, float all) {
        matrixScalf(ms, all, all, all);
    }

    public static void matrixScalf(MatrixStack ms, float x, float y, float z) {
        ms.scale(x, y, z);
    }

    public static void matrixPush(MatrixStack ms) {
        ms.push();
    }

    public static void matrixPop(MatrixStack ms) {
        ms.pop();
    }

    public static void matrixRotateDegreef(MatrixStack ms, float x, float y, float z) {
        matrixRotateDegreefX(ms, x);
        matrixRotateDegreefY(ms, y);
        matrixRotateDegreefZ(ms, z);
    }

    public static void matrixRotateDegreefX(MatrixStack ms, float x) {
        ms.rotate(Vector3f.XP.rotationDegrees(x));
    }

    public static void matrixRotateDegreefY(MatrixStack ms, float y) {
        ms.rotate(Vector3f.YP.rotationDegrees(y));
    }

    public static void matrixRotateDegreefZ(MatrixStack ms, float z) {
        ms.rotate(Vector3f.ZP.rotationDegrees(z));
    }

    public static void matrixRotateHorizontal(BlockState state, MatrixStack matrix) {
        Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
        matrixRotateDirection(direction, matrix);
    }

    public static void matrixRotateDirection(Direction direction, MatrixStack matrix) {
        if (direction == Direction.WEST) {
            matrixRotateDegreefY(matrix, 180);
            matrixTranslatef(matrix, -1f, 0f, -1f);
        } else if (direction == Direction.NORTH) {
            matrixRotateDegreefY(matrix, 90);
            matrixTranslatef(matrix, -1f, 0f, 0f);
        } else if (direction == Direction.SOUTH) {
            matrixRotateDegreefY(matrix, 270);
            matrixTranslatef(matrix, 0f, 0f, -1f);
        }
    }

    public static IBakedModel getBakedModel(ResourceLocation location) {

        if (BAKED_MODELS.containsKey(location))
            return BAKED_MODELS.get(location);

        IBakedModel model = ModelLoader.instance().bake(location, ModelRotation.X0_Y0);
        BAKED_MODELS.put(location, model);
        return model;
    }

    public static void renderBlockBakedModel(IBakedModel modelIn, MatrixStack matrixIn, IVertexBuilder buffer, int combinedOverlayIn, TileEntity tile) {
        renderBlockBakedModel(tile.getWorld(), modelIn, tile.getBlockState(), tile.getPos(), matrixIn, buffer, tile.getWorld().rand, combinedOverlayIn);
    }

    public static void renderBlockBakedModel(IBlockDisplayReader worldIn, IBakedModel modelIn, BlockState stateIn, BlockPos posIn, MatrixStack matrixIn, IVertexBuilder buffer, Random randomIn, int combinedOverlayIn) {
        renderBlockBakedModel(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, false, randomIn, 0, combinedOverlayIn, EmptyModelData.INSTANCE);
    }

    public static void renderBlockBakedModel(IBlockDisplayReader worldIn, IBakedModel modelIn, BlockState stateIn, BlockPos posIn, MatrixStack matrixIn, IVertexBuilder buffer, boolean checkSides, Random randomIn, long rand, int combinedOverlayIn, IModelData modelData) {
        BlockRendererDispatcher brd = mc.getBlockRendererDispatcher();
        BlockModelRenderer bmr = brd.getBlockModelRenderer();
        bmr.renderModel(worldIn, modelIn, stateIn, posIn, matrixIn, buffer, checkSides, randomIn, rand, combinedOverlayIn, modelData);
    }

    public static void drawHorizontalMovementString(MatrixStack matrix, FontRenderer fontRenderer, String text, String id, int blank, int x, int y, int width, int speed, Style... style) {
        IFormattableTextComponent textc = IKSGStyles.withStyle(new StringTextComponent(text), style);
        int textSize = fontRenderer.getStringPropertyWidth(textc);
        if (width >= textSize) {
            drawString(fontRenderer, matrix, textc, x, y, 0);
            return;
        }
        int allsize = textSize + blank;
        if (!MISALIGNEDS.containsKey(id)) {
            MISALIGNEDS.put(id, 0);
            MISALIGNEDS_LASTTIMES.put(id, System.currentTimeMillis());
        } else {
            long conttime = System.currentTimeMillis() - MISALIGNEDS_LASTTIMES.get(id);
            if (conttime >= 1000 / speed) {
                int zures = MISALIGNEDS.get(id);
                if (zures >= allsize)
                    MISALIGNEDS.put(id, 1);
                else
                    MISALIGNEDS.put(id, zures + 1);
                MISALIGNEDS_LASTTIMES.put(id, System.currentTimeMillis());
            }
        }
        int zure = MISALIGNEDS.get(id);

        if (zure < textSize) {
            String intext = text;
            for (int i = 0; i < text.length(); i++) {
                String cutble = IKSGStringUtil.cutForFront(text, i);
                int cuttoblesize = fontRenderer.getStringPropertyWidth(IKSGStyles.withStyle(new StringTextComponent(cutble), style));
                if (textSize - zure > cuttoblesize)
                    break;
                intext = cutble;
            }
            int backCont = 0;
            if (textSize - width > zure) {
                int tobidasizure = textSize - width > zure ? textSize - width - zure : 0;
                String aintext = text;
                for (int i = 0; i < text.length(); i++) {
                    String cutble = IKSGStringUtil.cutForFront(text, text.length() - i);
                    int cuttoblesize = fontRenderer.getStringPropertyWidth(IKSGStyles.withStyle(new StringTextComponent(cutble), style));
                    if (tobidasizure <= cuttoblesize)
                        break;
                    aintext = cutble;
                }
                backCont = aintext.length();
            }

            int backContZure = fontRenderer.getStringPropertyWidth(IKSGStyles.withStyle(new StringTextComponent(IKSGStringUtil.cutForFront(intext, intext.length() - backCont)), style));
            intext = IKSGStringUtil.cutForBack(intext, backCont);
            IFormattableTextComponent inextc = IKSGStyles.withStyle(new StringTextComponent(intext), style);
            drawBackString(fontRenderer, matrix, inextc, x + textSize - zure - backContZure, y, 0);
        }
        if (allsize - zure <= width) {
            String intext = text;
            for (int i = 0; i < text.length(); i++) {
                String cutble = IKSGStringUtil.cutForBack(text, i);
                int cuttoblesize = fontRenderer.getStringPropertyWidth(IKSGStyles.withStyle(new StringTextComponent(cutble), style));
                if (-(allsize - zure - width) > cuttoblesize)
                    break;
                intext = cutble;
            }
            IFormattableTextComponent inextc = IKSGStyles.withStyle(new StringTextComponent(intext), style);
            drawString(fontRenderer, matrix, inextc, x + allsize - zure, y, 0);
        }
    }

    public static void drawStringShadowble(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        fr.func_238407_a_(matrix, text.func_241878_f(), x, y, color);
    }

    public static void drawCenterStringShadowble(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        int size = fr.getStringPropertyWidth(text);
        drawStringShadowble(fr, matrix, text, x - size / 2, y, color);
    }

    public static void drawString(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        fr.func_243248_b(matrix, text, x, y, color);
    }

    public static void drawCenterString(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        int size = fr.getStringPropertyWidth(text);
        drawString(fr, matrix, text, x - size / 2, y, color);
    }

    public static void drawString(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color, boolean shadow) {
        if (shadow)
            drawStringShadowble(fr, matrix, text, x, y, color);
        else
            drawString(fr, matrix, text, x, y, color);
    }

    public static void drawCenterString(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color, boolean shadow) {
        if (shadow)
            drawCenterStringShadowble(fr, matrix, text, x, y, color);
        else
            drawCenterString(fr, matrix, text, x, y, color);
    }

    public static void drawBackString(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        int size = fr.getStringPropertyWidth(text);
        drawString(fr, matrix, text, x - size, y, color);
    }

    public static void drawBackStringShadowble(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        int size = fr.getStringPropertyWidth(text);
        drawCenterStringShadowble(fr, matrix, text, x - size, y, color);
    }

    public static void drawBackString(FontRenderer fr, MatrixStack matrix, ITextComponent text, int x, int y, int color, boolean shadow) {
        if (shadow)
            drawBackStringShadowble(fr, matrix, text, x, y, color);
        else
            drawBackString(fr, matrix, text, x, y, color);
    }

    public static float partialTicksMisalignment(float val, float prevVal, float partialTicks) {
        return val + (prevVal - val) * partialTicks;
    }

    public static void renderSpritePanel(ResourceLocation texlocation, MatrixStack matrix, IRenderTypeBuffer bufferIn, float x, float y, float z, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, float texSizeW, float texSizeH, int combinedOverlayIn, int combinedLightIn) {
        matrixPush(matrix);
        matrixRotateDegreefY(matrix, yaw);
        matrixRotateDegreefX(matrix, pitch);
        matrixRotateDegreefZ(matrix, roll);
        IVertexBuilder ivb = bufferIn.getBuffer(getTextuerRenderType(texlocation));

        float wst = texStartX / texSizeW;
        float wft = texFinishX / texSizeW + wst;
        float hst = texStartY / texSizeH;
        float hft = texFinishY / texSizeH + hst;

        addVertex(ivb, matrix, x, y, z, wst, hft, combinedOverlayIn, combinedLightIn);
        addVertex(ivb, matrix, w + x, y, z, wft, hft, combinedOverlayIn, combinedLightIn);
        addVertex(ivb, matrix, w + x, h + y, z, wft, hst, combinedOverlayIn, combinedLightIn);
        addVertex(ivb, matrix, x, h + y, z, wst, hst, combinedOverlayIn, combinedLightIn);
        matrixPop(matrix);
    }

    public static void addVertex(IVertexBuilder builder, MatrixStack matrix, float x, float y, float z, float u, float v, int combinedOverlayIn, int combinedLightIn) {
        MatrixStack.Entry entry = matrix.getLast();
        builder.pos(entry.getMatrix(), x, y, z).color(255, 255, 255, 255).tex(u, v).overlay(combinedOverlayIn).lightmap(combinedLightIn).normal(entry.getNormal(), 0f, 0f, 0f).endVertex();
    }

    public static RenderType getTextuerRenderType(ResourceLocation locationIn) {
        RenderType.State state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(new RenderState.TransparencyState("no_transparency", RenderSystem::disableBlend, () -> {
        })).diffuseLighting(new RenderState.DiffuseLightingState(false)).alpha(new RenderState.AlphaState(0.003921569F)).lightmap(new RenderState.LightmapState(true)).overlay(new RenderState.OverlayState(true)).build(true);
        return RenderType.makeType("entity_cutout", DefaultVertexFormats.ENTITY, 7, 256, true, false, state);
    }

    public static void renderRightHand(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn) {
        matrixPush(matrixStack);
        mc.getTextureManager().bindTexture(playerIn.getLocationSkin());
        PlayerRenderer plr = (PlayerRenderer) mc.getRenderManager().getRenderer(playerIn);
        plr.renderRightArm(matrixStack, bufferIn, combinedLightIn, playerIn);
        matrixPop(matrixStack);
    }

    public static void renderLeftHand(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn) {
        matrixPush(matrixStack);
        mc.getTextureManager().bindTexture(playerIn.getLocationSkin());
        PlayerRenderer plr = (PlayerRenderer) mc.getRenderManager().getRenderer(playerIn);
        plr.renderLeftArm(matrixStack, bufferIn, combinedLightIn, playerIn);
        matrixPop(matrixStack);
    }

    public static void matrixArmFirstPerson(MatrixStack matrixStackIn, float equippedProgress, float swingProgress, HandSide side) {
        boolean flag = side != HandSide.LEFT;
        float f = flag ? 1.0f : -1.0f;
        float f1 = MathHelper.sqrt(swingProgress);
        float f2 = -0.3f * MathHelper.sin(f1 * (float) Math.PI);
        float f3 = 0.4f * MathHelper.sin(f1 * ((float) Math.PI * 2f));
        float f4 = -0.4f * MathHelper.sin(swingProgress * (float) Math.PI);
        matrixTranslatef(matrixStackIn, f * (f2 + 0.64000005f), f3 + -0.6f + equippedProgress * -0.6f, f4 + -0.71999997f);
        matrixRotateDegreefY(matrixStackIn, f * 45.0f);
        float f5 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f6 = MathHelper.sin(f1 * (float) Math.PI);
        matrixRotateDegreefY(matrixStackIn, f * f6 * 70.0f);
        matrixRotateDegreefZ(matrixStackIn, f * f5 * -20.0f);
        AbstractClientPlayerEntity abstractclientplayerentity = mc.player;
        mc.getTextureManager().bindTexture(abstractclientplayerentity.getLocationSkin());
        matrixTranslatef(matrixStackIn, f * -1.0f, 3.6f, 3.5f);
        matrixRotateDegreefZ(matrixStackIn, f * 120.0f);
        matrixRotateDegreefX(matrixStackIn, 200.0f);
        matrixRotateDegreefY(matrixStackIn, f * -135.0f);
        matrixTranslatef(matrixStackIn, f * 5.6f, 0.0f, 0.0f);
    }
}
