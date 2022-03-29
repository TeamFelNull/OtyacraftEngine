package dev.felnull.otyacraftengine.client.debug;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionRotation;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MotionDebug {
    private static final MotionDebug INSTANCE = new MotionDebug();
    private final List<MotionPoint> points = new ArrayList<>();
    private final DebugOption option = new DebugOption();
    private Vector3f position = new Vector3f();
    private MotionRotation rotation = new MotionRotation();
    private Vector3f temporaryPosition = new Vector3f();
    private SimpleRotation temporaryRotation = new SimpleRotation();
    private Motion playMotion;
    private long cycleSpeed;

    @NotNull
    public static MotionDebug getInstance() {
        return INSTANCE;
    }

    @NotNull
    public EditType getEditType() {
        return option.editType;
    }

    public boolean isEnableTemporary() {
        return option.enableTemporary;
    }

    public boolean isEditTemporary() {
        return option.editTemporary;
    }

    public float getSensitivity() {
        return option.sensitivity;
    }

    public void setEditType(@NotNull EditType editType) {
        option.editType = editType;
    }

    public void setEnableTemporary(boolean enable) {
        option.enableTemporary = enable;
    }

    public void setEditTemporary(boolean editTemporary) {
        option.editTemporary = editTemporary;
    }

    public void setSensitivity(float sensitivity) {
        option.sensitivity = sensitivity;
    }

    public Vector3f getTemporaryPosition() {
        return temporaryPosition;
    }

    public SimpleRotation getTemporaryRotation() {
        return temporaryRotation;
    }

    public List<MotionPoint> getPoints() {
        return points;
    }

    public Vector3f getPosition() {
        return position;
    }

    public MotionRotation getRotation() {
        return rotation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(MotionRotation rotation) {
        this.rotation = rotation;
    }

    public void setRotationAngle(Vector3f angle) {
        var o = rotation.copy();
        setRotation(new MotionRotation(angle, o.center(), o.reset()));
    }

    public void setRotationCenter(Vector3f center) {
        var o = rotation.copy();
        setRotation(new MotionRotation(o.angle(), center, o.reset()));
    }

    public void setTemporaryPosition(Vector3f temporaryPosition) {
        this.temporaryPosition = temporaryPosition;
    }

    public void setTemporaryRotation(SimpleRotation temporaryRotation) {
        this.temporaryRotation = temporaryRotation;
    }

    public void addPosition(float x, float y, float z) {
        var o = getPosition().copy();
        o.add(x, y, z);
        setPosition(o);
    }

    public void addRotationAngle(float x, float y, float z) {
        var o = getRotation().angle().copy();
        o.add(x, y, z);
        setRotation(new MotionRotation(o, getRotation().center(), getRotation().reset()));
    }

    public void setRotationReset(boolean x, boolean y, boolean z) {
        setRotation(new MotionRotation(getRotation().angle(), getRotation().center(), Triple.of(x, y, z)));
    }

    public void addRotationCenter(float x, float y, float z) {
        var o = getRotation().center().copy();
        o.add(x, y, z);
        setRotation(new MotionRotation(getRotation().angle(), o, getRotation().reset()));
    }

    public void addTemporaryPosition(float x, float y, float z) {
        var o = getTemporaryPosition().copy();
        o.add(x, y, z);
        setTemporaryPosition(o);
    }

    public void addTemporaryRotationAngle(float x, float y, float z) {
        var o = getTemporaryRotation().angle().copy();
        o.add(x, y, z);
        setTemporaryRotation(new SimpleRotation(o, getRotation().center()));
    }

    public void addTemporaryRotationCenter(float x, float y, float z) {
        var o = getTemporaryRotation().center().copy();
        o.add(x, y, z);
        setTemporaryRotation(new SimpleRotation(getRotation().angle(), o));
    }

    public void reset() {
        setPosition(new Vector3f());
        setRotation(new MotionRotation());
        setTemporaryPosition(new Vector3f());
        setTemporaryRotation(new SimpleRotation());
    }

    public void pose(@NotNull PoseStack stack) {
        stack.translate(position.x(), position.y(), position.z());
        rotation.pose(stack);
        if (isEnableTemporary()) {
            stack.translate(temporaryPosition.x(), temporaryPosition.y(), temporaryPosition.z());
            temporaryRotation.pose(stack);
        }
    }

    public void poseDebug(@NotNull PoseStack stack) {
        if (playMotion == null) {
            pose(stack);
            return;
        }
        playMotion.pose(stack, OERenderUtil.getParSecond(cycleSpeed));
        if (isEnableTemporary()) {
            stack.translate(temporaryPosition.x(), temporaryPosition.y(), temporaryPosition.z());
            temporaryRotation.pose(stack);
        }
    }

    public MotionPoint createPoint() {
        return new MotionPoint(getPosition().copy(), getRotation().copy());
    }

    public void setPoint(MotionPoint point) {
        setPosition(point.position());
        setRotation(point.rotation());
    }

    @NotNull
    public Motion createMotion() {
        return new Motion(ImmutableList.copyOf(getPoints()));
    }

    public void setMotion(Motion motion) {
        reset();
        points.clear();
        points.addAll(motion.points());
    }

    public void startMotion(long cycleSpeed) {
        playMotion = createMotion();
        this.cycleSpeed = cycleSpeed;
    }

    public void stopMotion() {
        playMotion = null;
    }

    public boolean isMotionPlaying() {
        return playMotion != null;
    }

    public static record SimpleRotation(Vector3f angle, Vector3f center) {
        public SimpleRotation() {
            this(new Vector3f(), new Vector3f());
        }

        public void pose(PoseStack poseStack) {
            poseStack.translate(center.x(), center.y(), center.z());
            OERenderUtil.poseRotateAll(poseStack, angle.x(), angle.y(), angle.z());
            poseStack.translate(-center.x(), -center.y(), -center.z());
        }
    }

    private static class DebugOption {
        private boolean enableTemporary;
        private boolean editTemporary;
        @NotNull
        private EditType editType = EditType.POSITION;
        private float sensitivity = 1;
    }

    public static enum EditType {
        POSITION, ROTATION, ROTATION_CENTER;
    }
}
