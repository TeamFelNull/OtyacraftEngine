package dev.felnull.otyacraftengine.client.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import org.jetbrains.annotations.NotNull;

public class MotionDebug {
    private static final MotionDebug INSTANCE = new MotionDebug();
    private final Vector3f currentPosition = new Vector3f();
    private final Vector3f currentRotation = new Vector3f();
    private final Vector3f currentScale = new Vector3f(1, 1, 1);

    @NotNull
    public static MotionDebug getInstance() {
        return INSTANCE;
    }

    @NotNull
    public Vector3f getCurrentPosition() {
        return currentPosition;
    }

    @NotNull
    public Vector3f getCurrentRotation() {
        return currentRotation;
    }

    @NotNull
    public Vector3f getCurrentScale() {
        return currentScale;
    }

    public void setCurrentPosition(float x, float y, float z) {
        currentPosition.set(x, y, z);
    }

    public void setCurrentRotation(float x, float y, float z) {
        currentRotation.set(x, y, z);
    }

    public void setCurrentScale(float x, float y, float z) {
        currentScale.set(x, y, z);
    }

    public void poseCurrent(@NotNull PoseStack stack) {
        stack.translate(currentPosition.x(), currentScale.y(), currentScale.z());
        OERenderUtil.poseRotateAll(stack, currentRotation.x(), currentRotation.y(), currentRotation.z());
        stack.scale(currentScale.x(), currentScale.y(), currentScale.z());
    }
}
