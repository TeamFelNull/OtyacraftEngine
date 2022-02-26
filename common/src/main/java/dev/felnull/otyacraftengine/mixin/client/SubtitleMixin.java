package dev.felnull.otyacraftengine.mixin.client;


import dev.felnull.otyacraftengine.client.gui.subtitle.ICustomTimeSubtitle;
import dev.felnull.otyacraftengine.client.gui.subtitle.IDynamicSubtitle;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(SubtitleOverlay.Subtitle.class)
public class SubtitleMixin implements IDynamicSubtitle, ICustomTimeSubtitle {
    private Supplier<Vec3> dynamicLocation;
    private long customTime;

    @Override
    public void setDynamicLocation(Supplier<Vec3> location) {
        this.dynamicLocation = location;
    }

    @Override
    public Supplier<Vec3> getDynamicLocation() {
        return dynamicLocation;
    }

    @Override
    public void setCustomTime(long time) {
        this.customTime = 3000L - time;
    }

    @Override
    public long getCustomTime() {
        return customTime;
    }

    @Inject(method = "getLocation", at = @At("RETURN"), cancellable = true)
    private void getLocation(CallbackInfoReturnable<Vec3> cir) {
        if (dynamicLocation != null) {
            var loc = dynamicLocation.get();
            if (loc != null)
                cir.setReturnValue(loc);
        }
    }

    @Inject(method = "getTime", at = @At("RETURN"), cancellable = true)
    private void getTime(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(cir.getReturnValue() - customTime);
    }
}


