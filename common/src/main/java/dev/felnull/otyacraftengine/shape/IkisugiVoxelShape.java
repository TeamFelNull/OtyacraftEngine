package dev.felnull.otyacraftengine.shape;

public interface IkisugiVoxelShape {
    VoxelEntry[] getRenderEdges();

    void setRenderEdges(VoxelEntry... edges);
}
