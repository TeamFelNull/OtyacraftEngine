package dev.felnull.otyacraftengine.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public class ModelPartMixin {
    @Inject(method = "translateAndRotate", at = @At("HEAD"), cancellable = true)
    private void translateAndRotate(PoseStack poseStack, CallbackInfo ci) {
        if (OERenderUtil.SKIP_TRANSANDROT_MODELPART)
            ci.cancel();
    }
}
