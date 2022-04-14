package dev.felnull.otyacraftengine.fabric.mixin.client;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import dev.monarkhes.myron.impl.client.obj.AbstractObjLoader;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractObjLoader.class)
public class MyronAbstractObjLoaderMixin {
    @Inject(method = "loadModel", at = @At("RETURN"), cancellable = true, remap = false)
    private void loadModel(ResourceManager resourceManager, ResourceLocation identifier, ItemTransforms transformation, boolean isSideLit, CallbackInfoReturnable<UnbakedModel> cir) {
        if (OEClientEventHooks.onFabricOBJLoader(identifier))
            cir.setReturnValue(null);
    }
}
