package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderHelper {
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
}
