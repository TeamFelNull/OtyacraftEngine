package red.felnull.otyacraftengine.client.impl.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.level.block.Block;

public class OEClientExpectPlatformImpl {
    public static ModelBakery bakery;

    public static boolean isMiddlePressed(MouseHandler mouseHelper) {
        return mouseHelper.isMiddlePressed;
    }

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.key;
    }

    public static ModelBakery getModelBakery() {
        return bakery;
    }

    public static void setRenderLayer(Block block, RenderType type) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, type);
    }
}
