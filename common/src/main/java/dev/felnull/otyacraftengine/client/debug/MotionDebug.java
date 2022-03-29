package dev.felnull.otyacraftengine.client.debug;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionRotation;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEMath;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MotionDebug {
    private static final MotionDebug INSTANCE = new MotionDebug();
    private final List<MotionPoint> points = new ArrayList<>();
    private final Vector3f currentPosition = new Vector3f();
    private MotionRotation currentRotation = new MotionRotation();
    private final Vector3f currentScale = new Vector3f(1, 1, 1);
    private final Vector3f temporaryPosition = new Vector3f();
    private MotionRotation temporaryRotation = new MotionRotation();
    private final Vector3f temporaryScale = new Vector3f();
    private Vector3f position = new Vector3f();
    private MotionRotation rotation = new MotionRotation();
    private Vector3f oldPosition = new Vector3f();
    private MotionRotation oldRotation = new MotionRotation();
    private boolean temporary;
    private boolean editTemporary;
    private boolean editRotation;
    private float sensitivity = 1;
    private long cycleSpeed;
    private Motion debugMotion;


    public boolean isEditRotation() {
        return editRotation;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setEditRotation(boolean editRotation) {
        this.editRotation = editRotation;
    }

    @NotNull
    public static MotionDebug getInstance() {
        return INSTANCE;
    }

    @NotNull
    public Vector3f getCurrentPosition() {
        return currentPosition;
    }

    @NotNull
    public MotionRotation getCurrentRotation() {
        return currentRotation;
    }

    @NotNull
    public Vector3f getCurrentScale() {
        return currentScale;
    }

    public Vector3f getTemporaryPosition() {
        return temporaryPosition;
    }

    public MotionRotation getTemporaryRotation() {
        return temporaryRotation;
    }

    public Vector3f getTemporaryScale() {
        return temporaryScale;
    }

    public void setTemporaryPosition(float x, float y, float z) {
        temporaryPosition.set(x, y, z);
    }

    public void setTemporaryRotation(float ax, float ay, float az, float cx, float cy, float cz, boolean rx, boolean ry, boolean rz) {
        temporaryRotation = new MotionRotation(ax, ay, az, cx, cy, cz, rx, ry, rz);
    }

    public void setTemporaryScale(float x, float y, float z) {
        temporaryScale.set(x, y, z);
    }

    public boolean isEditTemporary() {
        return editTemporary;
    }

    public void setEditTemporary(boolean editTemporary) {
        this.editTemporary = editTemporary;
    }

    public void setCurrentPosition(float x, float y, float z) {
        currentPosition.set(x, y, z);
    }

    public void setCurrentRotation(float ax, float ay, float az, float cx, float cy, float cz, boolean rx, boolean ry, boolean rz) {
        currentRotation = new MotionRotation(ax, ay, az, cx, cy, cz, rx, ry, rz);
    }

    public void setCurrentScale(float x, float y, float z) {
        currentScale.set(x, y, z);
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public Vector3f getPosition(float delta) {
        return new Vector3f(Mth.lerp(delta, oldPosition.x(), position.x()), Mth.lerp(delta, oldPosition.y(), position.y()), Mth.lerp(delta, oldPosition.z(), position.z()));
    }

    public Vector3f getPosition() {
        var cp = currentPosition.copy();
        if (temporary)
            cp.add(temporaryPosition);
        return cp;
    }

    public MotionRotation getRotation(float delta) {
        return OEMath.leap(delta, oldRotation, rotation);
    }

    private static float lerpRoted(float delta, float old, float current) {
        float mr = 180f;
        float sa = Mth.abs(current - old);
        if (sa > mr) {
            if (old < current)
                current = -(360 - current);
            else
                current = 360 + current;
        }
        return Mth.lerp(delta, old, current);
    }

    public MotionRotation getRotation() {
        var cr = currentRotation.copy();
        if (temporary)
            cr = cr.add(temporaryRotation);
        return cr;
    }

    public Vector3f getScale() {
        var cs = currentScale.copy();
        if (temporary)
            cs.add(temporaryScale);
        return cs;
    }

    public void setPosition(float x, float y, float z) {
        if (editTemporary)
            setTemporaryPosition(x, y, z);
        else
            setCurrentPosition(x, y, z);
    }

    public void addPosition(float x, float y, float z) {
        if (editTemporary) {
            var tr = getTemporaryPosition();
            setTemporaryPosition(tr.x() + x, tr.y() + y, tr.z() + z);
        } else {
            var cr = getCurrentPosition();
            setCurrentPosition(cr.x() + x, cr.y() + y, cr.z() + z);
        }
    }

    public void addRotation(float x, float y, float z) {
        if (editTemporary) {
            var tr = getTemporaryRotation();
         //   setTemporaryRotation(addRotation(tr.x(), x), addRotation(tr.y(), y), addRotation(tr.z(), z));
        } else {
            var cr = getCurrentRotation();
        //    setCurrentRotation(addRotation(cr.x(), x), addRotation(cr.y(), y), addRotation(cr.z(), z));
        }
    }

    public void tick() {
        oldRotation = rotation;
        rotation = getRotation();

        oldPosition = position;
        position = getPosition();
    }

    private float addRotation(float v, float add) {
        return (v + add) % 360;
    }

    public void setRotation(float x, float y, float z) {
   /*     if (editTemporary)
            setTemporaryRotation(x, y, z);
        else
            setCurrentRotation(x, y, z);*/
    }

    public void setScale(float x, float y, float z) {
        if (editTemporary)
            setTemporaryScale(x, y, z);
        else
            setCurrentScale(x, y, z);
    }

    public void reset() {
        setPosition(0, 0, 0);
        setRotation(0, 0, 0);
        if (temporary) {
            setTemporaryScale(0, 0, 0);
        } else {
            setCurrentScale(1, 1, 1);
        }
    }

    public void poseCurrent(@NotNull PoseStack stack, float delta) {
        var pos = getPosition(delta);
        var rot = getRotation(delta);
        var scale = getScale();
        stack.translate(pos.x(), pos.y(), pos.z());
     //   OERenderUtil.poseRotateAll(stack, rot.x(), rot.y(), rot.z());
        stack.scale(scale.x(), scale.y(), scale.z());
    }

    public void poseCurrent(@NotNull PoseStack stack) {
        var pos = getPosition();
        var rot = getRotation();
        var scale = getScale();
        stack.translate(pos.x(), pos.y(), pos.z());
      //  OERenderUtil.poseRotateAll(stack, rot.x(), rot.y(), rot.z());
        stack.scale(scale.x(), scale.y(), scale.z());
    }

    public void poseDebug(@NotNull PoseStack stack, float delta) {
        if (debugMotion == null) {
            poseCurrent(stack, delta);
            return;
        }
        debugMotion.pose(stack, OERenderUtil.getParSecond(cycleSpeed));
    }

    public MotionPoint getCurrentEntry() {
        // return new MotionPoint(getCurrentPosition().copy(), getCurrentRotation().copy());
        return null;
    }

    public void load(MotionPoint entry) {
        setCurrentPosition(entry.position().x(), entry.position().y(), entry.position().z());
        // setCurrentRotation(entry.rotation().x(), entry.rotation().y(), entry.rotation().z());
    }

    public List<MotionPoint> getPoints() {
        return points;
    }

    public Motion getMotion() {
        return new Motion(ImmutableList.copyOf(getPoints()));
    }

    public void setMotion(Motion motion) {
        reset();
        points.clear();
        points.addAll(motion.points());
    }

    public void startMotion(long cycleSpeed) {
        debugMotion = getMotion();
        this.cycleSpeed = cycleSpeed;
    }

    public void stopMotion() {
        debugMotion = null;
    }

    public boolean isMotionTesting() {
        return debugMotion != null;
    }
}
