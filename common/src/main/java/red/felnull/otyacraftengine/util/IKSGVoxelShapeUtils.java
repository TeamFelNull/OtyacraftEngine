package red.felnull.otyacraftengine.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class IKSGVoxelShapeUtils {
    public static VoxelShape makeBox0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    public static VoxelShape makeBox90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox0(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    public static VoxelShape makeBox180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox0(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2);
    }

    public static VoxelShape makeBox270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox180(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    public static VoxelShape addBox0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox0(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addBox90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox90(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addBox180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox180(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addBox270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox270(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape uniteBox(VoxelShape... shapes) {
        if (shapes.length == 1)
            return shapes[0];
        List<VoxelShape> shapesll = new ArrayList<>();
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
        return Shapes.or(fristV, shapesll.toArray(new VoxelShape[1]));
    }

    public static VoxelShape translateBox(VoxelShape shape, double x, double y, double z) {
        List<VoxelShape> shapes = new ArrayList<>();
        shape.toAabbs().forEach(n -> shapes.add(Shapes.create(n.move(1f / 16f * x, 1f / 16f * y, 1f / 16f * z))));
        return uniteBox(shapes.toArray(new VoxelShape[1]));
    }

    public static VoxelShape rotateBox90(VoxelShape shape) {
        List<VoxelShape> shapes = new ArrayList<>();
        shape.toAabbs().forEach(n -> shapes.add(Shapes.create(new AABB(n.minZ, n.minY, 1 - n.minX, n.maxZ, n.maxY, 1 - n.maxX))));
        return uniteBox(shapes.toArray(new VoxelShape[1]));
    }

    public static VoxelShape rotateBox180(VoxelShape shape) {
        List<VoxelShape> shapes = new ArrayList<>();
        shape.toAabbs().forEach(n -> shapes.add(Shapes.create(new AABB(1 - n.minX, n.minY, 1 - n.minZ, 1 - n.maxX, n.maxY, 1 - n.maxZ))));
        return uniteBox(shapes.toArray(new VoxelShape[1]));
    }


    public static VoxelShape rotateBox270(VoxelShape shape) {
        shape = rotateBox180(shape);
        List<VoxelShape> shapes = new ArrayList<>();
        shape.toAabbs().forEach(n -> shapes.add(Shapes.create(new AABB(n.minZ, n.minY, 1 - n.minX, n.maxZ, n.maxY, 1 - n.maxX))));
        return uniteBox(shapes.toArray(new VoxelShape[1]));
    }

    public static VoxelShape rotateBoxDirection(VoxelShape shape, Direction direction) {
        switch (direction) {
            case SOUTH:
                return rotateBox180(shape);
            case EAST:
                return rotateBox270(shape);
            case WEST:
                return rotateBox90(shape);
            default:
                return shape;
        }
    }
}
