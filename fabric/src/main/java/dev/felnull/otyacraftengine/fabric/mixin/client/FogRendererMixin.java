package dev.felnull.otyacraftengine.fabric.mixin.client;

import com.mojang.blaze3d.shaders.FogShape;
import dev.felnull.otyacraftengine.client.event.ClientCameraEvent;
import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Shadow
    private static float fogRed;

    @Shadow
    private static float fogGreen;

    @Shadow
    private static float fogBlue;

    @Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", remap = false, ordinal = 1))
    private static void setupColor(Camera camera, float f, ClientLevel clientLevel, int i, float g, CallbackInfo ci) {
        OEClientEventHooks.onComputeFogColor(camera, fogRed, fogGreen, fogBlue, f, new ClientCameraEvent.FogColorSetter() {
            @Override
            public void setRed(float red) {
                fogRed = red;
            }

            @Override
            public void setGreen(float green) {
                fogGreen = green;
            }

            @Override
            public void setBlue(float blue) {
                fogBlue = blue;
            }
        });
    }

    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", remap = false, ordinal = 0, shift = At.Shift.BY, by = -1), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void setupFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci, FogType fogType, Entity entity, FogRenderer.FogData fogData) {
        float[] distance = {fogData.start, fogData.end};
        FogShape[] shapes = {fogData.shape};

        if (!OEClientEventHooks.onRenderFog(fogMode, fogType, fogData.start, fogData.end, fogData.shape, g, new ClientCameraEvent.RenderFogSetter() {
            @Override
            public void setStartDistance(float startDistance) {
                distance[0] = startDistance;
            }

            @Override
            public void setEndDistance(float endDistance) {
                distance[1] = endDistance;
            }

            @Override
            public void setFogShape(FogShape fogShape) {
                shapes[0] = fogShape;
            }
        })) {
            fogData.start = distance[0];
            fogData.end = distance[1];
            fogData.shape = shapes[0];
        }
    }

}
