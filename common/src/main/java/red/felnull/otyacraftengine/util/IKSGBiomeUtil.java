package red.felnull.otyacraftengine.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;

public class IKSGBiomeUtil {
    public static void addOverworldContinentalBiome(ResourceLocation biomeLocation, BiomeType type, double weight) {
        ResourceKey<Biome> biomekey = ResourceKey.create(Registry.BIOME_REGISTRY, biomeLocation);
        OEExpectPlatform.addOverworldContinentalBiome(biomekey, type, weight);
    }


    public static enum BiomeType {
        SNOWY,
        COOL,
        TEMPERATE,
        DRY;
    }
}
