package red.felnull.otyacraftengine.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;

public class IKSGVoxelShapeUtils {
    public static VoxelShape makeBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    public static VoxelShape makeBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    public static VoxelShape makeBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2);
    }

    public static VoxelShape makeBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    public static VoxelShape addBoxY0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY90(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape addBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY270(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    public static VoxelShape uniteBox(VoxelShape... shapes) {
        return Shapes.or(shapes[0], ArrayUtils.remove(shapes, 0));
    }

    public static VoxelShape translateBox(VoxelShape shape, double x, double y, double z) {
        VoxelShape[] shapes = {};
        for (AABB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, Shapes.create(aabb.move(1d / 16d * x, 1d / 16d * y, 1d / 16d * z)));
        }
        return uniteBox(shapes);
    }

    public static VoxelShape rotateBoxY90(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AABB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, Shapes.create(new AABB(aabb.minZ, aabb.minY, 1 - aabb.minX, aabb.maxZ, aabb.maxY, 1 - aabb.maxX)));
        }
        return uniteBox(shapes);
    }

    public static VoxelShape rotateBoxY180(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AABB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, Shapes.create(new AABB(1 - aabb.minX, aabb.minY, 1 - aabb.minZ, 1 - aabb.maxX, aabb.maxY, 1 - aabb.maxZ)));
        }
        return uniteBox(shapes);
    }

    public static VoxelShape rotateBoxY270(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AABB aabb : rotateBoxY180(shape).toAabbs()) {
            shapes = ArrayUtils.add(shapes, Shapes.create(new AABB(aabb.minZ, aabb.minY, 1 - aabb.minX, aabb.maxZ, aabb.maxY, 1 - aabb.maxX)));
        }
        return uniteBox(shapes);
    }

    public static VoxelShape rotateBoxDirection(VoxelShape shape, Direction direction) {
        switch (direction) {
            case SOUTH:
                return rotateBoxY180(shape);
            case EAST:
                return rotateBoxY270(shape);
            case WEST:
                return rotateBoxY90(shape);
            default:
                return shape;
        }
    }
}
