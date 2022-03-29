package dev.felnull.otyacraftengine.client.motion;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.util.OEMath;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Motion {
    private final List<MotionPoint> points;

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

    public JsonObject toJson() {
        var jo = new JsonObject();
        jo.addProperty("version", 1);
        var ja = new JsonArray();
        points.forEach(n -> ja.add(n.toJson()));
        jo.add("points", ja);
        return jo;
    }

    public List<MotionPoint> getPoints() {
        return points;
    }

    @NotNull
    public Pair<MotionPoint, MotionPoint> getInterval(float par) {
        par = Mth.clamp(par, 0, 1);
        int point = Mth.clamp((int) Math.floor((float) (points.size() - 1) * par), 0, points.size() - 1);
        int npoint = Mth.clamp(point + 1, 0, points.size() - 1);
        return Pair.of(points.get(point), points.get(npoint));
    }

    public void pose(@NotNull PoseStack stack, float par) {
        if (points.isEmpty()) return;
        var iv = getInterval(par);
        var st = iv.getLeft();
        var en = iv.getRight();
        Vector3f pos;
        MotionRotation rot;
        if (st == en) {
            pos = st.getPosition();
            rot = st.getRotation();
        } else {
            float op = par % (1f / (points.size() - 1));
            op *= points.size() - 1;
            pos = OEMath.leap(op, st.getPosition(), en.getPosition());
            rot = OEMath.leap(op, st.getRotation(), en.getRotation());
        }
        stack.translate(pos.x(), pos.y(), pos.z());
        rot.pose(stack);
    }
}
