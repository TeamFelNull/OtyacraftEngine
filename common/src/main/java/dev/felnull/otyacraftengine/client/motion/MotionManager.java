package dev.felnull.otyacraftengine.client.motion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

    public JsonObject toJson(Motion motion) {
        var jo = new JsonObject();
        jo.addProperty("version", 1);
        var ja = new JsonArray();
        for (MotionPoint point : motion.points()) {
            var jp = new JsonObject();
            var p = new JsonArray();
            p.add(point.position().x());
            p.add(point.position().y());
            p.add(point.position().z());
            jp.add("pos", p);
            var r = new JsonArray();
            r.add(point.rotation().x());
            r.add(point.rotation().y());
            r.add(point.rotation().z());
            jp.add("rot", r);
            ja.add(jp);
        }
        jo.add("points", ja);
        return jo;
    }


}
