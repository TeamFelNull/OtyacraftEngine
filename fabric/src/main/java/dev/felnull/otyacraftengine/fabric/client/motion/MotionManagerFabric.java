package dev.felnull.otyacraftengine.fabric.client.motion;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.motion.MotionLoader;
import dev.felnull.otyacraftengine.client.motion.MotionManager;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MotionManagerFabric extends MotionManager implements SimpleResourceReloadListener<MotionLoader> {
    public static final ResourceLocation MOTION = new ResourceLocation(OtyacraftEngine.MODID, "motion");

    @Override
    public CompletableFuture<MotionLoader> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> prepare(manager, profiler), executor);
    }

    @Override
    public CompletableFuture<Void> apply(MotionLoader data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> apply(data, manager, profiler), executor);
    }

    @Override
    public ResourceLocation getFabricId() {
        return MOTION;
    }
}
