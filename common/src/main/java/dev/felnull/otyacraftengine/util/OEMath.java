package dev.felnull.otyacraftengine.util;

import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.motion.MotionRotation;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

public class OEMath {
    public static MotionRotation add(MotionRotation rotation, MotionRotation rotation2) {
        var c = rotation.center().copy();
        c.add(rotation2.center());

        var a = rotation.angle().copy();
        a.add(rotation2.angle());

        return new MotionRotation(a, c, Triple.of(false, false, false));
    }

    @NotNull
    public static MotionRotation leap(float delta, @NotNull MotionRotation old, @NotNull MotionRotation current) {
        return new MotionRotation(OEMath.leapAngle(delta, old, current), leap(delta, old.center(), current.center()), Triple.of(false, false, false));
    }

    @NotNull
    public static Vector3f leap(float delta, @NotNull Vector3f old, @NotNull Vector3f current) {
        return new Vector3f(Mth.lerp(delta, old.x(), current.x()), Mth.lerp(delta, old.y(), current.y()), Mth.lerp(delta, old.z(), current.z()));
    }

    @NotNull
    public static Vector3f leapAngle(float delta, @NotNull MotionRotation oldRotation, @NotNull MotionRotation currentRotation) {
        return leapAngle(delta, oldRotation.angle(), currentRotation.angle(), currentRotation.reset());
    }

    @NotNull
    public static Vector3f leapAngle(float delta, @NotNull Vector3f oldAngle, @NotNull Vector3f currentAngle, @NotNull Triple<Boolean, Boolean, Boolean> reset) {
        float x = leapAngleValue(delta, oldAngle.x(), currentAngle.x(), reset.getLeft());
        float y = leapAngleValue(delta, oldAngle.y(), currentAngle.y(), reset.getMiddle());
        float z = leapAngleValue(delta, oldAngle.z(), currentAngle.z(), reset.getRight());
        return new Vector3f(x, y, z);
    }

    private static float leapAngleValue(float delta, float old, float current, boolean reset) {
        if (!reset) {
            return Mth.lerp(delta, old, current);
        } else {
            return Mth.lerp(delta, old, toNoResetAngle(old, current));
        }
    }

    private static float toNoResetAngle(float old, float val) {
        return old - (old % 360) + val;
    }
}
