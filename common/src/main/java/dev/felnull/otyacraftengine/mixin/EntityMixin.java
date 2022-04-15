package dev.felnull.otyacraftengine.mixin;

import dev.felnull.otyacraftengine.event.OECommonEventHooks;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (!OECommonEventHooks.onEntityTick((Entity) (Object) this))
            ci.cancel();
    }
}
