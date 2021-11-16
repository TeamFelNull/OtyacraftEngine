package dev.felnull.otyacraftengine.block;

import java.util.Set;

public interface IIkisugiVoxelShape {
    Set<Edge> getEdges();

    void setEdges(Set<Edge> edges);

    public static record Edge(double stX, double stY, double stZ, double enX, double enY, double enZ) {
    }
}
