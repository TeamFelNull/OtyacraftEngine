package red.felnull.otyacraftengine.mixin.fabric.client;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.client.OEHooksClient;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("TAIL"))
    private void onPress(long l, int i, int j, int k, CallbackInfo ci) {
        OEHooksClient.fireMouseInput(i, k, j);
    }
/*
    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onScroll(long handle, double xOffset, double yOffset, CallbackInfo info, double amount) {
        System.out.println("test");
    }
 */
}
