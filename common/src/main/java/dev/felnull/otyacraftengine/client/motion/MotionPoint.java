package dev.felnull.otyacraftengine.client.motion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public record MotionPoint(Vector3f position, MotionRotation rotation, float ratio) {
    public MotionPoint(Vector3f position, MotionRotation rotation) {
        this(position, rotation, 1f);
    }

    public static MotionPoint of(JsonObject jo) {
        var pos = jo.getAsJsonArray("pos");
        float rat = 1f;
        var r = jo.get("rat");
        if (r != null)
            rat = r.getAsFloat();
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

    public Component getText() {
        return new TextComponent(String.format("[%s,%s,%s] [%s,%s,%s]", position.x(), position.y(), position.z(), rotation.angle().x(), rotation.angle().y(), rotation.angle().z()));
    }

    public void pose(@NotNull PoseStack stack) {
        stack.translate(position.x(), position.y(), position.z());
        rotation.pose(stack);
    }
}