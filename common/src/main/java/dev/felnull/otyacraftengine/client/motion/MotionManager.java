package dev.felnull.otyacraftengine.client.motion;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MotionManager extends SimplePreparableReloadListener<MotionLoader> {
    private static MotionManager INSTANCE;
    private Map<ResourceLocation, Motion> motions;

    public MotionManager() {
        INSTANCE = this;
    }

    public static MotionManager getInstance() {
        return INSTANCE;
    }

    @Override
    protected MotionLoader prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        return new MotionLoader(resourceManager, profilerFiller);
    }

    @Override
    protected void apply(@NotNull MotionLoader loader, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        motions = loader.getMotions();
    }

    @Nullable
    public Map<ResourceLocation, Motion> getMotions() {
        return motions;
    }

    @Nullable
    public Motion getMotion(ResourceLocation location) {
        if (motions == null) return null;
        return motions.get(location);
    }
}
