package dev.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Consumer;

public class OERenderUtils {
    /**
     * PoseStackを16分の１単位で移動する
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param z         Z
     */
    public static void poseTrans16(PoseStack poseStack, double x, double y, double z) {
        float pix = 1f / 16f;
        poseStack.translate(pix * x, pix * y, pix * z);
    }

    /**
     * PoseStackをすべてのスケールを設定する
     *
     * @param poseStack PoseStack
     * @param scale     すべてのスケール
     */
    public static void poseScaleAll(PoseStack poseStack, float scale) {
        poseStack.scale(scale, scale, scale);
    }

    /**
     * PoseStackの角度をそれぞれ設定する
     *
     * @param poseStack PoseStack
     * @param x         X角度
     * @param y         Y角度
     * @param z         Z角度
     */
    public static void poseRotateAll(PoseStack poseStack, float x, float y, float z) {
        poseRotateX(poseStack, x);
        poseRotateY(poseStack, y);
        poseRotateZ(poseStack, z);
    }

    /**
     * PoseStackのX角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateX(PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.XP.rotationDegrees(angle));
    }

    /**
     * PoseStackのY角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateY(PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));
    }

    /**
     * PoseStackのZ角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateZ(PoseStack poseStack, float angle) {
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(angle));
    }

    /**
     * PoseStackの方向のブロックステートに設定する
     *
     * @param poseStack PoseStack
     * @param state     角度
     * @param roted     回転ずれ
     */
    public static void poseRotateHorizontalState(PoseStack poseStack, BlockState state, int roted) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        poseRotateDirection(poseStack, direction, roted);
    }

    /**
     * PoseStackをDirectionの方向にする
     *
     * @param poseStack PoseStack
     * @param direction 方向
     * @param roted     回転ずれ
     */
    public static void poseRotateDirection(PoseStack poseStack, Direction direction, int roted) {
        for (int i = 0; i < roted; i++) {
            direction = direction.getClockWise();
        }
        if (direction == Direction.WEST) {
            poseRotateY(poseStack, 180);
            poseStack.translate(-1f, 0f, -1f);
        } else if (direction == Direction.NORTH) {
            poseRotateY(poseStack, 90);
            poseStack.translate(-1f, 0f, 0f);
        } else if (direction == Direction.SOUTH) {
            poseRotateY(poseStack, 270);
            poseStack.translate(0f, 0f, -1f);
        }
    }

    public static void poseCenterConsumer(PoseStack poseStack, float centerX, float centerY, float centerZ, Consumer<PoseStack> poseStackConsumer) {
        poseStack.translate(centerX, centerX, centerX);
        poseStackConsumer.accept(poseStack);
        poseStack.translate(-centerX, -centerX, -centerX);
    }

    public static float getParSecond(long loopTime) {
        return (float) (System.currentTimeMillis() % loopTime) / (float) loopTime;
    }
}
