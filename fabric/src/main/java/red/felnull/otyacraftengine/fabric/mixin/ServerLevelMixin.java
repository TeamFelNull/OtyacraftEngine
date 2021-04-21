package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.OEEventHooks;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;save(Z)V"))
    private void save(ProgressListener progressListener, boolean bl, boolean bl2, CallbackInfo ci) {
        OEEventHooks.onWorldSave((ServerLevel) (Object) this);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tick(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        OEEventHooks.onPreWorldTick((ServerLevel) (Object) this);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tick2(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        OEEventHooks.onPostWorldTick((ServerLevel) (Object) this);
    }
}
