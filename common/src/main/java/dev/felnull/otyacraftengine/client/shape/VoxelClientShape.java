package dev.felnull.otyacraftengine.client.shape;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.shape.VoxelEdge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class VoxelClientShape {
    private final String meta;
    private final String version;
    private final List<VoxelEdge> renderEdges;

    public VoxelClientShape(@Nullable String meta, @Nullable String version, @NotNull List<VoxelEdge> edges) {
        this.meta = meta;
        this.version = version;
        this.renderEdges = ImmutableList.copyOf(edges);
    }

    @NotNull
    public List<VoxelEdge> getRenderEdges() {
        return renderEdges;
    }

    @Nullable
    public String getVersion() {
        return version;
    }

    @Nullable
    public String getMeta() {
        return meta;
    }

    @Nullable
    public static VoxelClientShape parse(JsonObject jo) {
        if (jo == null || !(jo.has("type") && jo.get("type").isJsonPrimitive() && "ikisugi_voxel_shape".equals(jo.get("type").getAsString())))
            return null;
        String meta = null;
        if (jo.has("meta") && jo.get("meta").isJsonPrimitive())
            meta = jo.get("meta").getAsString();

        String version = null;
        if (jo.has("version") && jo.get("version").isJsonPrimitive())
            version = jo.get("version").getAsString();

        ImmutableList.Builder<VoxelEdge> edgeBuilder = new ImmutableList.Builder<>();

        if (jo.has("edges") && jo.get("edges").isJsonArray()) {
            var ja = jo.getAsJsonArray("edges");
            for (JsonElement element : ja) {
                if (element.isJsonArray()) {
                    var ed = VoxelEdge.parse(element.getAsJsonArray());
                    if (ed != null)
                        edgeBuilder.add(ed);
                }
            }
        }

        return new VoxelClientShape(meta, version, edgeBuilder.build());
    }

    @Override
    public String toString() {
        return "VoxelClientShape{" +
                "meta='" + meta + '\'' +
                ", version='" + version + '\'' +
                ", renderEdges count=" + renderEdges.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelClientShape that = (VoxelClientShape) o;
        return Objects.equals(meta, that.meta) && Objects.equals(version, that.version) && Objects.equals(renderEdges, that.renderEdges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta, version, renderEdges);
    }
}
