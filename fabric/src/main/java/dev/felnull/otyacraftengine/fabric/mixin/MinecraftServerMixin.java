package dev.felnull.otyacraftengine.fabric.mixin;

import dev.felnull.otyacraftengine.event.MoreLifecycleEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "saveAllChunks", at = @At("RETURN"), cancellable = true)
    private void saveAllChunks(boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
        MoreLifecycleEvent.SERVER_SAVING.invoker().stateChanged((MinecraftServer) (Object) this);
    }
}
