package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.OEEventHooks;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;save(Z)V"))
    private void save(ProgressListener progressListener, boolean bl, boolean bl2, CallbackInfo ci) {
        OEEventHooks.onWorldSave((ServerLevel) (Object) this);
    }
}
