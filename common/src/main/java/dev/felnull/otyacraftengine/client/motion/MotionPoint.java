package dev.felnull.otyacraftengine.client.motion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.Objects;

public class MotionPoint {
    private final Vector3f position;
    private final MotionRotation rotation;
    //このポイントから次のポイントまでの間の比率
    private final float ratio;

    public MotionPoint(float px, float py, float pz, float ax, float ay, float az, float ox, float oy, float oz, boolean rx, boolean ry, boolean rz) {
        this(new Vector3f(px, py, pz), new MotionRotation(ax, ay, az, ox, oy, oz, rx, ry, rz));
    }

    public MotionPoint(MotionPose pose, float ratio) {
        this(pose.position(), pose.rotation(), ratio);
    }

    public MotionPoint(Vector3f position, MotionRotation rotation) {
        this(position, rotation, 1f);
    }

    public MotionPoint(Vector3f position, MotionRotation rotation, float ratio) {
        this.position = position;
        this.rotation = rotation;
        this.ratio = ratio;
    }

    public static MotionPoint of(JsonObject jo) {
        var pos = jo.getAsJsonArray("pos");
        var jr = jo.get("rat");
        float rat = 1f;
        if (jr != null)
            rat = jr.getAsFloat();
        return new MotionPoint(new Vector3f(pos.get(0).getAsFloat(), pos.get(1).getAsFloat(), pos.get(2).getAsFloat()), MotionRotation.of(jo.getAsJsonObject("rot")), rat);
    }

    public JsonObject toJson() {
        var jo = new JsonObject();
        var p = new JsonArray();
        p.add(position.x());
        p.add(position.y());
        p.add(position.z());
        jo.add("pos", p);
        jo.add("rot", rotation.toJson());
        if (ratio != 1f)
            jo.addProperty("rat", ratio);
        return jo;
    }

    public MotionRotation getRotation() {
        return rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRatio() {
        return ratio;
    }

    public Component getText() {
        return new TextComponent(String.format("%s [%s,%s,%s] [%s,%s,%s]", ratio, position.x(), position.y(), position.z(), rotation.angle().x(), rotation.angle().y(), rotation.angle().z()));
    }

    public MotionPose getPose() {
        return new MotionPose(position, rotation);
    }

    @Override
    public String toString() {
        return "MotionPoint{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", ratio=" + ratio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotionPoint that = (MotionPoint) o;
        return Float.compare(that.ratio, ratio) == 0 && Objects.equals(position, that.position) && Objects.equals(rotation, that.rotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, rotation, ratio);
    }
}