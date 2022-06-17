package dev.felnull.otyacraftengine.fabric.mixin.client;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public ClientLevel level;

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void setLevel(ClientLevel clientLevel, CallbackInfo ci) {
        if (level != null)
            OEClientEventHooks.onLevelUnload(level);
    }

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateScreenAndTick(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 0, shift = At.Shift.AFTER))
    private void clearLevel(Screen screen, CallbackInfo ci) {
        if (this.level != null)
            OEClientEventHooks.onLevelUnload(level);
    }
}
