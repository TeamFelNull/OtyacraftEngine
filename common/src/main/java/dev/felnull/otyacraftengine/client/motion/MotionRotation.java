package dev.felnull.otyacraftengine.client.motion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEMath;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

public record MotionRotation(Vector3f angle, Vector3f center, Triple<Boolean, Boolean, Boolean> reset) {
    public MotionRotation(float ax, float ay, float az, float cx, float cy, float cz, boolean rx, boolean ry, boolean rz) {
        this(new Vector3f(ax, ay, az), new Vector3f(cx, cy, cz), Triple.of(rx, ry, rz));
    }

    public MotionRotation copy() {
        return new MotionRotation(angle.copy(), center.copy(), reset);
    }

    public MotionRotation add(MotionRotation rotation) {
        return OEMath.add(this, rotation);
    }

    public MotionRotation() {
        this(new Vector3f(), new Vector3f(), Triple.of(false, false, false));
    }

    public static MotionRotation of(JsonObject jo) {
        var ja = jo.getAsJsonArray("angle");
        var jc = jo.getAsJsonArray("center");
        var jr = jo.getAsJsonArray("reset");
        Triple<Boolean, Boolean, Boolean> res = Triple.of(false, false, false);
        if (jr != null)
            res = Triple.of(jr.get(0).getAsBoolean(), jr.get(1).getAsBoolean(), jr.get(2).getAsBoolean());
        return new MotionRotation(new Vector3f(ja.get(0).getAsFloat(), ja.get(1).getAsFloat(), ja.get(2).getAsFloat()), new Vector3f(jc.get(0).getAsFloat(), jc.get(1).getAsFloat(), jc.get(2).getAsFloat()), res);
    }

    public JsonObject toJson() {
        var jo = new JsonObject();
        var a = new JsonArray();
        a.add(angle.x());
        a.add(angle.y());
        a.add(angle.z());
        jo.add("angle", a);

        var c = new JsonArray();
        c.add(center.x());
        c.add(center.y());
        c.add(center.z());
        jo.add("center", c);

        if (!reset.getRight() && !reset.getMiddle() && !reset.getLeft()) {
            var r = new JsonArray();
            r.add(reset.getLeft());
            r.add(reset.getMiddle());
            r.add(reset.getRight());
            jo.add("reset", r);
        }
        return jo;
    }

    public void pose(@NotNull PoseStack poseStack) {
        poseStack.translate(center.x(), center.y(), center.z());
        OERenderUtil.poseRotateAll(poseStack, angle.x(), angle.y(), angle.z());
        poseStack.translate(-center.x(), -center.y(), -center.z());
    }
}
