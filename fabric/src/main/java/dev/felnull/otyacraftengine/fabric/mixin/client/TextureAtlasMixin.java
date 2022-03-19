package dev.felnull.otyacraftengine.fabric.mixin.client;

import dev.felnull.otyacraftengine.client.model.obj.OEOBJLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Set;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {
    @ModifyVariable(method = "prepareToStitch", at = @At("STORE"), ordinal = 0)
    private Set<ResourceLocation> modifySet(Set<ResourceLocation> val) {
        val.addAll(OEOBJLoader.getInstance().getLoadTextures((TextureAtlas) (Object) this));
        return val;
    }
}
