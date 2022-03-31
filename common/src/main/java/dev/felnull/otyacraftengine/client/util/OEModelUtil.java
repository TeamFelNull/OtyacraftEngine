package dev.felnull.otyacraftengine.client.util;

import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class OEModelUtil {
    private static final Minecraft mc = Minecraft.getInstance();

    public static BakedModel getModel(ResourceLocation resourceLocation) {
        return OEClientExpectPlatform.getModel(resourceLocation);
    }

    public static BakedModel getModel(ModelResourceLocation location) {
        return mc.getModelManager().getModel(location);
    }

    public static BakedModel getModel(BlockState state) {
        return mc.getModelManager().getBlockModelShaper().getBlockModel(state);
    }

    public static boolean isSlimPlayerModel(AbstractClientPlayer player) {
        var pl = player.getModelName();
        return "slim".equals(pl);
    }
}
