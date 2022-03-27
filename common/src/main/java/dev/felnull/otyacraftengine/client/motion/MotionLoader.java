package dev.felnull.otyacraftengine.client.motion;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MotionLoader {
    public static final Logger LOGGER = LogManager.getLogger(MotionLoader.class);
    private static final Gson GSON = new Gson();
    private final Map<ResourceLocation, Motion> motions = new HashMap<>();

    protected MotionLoader(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        profilerFiller.startTick();
        for (ResourceLocation id : resourceManager.listResources("oe_motion", path -> path.endsWith(".json"))) {
            profilerFiller.push(id.toString());
            try (InputStream stream = resourceManager.getResource(id).getInputStream(); Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                JsonObject jo = GSON.fromJson(reader, JsonObject.class);
                var p = id.getPath();
                motions.put(new ResourceLocation(id.getNamespace(), p.substring("oe_motion/".length(), p.length() - ".json".length())), Motion.of(jo));
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading motion resource json " + id, e);
            }
            profilerFiller.pop();
        }
        profilerFiller.endTick();
    }

    public Map<ResourceLocation, Motion> getMotions() {
        return ImmutableMap.copyOf(motions);
    }
}
