package dev.felnull.otyacraftengine.client.motion;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Motion(@NotNull List<MotionPoint> points) {
    public Motion(@NotNull List<MotionPoint> points) {
        this.points = points;
    }

    public static Motion of(JsonObject json) {
        int v = json.get("version").getAsInt();
        if (v != 1) throw new IllegalStateException("Unsupported motion version");
        var ja = json.getAsJsonArray("points");
        ImmutableList.Builder<MotionPoint> builder = ImmutableList.builder();
        ja.forEach(n -> builder.add(MotionPoint.of(n.getAsJsonObject())));
        return new Motion(builder.build());
    }

    @NotNull
    public Pair<MotionPoint, MotionPoint> getInterval(float par) {
        par = Mth.clamp(par, 0, 1);
        int point = Mth.clamp((int) Math.floor((float) (points.size() - 1) * par), 0, points.size() - 1);
        int npoint = Mth.clamp(point + 1, 0, points.size() - 1);
        return Pair.of(points.get(point), points.get(npoint));
    }

    public void pose(@NotNull PoseStack stack, float par) {
        var iv = getInterval(par);
        var st = iv.getLeft();
        var en = iv.getRight();
        Vector3f pos;
        Vector3f rot;
        if (st == en) {
            pos = st.position();
            rot = st.rotation();
        } else {
            float op = par % (1f / (points.size() - 1));
            op *= points.size() - 1;
            pos = new Vector3f(Mth.lerp(op, st.position().x(), en.position().x()), Mth.lerp(op, st.position().y(), en.position().y()), Mth.lerp(op, st.position().z(), en.position().z()));
            rot = new Vector3f(Mth.lerp(op, st.rotation().x(), en.rotation().x()), Mth.lerp(op, st.rotation().y(), en.rotation().y()), Mth.lerp(op, st.rotation().z(), en.rotation().z()));
        }
        stack.translate(pos.x(), pos.y(), pos.z());
        OERenderUtil.poseRotateAll(stack, rot.x(), rot.y(), rot.z());
    }
}
