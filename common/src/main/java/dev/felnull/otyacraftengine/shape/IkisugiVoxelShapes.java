package dev.felnull.otyacraftengine.shape;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.util.OEVoxelShapeUtil;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

public class IkisugiVoxelShapes {
    private static final IkisugiVoxelShapes INSTANCE = new IkisugiVoxelShapes();

    public static IkisugiVoxelShapes getInstance() {
        return INSTANCE;
    }

    public VoxelShape getShapeFromJson(JsonObject shapeJson) {
        var version = shapeJson.get("version");
        if (version != null && version.getAsInt() == 2)
            return getShapeFromJsonV2(shapeJson);
        return getShapeFromJsonV1(shapeJson);
    }

    private VoxelShape getShapeFromJsonV1(JsonObject shapeJ) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (JsonElement jshape : shapeJ.getAsJsonArray("shapes")) {
            JsonArray ja = jshape.getAsJsonArray();
            VoxelShape shape = OEVoxelShapeUtil.makeBox(ja.get(0).getAsDouble(), ja.get(1).getAsDouble(), ja.get(2).getAsDouble(), ja.get(3).getAsDouble(), ja.get(4).getAsDouble(), ja.get(5).getAsDouble());
            shapes.add(shape);
        }
        return OEVoxelShapeUtil.uniteBox(shapes);
    }

    private VoxelShape getShapeFromJsonV2(JsonObject shapeJ) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (JsonElement jshape : shapeJ.getAsJsonArray("shapes")) {
            JsonArray ja = jshape.getAsJsonArray();
            VoxelShape shape = OEVoxelShapeUtil.makeBox(ja.get(0).getAsDouble() * 16, ja.get(1).getAsDouble() * 16, ja.get(2).getAsDouble() * 16, ja.get(3).getAsDouble() * 16, ja.get(4).getAsDouble() * 16, ja.get(5).getAsDouble() * 16);
            shapes.add(shape);
        }
        var shape = OEVoxelShapeUtil.uniteBox(shapes);
        Set<Edge> edges = new HashSet<>();
        for (JsonElement jshape : shapeJ.getAsJsonArray("edges")) {
            JsonArray ja = jshape.getAsJsonArray();
            edges.add(new Edge(ja.get(0).getAsDouble(), ja.get(1).getAsDouble(), ja.get(2).getAsDouble(), ja.get(3).getAsDouble(), ja.get(4).getAsDouble(), ja.get(5).getAsDouble()));
        }
        ((IkisugiVoxelShape) shape).setEdges(Collections.unmodifiableSet(edges));
        return shape;
    }

    public VoxelShape copy(VoxelShape target, IkisugiVoxelShape source) {
        ((IkisugiVoxelShape) target).setEdges(source.getEdges());
        return target;
    }

    public VoxelShape unite(VoxelShape target, VoxelShape... shapes) {
        Set<Edge> edges = new HashSet<>();
        for (VoxelShape shape : shapes) {
            var shp = ((IkisugiVoxelShape) shape).getEdges();
            if (shp != null) edges.addAll(shp);
        }
        ((IkisugiVoxelShape) target).setEdges(Collections.unmodifiableSet(edges));
        return target;
    }

    public VoxelShape unite(VoxelShape target, List<IkisugiVoxelShape> shapes) {
        Set<Edge> edges = new HashSet<>();
        shapes.forEach(n -> {
            var shp = n.getEdges();
            if (shp != null) edges.addAll(shp);
        });
        ((IkisugiVoxelShape) target).setEdges(Collections.unmodifiableSet(edges));
        return target;
    }

    public VoxelShape move(VoxelShape target, IkisugiVoxelShape source, double x, double y, double z) {
        x /= 16;
        y /= 16;
        z /= 16;
        Set<Edge> edges = new HashSet<>();
        if (source.getEdges() == null) return target;
        for (Edge edge : source.getEdges()) {
            edges.add(new Edge(edge.stX() + x, edge.stY() + y, edge.stZ() + z, edge.enX() + x, edge.enY() + y, edge.enZ() + z));
        }
        ((IkisugiVoxelShape) target).setEdges(Collections.unmodifiableSet(edges));
        return target;
    }

    public VoxelShape rotate(VoxelShape target, IkisugiVoxelShape source, RotateAngledAxis angledAxis) {
        Set<Edge> edges = new HashSet<>();
        if (source.getEdges() == null) return target;
        for (Edge edge : source.getEdges()) {
            edges.add(angledAxis.convertEdge(edge));
        }
        ((IkisugiVoxelShape) target).setEdges(Collections.unmodifiableSet(edges));
        return target;
    }
}
