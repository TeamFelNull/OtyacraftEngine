package red.felnull.otyacraftengine.util;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.ArrayList;
import java.util.List;

public class VoxelShapeHelper {
    public static VoxelShape makeCuboidShaoe0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.makeCuboidShape(x1, y1, z1, x2, y2, z2);
    }

    public static VoxelShape makeCuboidShaoe90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe0(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    public static VoxelShape makeCuboidShaoe180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe0(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2);
    }

    public static VoxelShape makeCuboidShaoe270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe180(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    public static VoxelShape addCuboidShaoe0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe0(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addCuboidShaoe90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe90(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addCuboidShaoe180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe180(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addCuboidShaoe270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeCuboidShaoe270(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape allOr(VoxelShape... shapes) {
        if (shapes.length == 1)
            return shapes[0];
        List<VoxelShape> shapesll = new ArrayList<VoxelShape>();
        VoxelShape fristV = null;
        boolean isFrist = false;
        for (VoxelShape box : shapes) {
            if (!isFrist) {
                isFrist = true;
                fristV = box;
            } else {
                shapesll.add(box);
            }
        }
        return VoxelShapes.or(fristV, shapesll.toArray(new VoxelShape[1]));
    }

    public static VoxelShape translate(VoxelShape shape, double x, double y, double z) {
        List<VoxelShape> shapes = new ArrayList<VoxelShape>();
        shape.toBoundingBoxList().forEach(n -> shapes.add(VoxelShapes.create(n.offset(1f / 16f * x, 1f / 16f * y, 1f / 16f * z))));
        return allOr(shapes.toArray(new VoxelShape[1]));
    }

    public static VoxelShape rotate90(VoxelShape shape) {
        List<VoxelShape> shapes = new ArrayList<VoxelShape>();
        shape.toBoundingBoxList().forEach(n -> shapes.add(VoxelShapes.create(new AxisAlignedBB(n.minZ, n.minY, 1 - n.minX, n.maxZ, n.maxY, 1 - n.maxX))));
        return allOr(shapes.toArray(new VoxelShape[1]));
    }

    public static VoxelShape rotate180(VoxelShape shape) {
        List<VoxelShape> shapes = new ArrayList<VoxelShape>();
        shape.toBoundingBoxList().forEach(n -> shapes.add(VoxelShapes.create(new AxisAlignedBB(1 - n.minX, n.minY, 1 - n.minZ, 1 - n.maxX, n.maxY, 1 - n.maxZ))));
        return allOr(shapes.toArray(new VoxelShape[1]));
    }


    public static VoxelShape rotate270(VoxelShape shape) {
        shape = rotate180(shape);
        List<VoxelShape> shapes = new ArrayList<VoxelShape>();
        shape.toBoundingBoxList().forEach(n -> shapes.add(VoxelShapes.create(new AxisAlignedBB(n.minZ, n.minY, 1 - n.minX, n.maxZ, n.maxY, 1 - n.maxX))));
        return allOr(shapes.toArray(new VoxelShape[1]));
    }

    public static VoxelShape rotateDirection(VoxelShape shape, Direction direction) {
        switch (direction) {
            case SOUTH:
                return rotate180(shape);
            case EAST:
                return rotate270(shape);
            case WEST:
                return rotate90(shape);
            default:
                return shape;
        }
    }
}
