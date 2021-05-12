package red.felnull.otyacraftengine.client.impl.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();
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

    public static void addSubtitle(Component text, Vec3 location) {
        for (SubtitleOverlay.Subtitle subtitle : mc.gui.subtitleOverlay.subtitles) {
            if (subtitle.getText().equals(text)) {
                subtitle.refresh(location);
                return;
            }
        }
        SubtitleOverlay.Subtitle sb = (mc.gui.subtitleOverlay).new Subtitle(text, location);
        mc.gui.subtitleOverlay.subtitles.add(sb);
    }

}
