package red.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.OEEventHooks;
import red.felnull.otyacraftengine.api.event.client.OEClientEventHooks;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    private boolean pause;

    @Shadow
    private float pausePartialTick;

    @Shadow
    @Final
    private Timer timer;

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void setLevel(ClientLevel clientLevel, CallbackInfo ci) {
        if (level != null)
            OEEventHooks.onWorldUnload(level);
    }

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateScreenAndTick(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void clearLevel(Screen screen, CallbackInfo ci) {
        if (level != null) {
            OEEventHooks.onWorldUnload(level);
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V", ordinal = 0))
    private void runTick(boolean bl, CallbackInfo ci) {
        OEClientEventHooks.onRenderTickStart(this.pause ? this.pausePartialTick : this.timer.partialTick);
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", ordinal = 4))
    private void runTick2(boolean bl, CallbackInfo ci) {
        OEClientEventHooks.onRenderTickEnd(this.pause ? this.pausePartialTick : this.timer.partialTick);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V", ordinal = 0))
    private void tick(CallbackInfo ci) {
        OEClientEventHooks.onPreClientTick();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick2(CallbackInfo ci) {
        OEClientEventHooks.onPostClientTick();
    }

}
