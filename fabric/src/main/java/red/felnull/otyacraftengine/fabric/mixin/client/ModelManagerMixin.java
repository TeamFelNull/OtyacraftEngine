package red.felnull.otyacraftengine.mixin.fabric.client;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import red.felnull.otyacraftengine.client.impl.fabric.OEClientExpectPlatformImpl;

@Mixin(ModelManager.class)
public class ModelManagerMixin {
    @Inject(method = "prepare", at = @At("RETURN"))
    private void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<ModelBakery> cir) {
        OEClientExpectPlatformImpl.bakery = cir.getReturnValue();
    }
}
