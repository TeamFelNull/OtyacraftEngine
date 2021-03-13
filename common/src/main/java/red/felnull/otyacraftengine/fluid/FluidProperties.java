package red.felnull.otyacraftengine.fluid;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class FluidProperties {
    private int color = -1;
    private boolean coloring = true;
    private SoundEvent customEmptySound;
    private float customEmptySoundPitch = 1;
    private SoundEvent pickupSound = SoundEvents.BUCKET_FILL;
    private boolean autoBucketName = true;

    public FluidProperties color(int color) {
        this.color = color;
        return this;
    }

    public FluidProperties noColoring() {
        this.coloring = false;
        return this;
    }

    public FluidProperties noAutoBucketName() {
        this.autoBucketName = false;
        return this;
    }

    public FluidProperties emptySound(SoundEvent emptySound) {
        this.customEmptySound = emptySound;
        return this;
    }

    public FluidProperties emptySound(SoundEvent emptySound, float pitch) {
        this.customEmptySound = emptySound;
        this.customEmptySoundPitch = pitch;
        return this;
    }

    public FluidProperties pickupSound(SoundEvent pickupSound) {
        this.pickupSound = pickupSound;
        return this;
    }

    public int getColor() {
        return color;
    }

    public boolean isColoring() {
        return coloring;
    }

    public float getCustomEmptySoundPitch() {
        return customEmptySoundPitch;
    }

    public SoundEvent getCustomEmptySound() {
        return customEmptySound;
    }

    public boolean isCustomEmptySound() {
        return customEmptySound != null;
    }

    public SoundEvent getPickupSound() {
        return pickupSound;
    }

    public boolean isAutoBucketName() {
        return autoBucketName;
    }
}
