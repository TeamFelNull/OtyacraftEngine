package red.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("TAIL"))
    private void onPress(long l, int i, int j, int k, CallbackInfo ci) {
        OEClientHooks.fireMouseInput(i, k, j);
    }

    @Inject(method = "onPress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;overlay:Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0), cancellable = true)
    private void onPress2(long l, int i, int j, int k, CallbackInfo ci) {
        if (OEClientHooks.onRawMouseClicked(i, k, j)) {
            ci.cancel();
        }
    }
}
