package dev.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Invoker(value = "loadEffect")
    void loadEffectInvoker(ResourceLocation location);
}
