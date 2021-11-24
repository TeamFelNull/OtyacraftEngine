package dev.felnull.otyacraftengine.fabric.mixin.client;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.model.SpecialModelLoader;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
    @Shadow
    protected abstract void loadTopLevel(ModelResourceLocation modelResourceLocation);

    @Shadow
    protected abstract BlockModel loadBlockModel(ResourceLocation resourceLocation) throws IOException;

    @Shadow
    protected abstract void cacheAndQueueDependencies(ResourceLocation resourceLocation, UnbakedModel unbakedModel);

    @Shadow
    @Final
    private Map<ResourceLocation, UnbakedModel> unbakedCache;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", shift = At.Shift.AFTER, ordinal = 3))
    private void init(ResourceManager resourceManager, BlockColors blockColors, ProfilerFiller profilerFiller, int i, CallbackInfo ci) {
        SpecialModelLoader.getInstance().load((ModelBakery) (Object) this);
    }

    @Inject(method = "loadModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;Ljava/lang/String;)V", ordinal = 1), cancellable = true)
    private void loadModel(ResourceLocation resourceLocation, CallbackInfo ci) throws Exception {
        ModelResourceLocation modelResourceLocation = (ModelResourceLocation) resourceLocation;
        if (Objects.equals(modelResourceLocation.getVariant(), OtyacraftEngine.MODID + "_default")) {
            ResourceLocation resourceLocation3 = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath());
            BlockModel blockModel = this.loadBlockModel(resourceLocation3);
            this.cacheAndQueueDependencies(modelResourceLocation, blockModel);
            this.unbakedCache.put(resourceLocation3, blockModel);
            ci.cancel();
        }
    }
}
