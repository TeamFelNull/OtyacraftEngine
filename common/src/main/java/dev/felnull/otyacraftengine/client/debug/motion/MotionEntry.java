package dev.felnull.otyacraftengine.client.debug.motion;

import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public record MotionEntry(Vector3f position, Vector3f rotation, Vector3f scale) {

    public Component getText() {
        return new TextComponent(String.format("[%s,%s,%s] [%s,%s,%s]", position.x(), position.y(), position.z(), rotation.x(), rotation.y(), rotation.z()));
    }
}