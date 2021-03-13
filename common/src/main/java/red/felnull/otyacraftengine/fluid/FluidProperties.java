package red.felnull.otyacraftengine.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;

public class FluidProperties {
    private int color = 0xFFFFFFFF;
    private boolean coloring = true;
    private SoundEvent customEmptySound;
    private float customEmptySoundPitch = 1;
    private SoundEvent pickupSound = SoundEvents.BUCKET_FILL;
    private boolean autoBucketName = true;
    private ResourceLocation stillTexture = new ResourceLocation("block/water_still");
    private ResourceLocation flowingTexture = new ResourceLocation("block/water_flow");
    private ResourceLocation overlayTexture = new ResourceLocation("block/water_overlay");
    private int lightLevel = 0;
    private int density = 1000;
    private int temperature = 300;
    private int viscosity = 1000;
    private boolean isGaseous;
    private Rarity rarity = Rarity.COMMON;
    private int tickDelay = 5;
    private int levelDecreasePerBlock = 1;
    private float explosionResistance = 1;
    private int slopeFindDistance = 4;
    private boolean canMultiply;

    public FluidProperties multiply() {
        this.canMultiply = true;
        return this;
    }

    public FluidProperties slopeFindDistance(int slopeFindDistance) {
        this.slopeFindDistance = slopeFindDistance;
        return this;
    }

    public FluidProperties levelDecreasePerBlock(int levelDecreasePerBlock) {
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        return this;
    }

    public FluidProperties explosionResistance(int explosionResistance) {
        this.explosionResistance = explosionResistance;
        return this;
    }

    public FluidProperties tickDelay(int tickDelay) {
        this.tickDelay = tickDelay;
        return this;
    }

    public FluidProperties rarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public FluidProperties gas() {
        this.isGaseous = true;
        return this;
    }

    public FluidProperties viscosity(int viscosity) {
        this.viscosity = viscosity;
        return this;
    }

    public FluidProperties temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public FluidProperties density(int density) {
        this.density = density;
        return this;
    }

    public FluidProperties lightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
        return this;
    }

    public FluidProperties overlayTexture(ResourceLocation overlayTexture) {
        this.overlayTexture = overlayTexture;
        return this;
    }

    public FluidProperties flowingTexture(ResourceLocation flowingTexture) {
        this.flowingTexture = flowingTexture;
        return this;
    }

    public FluidProperties stillTexture(ResourceLocation stillTexture) {
        this.stillTexture = stillTexture;
        return this;
    }

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

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public int getDensity() {
        return density;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getViscosity() {
        return viscosity;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public boolean isGaseous() {
        return isGaseous;
    }

    public int getTickDelay() {
        return tickDelay;
    }

    public float getExplosionResistance() {
        return explosionResistance;
    }

    public int getLevelDecreasePerBlock() {
        return levelDecreasePerBlock;
    }

    public int getSlopeFindDistance() {
        return slopeFindDistance;
    }

    public boolean isCanMultiply() {
        return canMultiply;
    }
}
