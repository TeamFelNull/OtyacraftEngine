package dev.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.block.IIkisugiVoxelShape;
import dev.felnull.otyacraftengine.block.IkisugiVoxelShape;
import dev.felnull.otyacraftengine.block.RotateAngledAxis;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * VoxelShapeを簡単に移動や回転をできるようにする
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class OEVoxelShapeUtil {
    private static final Gson gson = new Gson();

    /**
     * VoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    /**
     * Y90度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    /**
     * Y180度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2);
    }

    /**
     * Y270度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    /**
     * VoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y90度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY90(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y180度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y270度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY270(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * VoxelShapeを合体する
     *
     * @param shapes 合わせるVoxelShape
     * @return 合体したVoxelShape
     */
    public static VoxelShape uniteBox(VoxelShape... shapes) {
        var shape = Shapes.or(shapes[0], ArrayUtils.remove(shapes, 0));
        return IkisugiVoxelShape.getInstance().unite(shape, shapes);
    }

    /**
     * VoxelShapeを合体する
     *
     * @param shapes 合わせるVoxelShape
     * @return 合体したVoxelShape
     */
    public static VoxelShape uniteBox(List<VoxelShape> shapes) {
        if (shapes.isEmpty())
            return Shapes.empty();
        List<VoxelShape> shapesCp = new ArrayList<>(shapes);
        var voxelShape = shapesCp.remove(0);
        var shape = shapesCp.stream().reduce(voxelShape, Shapes::or);
        return IkisugiVoxelShape.getInstance().unite(shape, shapes.stream().map(n -> (IIkisugiVoxelShape) n).toList());
    }

    /**
     * VoxelShapeを移動する
     * 1.0fで16分の１ブロック分移動
     *
     * @param shape 元のVoxelShape
     * @param x     X方向
     * @param y     Y方向
     * @param z     Z方向
     * @return 移動し終わったあとのVoxelShape
     */
    public static VoxelShape moveBox(VoxelShape shape, double x, double y, double z) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (AABB aabb : shape.toAabbs()) {
            shapes.add(Shapes.create(aabb.move(1d / 16d * x, 1d / 16d * y, 1d / 16d * z)));
        }
        var ushape = uniteBox(shapes);
        return IkisugiVoxelShape.getInstance().move(ushape, (IIkisugiVoxelShape) shape, x, y, z);
    }

    /**
     * VoxelShapeを指定した角度に回転する
     *
     * @param shape      回転するVoxelShape
     * @param angledAxis 角度
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBox(VoxelShape shape, RotateAngledAxis angledAxis) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (AABB aabb : shape.toAabbs()) {
            shapes.add(Shapes.create(angledAxis.convertAABB(aabb)));
        }
        var ushape = uniteBox(shapes);
        return IkisugiVoxelShape.getInstance().rotate(ushape, (IIkisugiVoxelShape) shape, angledAxis);
    }

    /**
     * VoxelShapeをY90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY90(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Y90);
    }

    /**
     * VoxelShapeをY180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY180(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Y180);
    }

    /**
     * VoxelShapeをY270度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY270(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Y270);
    }

    /**
     * VoxelShapeをX90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX90(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.X90);
    }

    /**
     * VoxelShapeをX180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX180(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.X180);
    }

    /**
     * VoxelShapeをX270度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX270(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.X270);
    }

    /**
     * VoxelShapeをZ90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxZ90(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Z90);
    }

    /**
     * VoxelShapeをZ180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxZ180(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Z180);
    }

    /**
     * VoxelShapeをDirectionの方向に回転する
     *
     * @param shape     回転するVoxelShape
     * @param direction 回転する方向のDirection
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxDirection(VoxelShape shape, Direction direction) {
        return switch (direction) {
            case SOUTH -> rotateBoxY180(shape);
            case EAST -> rotateBoxY270(shape);
            case WEST -> rotateBoxY90(shape);
            default -> shape;
        };
    }

    public static VoxelShape getShapeFromResource(ResourceLocation location) {
        InputStream stream = OEVoxelShapeUtil.class.getResourceAsStream("/data/" + location.getNamespace() + "/shape/" + location.getPath() + ".json");
        if (stream == null) {
            return makeBox(16, 16, 16, 16, 16, 16);
        }
        stream = new BufferedInputStream(stream);
        return IkisugiVoxelShape.getInstance().getShapeFromJson(gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class));
    }

    public static DirectionVoxelShapes makeAllDirection(VoxelShape shape) {
        return new DirectionVoxelShapes(shape, rotateBoxDirection(shape, Direction.SOUTH), rotateBoxDirection(shape, Direction.EAST), rotateBoxDirection(shape, Direction.WEST));
    }

    public static record DirectionVoxelShapes(VoxelShape north, VoxelShape south, VoxelShape east, VoxelShape west) {
        public VoxelShape getShape(Direction direction) {
            return switch (direction) {
                case SOUTH -> south;
                case EAST -> east;
                case WEST -> west;
                default -> north;
            };
        }

    }

}