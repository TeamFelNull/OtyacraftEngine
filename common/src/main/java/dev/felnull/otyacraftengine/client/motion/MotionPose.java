package dev.felnull.otyacraftengine.client.motion;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import org.jetbrains.annotations.NotNull;

public record MotionPose(Vector3f position, MotionRotation rotation) {
    public void pose(@NotNull PoseStack stack) {
        stack.translate(position.x(), position.y(), position.z());
        rotation.pose(stack);
    }

    public MotionPose reverse() {
        var pos = position.copy();
        pos.mul(-1f, 1f, 1f);
        var ang = rotation.angle().copy();
        ang.mul(1f, -1f, -1f);
        var ori = rotation.origin().copy();
        ori.mul(-1, 1f, 1f);
        var rot = new MotionRotation(ang, ori, rotation.reset());
        return new MotionPose(pos, rot);
    }
}
