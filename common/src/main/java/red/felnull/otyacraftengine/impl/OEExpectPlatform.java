package red.felnull.otyacraftengine.impl;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import me.shedaniel.architectury.fluid.FluidStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.util.IKSGBiomeUtil;

import java.util.List;
import java.util.Optional;

public class OEExpectPlatform {
    @ExpectPlatform
    public static List<IOEIntegration> getIntegrations() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TagCollection<Fluid> getFluidAllTags() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TagCollection<GameEvent> getGameEventAllTags() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addOverworldContinentalBiome(ResourceKey<Biome> biome, IKSGBiomeUtil.BiomeType type, double weight) {
        throw new AssertionError();
    }
}
