package red.felnull.otyacraftengine.client.impl.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import red.felnull.otyacraftengine.client.gui.components.IIkisugiSubtitle;

import java.util.UUID;
import java.util.function.Supplier;

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

    public static void addSubtitle(UUID id, Component text, long time, Supplier<Vec3> location) {
        for (SubtitleOverlay.Subtitle subtitle : mc.gui.subtitleOverlay.subtitles) {
            if (subtitle instanceof IIkisugiSubtitle) {
                IIkisugiSubtitle is = (IIkisugiSubtitle) subtitle;
                if ((is.getID() != null && is.getID().equals(id)) || (id == null && subtitle.getText().equals(text))) {
                    is.overrideRefresh(text, location, time);
                    return;
                }
            }
        }
        SubtitleOverlay.Subtitle sb = new SubtitleOverlay.Subtitle(text, location.get());
        IIkisugiSubtitle is = (IIkisugiSubtitle) sb;
        is.setID(id);
        is.setTime(time);
        is.setDynamicLocation(location);
        mc.gui.subtitleOverlay.subtitles.add(sb);
    }

    public static void setNonClosePixels(DynamicTexture texture, NativeImage image) {
     //   texture.getPixels()
    }
}
