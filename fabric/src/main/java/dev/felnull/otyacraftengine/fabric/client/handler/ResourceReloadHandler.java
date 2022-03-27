package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.fabric.client.motion.MotionManagerFabric;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class ResourceReloadHandler {
    //    public static final ResourceLocation OE_3D_MODEL = new ResourceLocation(OtyacraftEngine.MODID, "3d_model");

    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new MotionManagerFabric());
       /* ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleResourceReloadListener<OEModelLoader>() {
            @Override
            public ResourceLocation getFabricId() {
                return OE_3D_MODEL;
            }

            @Override
            public CompletableFuture<OEModelLoader> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.supplyAsync(() -> OEModelLoader.load(manager), executor);
            }

            @Override
            public CompletableFuture<Void> apply(OEModelLoader data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.runAsync(data::apply, executor);
            }
        });*/
    }
}
