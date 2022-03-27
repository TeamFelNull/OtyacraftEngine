package dev.felnull.otyacraftengine.client.motion;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public record MotionPoint(Vector3f position, Vector3f rotation) {
    public static MotionPoint of(JsonObject jo) {
        var pos = jo.getAsJsonArray("pos");
        var rot = jo.getAsJsonArray("rot");
        return new MotionPoint(new Vector3f(pos.get(0).getAsFloat(), pos.get(1).getAsFloat(), pos.get(2).getAsFloat()), new Vector3f(rot.get(0).getAsFloat(), rot.get(1).getAsFloat(), rot.get(2).getAsFloat()));
    }

    public Component getText() {
        return new TextComponent(String.format("[%s,%s,%s] [%s,%s,%s]", position.x(), position.y(), position.z(), rotation.x(), rotation.y(), rotation.z()));
    }

    public void pose(@NotNull PoseStack stack) {
        stack.translate(position.x(), position.y(), position.z());
        OERenderUtil.poseRotateAll(stack, rotation.x(), rotation.y(), rotation.z());
    }
}