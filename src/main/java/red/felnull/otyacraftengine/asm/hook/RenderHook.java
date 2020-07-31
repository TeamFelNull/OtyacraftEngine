package red.felnull.otyacraftengine.asm.hook;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;

public class RenderHook {
    public static void renderItemOverlayIntoGUIHook(FontRenderer fr, ItemStack stack, int x, int y, String text) {
        MatrixStack ms = new MatrixStack();
        IKSGRenderUtil.matrixPush(ms);
        float zlevel = Minecraft.getInstance().getItemRenderer().zLevel;
        IKSGRenderUtil.matrixTranslatef(ms, 0, 0, zlevel + 200.0F);
        IKSGRenderUtil.guiBindAndBlit(IKSGTextureUtil.getLoadingIconTextuer(), ms, x, y, 0, 0, 16, 16, 16, 16);
        IKSGRenderUtil.matrixPop(ms);
    }
}
