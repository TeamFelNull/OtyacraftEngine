package dev.felnull.otyacraftengine.fabric.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.block.IIkisugiVoxelShape;
import dev.felnull.otyacraftengine.client.debug.HighlightVoxelShapeType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    private ClientLevel level;

    @Shadow
    private boolean needsUpdate;

    @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
    private void renderHitOutline(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        var shapeType = OtyacraftEngine.CONFIG.highlightVoxelShape;
        if (shapeType != HighlightVoxelShapeType.OFF) {
            var shape = shapeType.getGetter();
            if (shape != null)
                LevelRenderer.renderVoxelShape(poseStack, vertexConsumer, shape.getShape(blockState, level, blockPos, CollisionContext.of(entity)), (double) blockPos.getX() - d, (double) blockPos.getY() - e, (double) blockPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F);
            ci.cancel();
        }
    }

    @Inject(method = "renderShape", at = @At("HEAD"), cancellable = true)
    private static void renderShape(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double x, double y, double z, float r, float g, float b, float a, CallbackInfo ci) {
        if (!OtyacraftEngine.CONFIG.fastVoxelShapeRender) return;
        if (((IIkisugiVoxelShape) voxelShape).getEdges() == null) return;
        ci.cancel();
        var pose = poseStack.last();
        for (IIkisugiVoxelShape.Edge edge : ((IIkisugiVoxelShape) voxelShape).getEdges()) {
            renderShapeEdge(pose, vertexConsumer, edge.stX(), edge.stY(), edge.stZ(), edge.enX(), edge.enY(), edge.enZ(), x, y, z, r, g, b, a);
        }
    }

    private static void renderShapeEdge(PoseStack.Pose pose, VertexConsumer vertexConsumer, double sx, double sy, double sz, double ex, double ey, double ez, double x, double y, double z, float r, float g, float b, float a) {
        float q = (float) (ex - sx);
        float r2 = (float) (ey - sy);
        float s = (float) (ez - sz);
        float t = Mth.sqrt(q * q + r2 * r2 + s * s);
        q /= t;
        r2 /= t;
        s /= t;
        vertexConsumer.vertex(pose.pose(), (float) (sx + x), (float) (sy + y), (float) (sz + z)).color(r, g, b, a).normal(pose.normal(), q, r2, s).endVertex();
        vertexConsumer.vertex(pose.pose(), (float) (ex + x), (float) (ey + y), (float) (ez + z)).color(r, g, b, a).normal(pose.normal(), q, r2, s).endVertex();
    }
}
