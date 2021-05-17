package red.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.Util;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import red.felnull.otyacraftengine.client.gui.components.IIkisugiSubtitle;

import java.util.UUID;
import java.util.function.Supplier;

@Mixin(SubtitleOverlay.Subtitle.class)
public abstract class SubtitleMixin implements IIkisugiSubtitle {
    private Component itext;
    private UUID id;
    private Supplier<Vec3> dynamicLocation;

    @Shadow
    public abstract void refresh(Vec3 vec3);

    @Shadow
    private long time;

    @Override
    public void overrideRefresh(Component component, Supplier<Vec3> vec3, long time) {
        this.dynamicLocation = vec3;
        this.itext = component;
        refresh(vec3.get());
        setTime(time);
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public void setID(UUID id) {
        this.id = id;
    }

    @Inject(method = "getText", at = @At("RETURN"), cancellable = true)
    private void getText(CallbackInfoReturnable<Component> cir) {
        if (itext != null)
            cir.setReturnValue(itext);
    }

    @Inject(method = "getLocation", at = @At("RETURN"), cancellable = true)
    private void getLocation(CallbackInfoReturnable<Vec3> cir) {
        if (dynamicLocation != null)
            cir.setReturnValue(dynamicLocation.get());
    }

    @Override
    public void setDynamicLocation(Supplier<Vec3> location) {
        this.dynamicLocation = location;
    }

    @Override
    public void setTime(long time) {
        this.time = Util.getMillis() - 3000 + time;
    }
}
