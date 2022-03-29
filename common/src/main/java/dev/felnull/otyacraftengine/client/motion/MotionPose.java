package dev.felnull.otyacraftengine.client.motion;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

public record MotionPose(Vector3f position, MotionRotation rotation) {
    public void pose(PoseStack stack) {
        stack.translate(position.x(), position.y(), position.z());
        rotation.pose(stack);
    }
}
