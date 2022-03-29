package dev.felnull.otyacraftengine.client.debug;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.model.OETestModels;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionRotation;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
    private float ratio = 1f;
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

    public boolean isFixOrigin() {
        return option.fixOrigin;
    }

    public void setFixOrigin(boolean fix) {
        option.fixOrigin = fix;
    }

    public boolean isShowOrigin() {
        return option.showOrigin;
    }

    public void setShowOrigin(boolean show) {
        this.option.showOrigin = show;
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

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(MotionRotation rotation) {
        this.rotation = rotation;
    }

    public void setRotationAngle(Vector3f angle) {
        var o = rotation.copy();
        setRotation(new MotionRotation(angle, o.origin(), o.reset()));
    }

    public void setRotationOrigin(Vector3f origin) {
        setRotationOrigin(origin, false);
    }

    public void setRotationOrigin(Vector3f origin, boolean force) {
        if (isFixOrigin() && !force) return;
        var o = rotation.copy();
        setRotation(new MotionRotation(o.angle(), origin, o.reset()));
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
        setRotation(new MotionRotation(o, getRotation().origin(), getRotation().reset()));
    }

    public void setRotationReset(boolean x, boolean y, boolean z) {
        setRotation(new MotionRotation(getRotation().angle(), getRotation().origin(), Triple.of(x, y, z)));
    }

    public void addRotationOrigin(float x, float y, float z) {
        if (isFixOrigin()) return;
        var o = getRotation().origin().copy();
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
        setTemporaryRotation(new SimpleRotation(o, getRotation().origin()));
    }

    public void addTemporaryRotationOrigin(float x, float y, float z) {
        if (isFixOrigin()) return;
        var o = getTemporaryRotation().origin().copy();
        o.add(x, y, z);
        setTemporaryRotation(new SimpleRotation(getRotation().angle(), o));
    }

    public void reset() {
        if (isEditTemporary()) {
            setTemporaryPosition(new Vector3f());
            setTemporaryRotation(new SimpleRotation());
        } else {
            setPosition(new Vector3f());
            var or = getRotation().origin().copy();
            setRotation(new MotionRotation());
            if (isFixOrigin())
                setRotationOrigin(or, true);
        }
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

    public void onDebug(@NotNull PoseStack stack, MultiBufferSource multiBufferSource, float scale) {
        poseDebug(stack);
        stack.pushPose();

        if (!isShowOrigin()) return;

        stack.pushPose();
        stack.translate(rotation.origin().x(), rotation.origin().y(), rotation.origin().z());
        OERenderUtil.poseScaleAll(stack, 0.1f * scale);
        OERenderUtil.renderModel(stack, multiBufferSource.getBuffer(Sheets.cutoutBlockSheet()), OEModelUtil.getModel(OETestModels.XYZ_AXIS), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        stack.popPose();

        stack.pushPose();
        stack.translate(rotation.origin().x(), rotation.origin().y(), rotation.origin().z());
        OERenderUtil.poseScaleAll(stack, scale);
        OERenderUtil.renderModel(stack, multiBufferSource.getBuffer(Sheets.cutoutBlockSheet()), OEModelUtil.getModel(OETestModels.ORIGIN), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        stack.popPose();

        stack.popPose();
    }

    public MotionPoint createPoint() {
        return new MotionPoint(getPosition().copy(), getRotation().copy(), ratio);
    }

    public void setPoint(MotionPoint point) {
        setPosition(point.getPosition());
        setRotation(point.getRotation());
        setRatio(point.getRatio());
    }

    @NotNull
    public Motion createMotion() {
        return new Motion(ImmutableList.copyOf(getPoints()));
    }

    public void setMotion(Motion motion) {
        reset();
        points.clear();
        points.addAll(motion.getPoints());
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

    public static record SimpleRotation(Vector3f angle, Vector3f origin) {
        public SimpleRotation() {
            this(new Vector3f(), new Vector3f());
        }

        public void pose(PoseStack poseStack) {
            poseStack.translate(origin.x(), origin.y(), origin.z());
            OERenderUtil.poseRotateAll(poseStack, angle.x(), angle.y(), angle.z());
            poseStack.translate(-origin.x(), -origin.y(), -origin.z());
        }
    }

    private static class DebugOption {
        private boolean enableTemporary;
        private boolean editTemporary;
        @NotNull
        private EditType editType = EditType.POSITION;
        private float sensitivity = 1;
        private boolean fixOrigin = true;
        private boolean showOrigin = true;
    }

    public static enum EditType {
        POSITION, ROTATION, ROTATION_ORIGIN;
    }
}
