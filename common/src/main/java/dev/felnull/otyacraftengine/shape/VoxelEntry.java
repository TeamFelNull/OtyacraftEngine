package dev.felnull.otyacraftengine.shape;

import net.minecraft.resources.ResourceLocation;

public class VoxelEntry {
    private final ResourceLocation location;
    private final VoxelPose pose;
    private VoxelEdge[] cacheEdges;

    public VoxelEntry(ResourceLocation location, VoxelPose pose) {
        this.location = location;
        this.pose = pose;
    }

    public VoxelEntry(ResourceLocation location) {
        this(location, new VoxelPose(0, 0, 0));
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public VoxelPose getPose() {
        return pose;
    }

    public VoxelEdge[] getCacheEdges() {
        return cacheEdges;
    }

    public void setCacheEdges(VoxelEdge[] cacheEdges) {
        this.cacheEdges = cacheEdges;
    }
}
