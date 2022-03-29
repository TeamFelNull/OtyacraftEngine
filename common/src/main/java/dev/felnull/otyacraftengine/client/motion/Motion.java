package dev.felnull.otyacraftengine.client.motion;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.util.OEMath;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Motion {
    private final List<MotionPoint> points;
    private final float[] elapsedRatio;

    public Motion(@NotNull List<MotionPoint> points) {
        if (!(points instanceof ImmutableCollection<?>))
            points = ImmutableList.copyOf(points);
        this.points = points;
        this.elapsedRatio = new float[this.points.size()];
        if (elapsedRatio.length == 0) return;
        this.elapsedRatio[0] = 0;
        if (elapsedRatio.length == 1) return;
        for (int i = 1; i < elapsedRatio.length; i++) {
            this.elapsedRatio[i] = this.elapsedRatio[i - 1] + this.points.get(i - 1).getRatio();
        }
    }

    public static Motion of(JsonObject json) {
        int v = json.get("version").getAsInt();
        if (v != 1) throw new IllegalStateException("Unsupported motion version");
        var ja = json.getAsJsonArray("points");
        ImmutableList.Builder<MotionPoint> builder = ImmutableList.builder();
        ja.forEach(n -> builder.add(MotionPoint.of(n.getAsJsonObject())));
        return new Motion(builder.build());
    }

    public JsonObject toJson() {
        var jo = new JsonObject();
        jo.addProperty("version", 1);
        var ja = new JsonArray();
        points.forEach(n -> ja.add(n.toJson()));
        jo.add("points", ja);
        return jo;
    }

    public float getTotalRatio() {
        return elapsedRatio[elapsedRatio.length - 1];
    }

    public List<MotionPoint> getPoints() {
        return points;
    }

    @NotNull
    public Triple<MotionPoint, MotionPoint, Integer> getPontByRatio(float ratio) {
        ratio = Mth.clamp(ratio, 0, getTotalRatio());
        int num = 0;
        for (int i = 0; i < elapsedRatio.length - 1; i++) {
            float ls = elapsedRatio[i];
            float le = elapsedRatio[i + 1];
            if (ls < ratio && le > ratio) {
                num = i;
                break;
            }
        }
        return Triple.of(points.get(num), points.get(num + 1), num);
    }

    public MotionPose getPose(float par) {
        return getPose(par, MotionSwapper.EMPTY);
    }

    public MotionPose getPose(float par, MotionSwapper swapper) {
        if (points.isEmpty()) return new MotionPose(Vector3f.ZERO, new MotionRotation());
        float rp = getTotalRatio() * par;
        var iv = getPontByRatio(rp);
        var st = iv.getLeft();
        var en = iv.getMiddle();

        var ps = swapper.swapPrePoint(this, par, iv.getRight(), st, en);
        var ns = swapper.swapNextPoint(this, par, iv.getRight(), st, en);
        if (ps != null)
            st = ps;
        if (ns != null)
            en = ns;

        Vector3f pos;
        MotionRotation rot;
        if (st == en) {
            pos = st.getPosition();
            rot = st.getRotation();
        } else {
            float rrp = st.getRatio() / getTotalRatio();
            float rep = elapsedRatio[iv.getRight()] / getTotalRatio();
            float op = (par - rep) / rrp;
            pos = OEMath.leap(op, st.getPosition(), en.getPosition());
            rot = OEMath.leap(op, st.getRotation(), en.getRotation());
        }
        return new MotionPose(pos, rot);
    }

    public void pose(float par, Consumer<MotionPose> pose, MotionSwapper swapper) {
        pose.accept(getPose(par, swapper));
    }

    public void pose(float par, Consumer<MotionPose> pose) {
        pose(par, pose, MotionSwapper.EMPTY);
    }

    public void pose(@NotNull PoseStack stack, float par, MotionSwapper swapper) {
        var pose = getPose(par, swapper);
        pose.pose(stack);
    }

    public void pose(@NotNull PoseStack stack, float par) {
        pose(stack, par, MotionSwapper.EMPTY);
    }

    @Override
    public String toString() {
        return "Motion{" +
                "points=" + points +
                ", elapsedRatio=" + Arrays.toString(elapsedRatio) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motion motion = (Motion) o;
        return Objects.equals(points, motion.points) && Arrays.equals(elapsedRatio, motion.elapsedRatio);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(points);
        result = 31 * result + Arrays.hashCode(elapsedRatio);
        return result;
    }


}
