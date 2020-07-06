package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {

    private static Map<ResourceLocation, IBakedModel> BAKED_MODELS = new HashMap<ResourceLocation, IBakedModel>();

    private static Minecraft mc = Minecraft.getInstance();

    public static void drawPlayerFase(MatrixStack matx, String name, int x, int y) {
        matrixPush(matx);
        ResourceLocation plskin = TextureHelper.getPlayerSkinTexture(name);
        guiBindAndBlit(plskin, matx, x, y, 8, 8, 8, 8, 64, 64);
        guiBindAndBlit(plskin, matx, x, y, 40, 8, 8, 8, 64, 64);
        matrixPop(matx);
    }

    public static void guiBindAndBlit(ResourceLocation location, MatrixStack matx, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSizeX, int textureSizeY) {
        mc.getTextureManager().bindTexture(location);
        guiBlit(matx, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
    }

    public static void guiBlit(MatrixStack matx, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSizeX, int textureSizeY) {
        AbstractGui.func_238463_a_(matx, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
    }

    public static void matrixTranslatef(MatrixStack ms, float x, float y, float z) {
        ms.translate(x, y, z);
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

    public static void matrixRotateDegreefX(MatrixStack ms, float x) {
        ms.rotate(new Vector3f(1, 0, 0).rotationDegrees(x));
    }

    public static void matrixRotateDegreefY(MatrixStack ms, float y) {
        ms.rotate(new Vector3f(0, 1, 0).rotationDegrees(y));
    }

    public static void matrixRotateDegreefZ(MatrixStack ms, float z) {
        ms.rotate(new Vector3f(0, 0, 1).rotationDegrees(z));
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
}
