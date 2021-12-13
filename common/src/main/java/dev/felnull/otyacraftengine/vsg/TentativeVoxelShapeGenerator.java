package dev.felnull.otyacraftengine.vsg;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TentativeVoxelShapeGenerator {
    public static JsonObject generate(VoxelShape shape, VoxelShape edge) {
        long st = System.currentTimeMillis();
        JsonObject jo = new JsonObject();
        jo.addProperty("time", System.currentTimeMillis());
        jo.addProperty("meta", "OtyacraftEngine Tentative VoxelShapeGenerator V1");
        jo.addProperty("version", 2);

        JsonArray shapes = new JsonArray();
        for (AABB toAabb : shape.toAabbs()) {
            JsonArray aabb = new JsonArray();
            aabb.add(toAabb.minX);
            aabb.add(toAabb.minY);
            aabb.add(toAabb.minZ);
            aabb.add(toAabb.maxX);
            aabb.add(toAabb.maxY);
            aabb.add(toAabb.maxZ);
            shapes.add(aabb);
        }
        jo.add("shapes", shapes);

        JsonArray edges = new JsonArray();
        edge.forAllEdges((sx, sy, sz, ex, ey, ez) -> {
            JsonArray aabb = new JsonArray();
            aabb.add(sx);
            aabb.add(sy);
            aabb.add(sz);
            aabb.add(ex);
            aabb.add(ey);
            aabb.add(ez);
            edges.add(aabb);
        });

        jo.add("edges", edges);

        jo.addProperty("elapsed", System.currentTimeMillis() - st);
        return jo;
    }
}
