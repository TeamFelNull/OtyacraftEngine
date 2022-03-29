package dev.felnull.otyacraftengine.client.motion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class MotionPoint {
    private final Vector3f position;
    private final MotionRotation rotation;

    public MotionPoint(Vector3f position, MotionRotation rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public static MotionPoint of(JsonObject jo) {
        var pos = jo.getAsJsonArray("pos");
        return new MotionPoint(new Vector3f(pos.get(0).getAsFloat(), pos.get(1).getAsFloat(), pos.get(2).getAsFloat()), MotionRotation.of(jo.getAsJsonObject("rot")));
    }

    public JsonObject toJson() {
        var jo = new JsonObject();
        var p = new JsonArray();
        p.add(position.x());
        p.add(position.y());
        p.add(position.z());
        jo.add("pos", p);
        jo.add("rot", rotation.toJson());
        return jo;
    }

    public MotionRotation getRotation() {
        return rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Component getText() {
        return new TextComponent(String.format("[%s,%s,%s] [%s,%s,%s]", position.x(), position.y(), position.z(), rotation.angle().x(), rotation.angle().y(), rotation.angle().z()));
    }

    public void pose(@NotNull PoseStack stack) {
        stack.translate(position.x(), position.y(), position.z());
        rotation.pose(stack);
    }
}