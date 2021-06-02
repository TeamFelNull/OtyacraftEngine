package red.felnull.otyacraftengine.client.impl;

import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.NativeImage;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;
import java.util.function.Supplier;

public class OEClientExpectPlatform {
    @ExpectPlatform
    public static boolean isMiddlePressed(MouseHandler mouseHelper) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Key getKey(KeyMapping key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ModelBakery getModelBakery() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setRenderLayer(Block block, RenderType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addSubtitle(UUID id, Component text, long time, Supplier<Vec3> location) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setNonClosePixels(DynamicTexture texture, NativeImage image) {
        throw new AssertionError();
    }
}
