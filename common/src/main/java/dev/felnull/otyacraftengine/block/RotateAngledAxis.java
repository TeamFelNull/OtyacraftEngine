package dev.felnull.otyacraftengine.block;

import net.minecraft.world.phys.AABB;

public enum RotateAngledAxis {
    Y90((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sz, sy, 1 - sx, ez, ey, 1 - ex)),
    Y180((sx, sy, sz, ex, ey, ez) -> new ConvertPos(1 - sx, sy, 1 - sz, 1 - ex, ey, 1 - ez)),
    Y270((sx, sy, sz, ex, ey, ez) -> {
        var pos = Y180.convert.convert(sx, sy, sz, ex, ey, ez);
        return new ConvertPos(pos.sz(), pos.sy(), 1 - pos.sx(), pos.ez(), pos.ey(), 1 - pos.ex());
    }),
    X90((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sy, sx, sz, ey, ex, ez)),
    X180((sx, sy, sz, ex, ey, ez) -> new ConvertPos(1 - sx, 1 - sy, sz, 1 - ex, 1 - ey, ez)),
    X270((sx, sy, sz, ex, ey, ez) -> {
        var pos = Y180.convert.convert(sx, sy, sz, ex, ey, ez);
        return new ConvertPos(1 - pos.sy(), pos.sx(), pos.sz(), 1 - pos.ey(), pos.ex(), pos.ez());
    }),
    Z90((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sx, sz, sy, ex, ez, ey)),
    Z180((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sx, 1 - sy, 1 - sz, ex, 1 - ey, 1 - ez));

    private final Convert convert;

    private RotateAngledAxis(Convert convert) {
        this.convert = convert;
    }

    public AABB convertAABB(AABB aabb) {
        var cp = convert.convert(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
        return new AABB(cp.sx(), cp.sy(), cp.sz(), cp.ex(), cp.ey(), cp.ez());
    }

    public IIkisugiVoxelShape.Edge convertEdge(IIkisugiVoxelShape.Edge edge) {
        var cp = convert.convert(edge.stX(), edge.stY(), edge.stZ(), edge.enX(), edge.enY(), edge.enZ());
        return new IIkisugiVoxelShape.Edge(cp.sx(), cp.sy(), cp.sz(), cp.ex(), cp.ey(), cp.ez());
    }

    private static interface Convert {
        ConvertPos convert(double sx, double sy, double sz, double ex, double ey, double ez);
    }

    private static record ConvertPos(double sx, double sy, double sz, double ex, double ey, double ez) {
    }
}
