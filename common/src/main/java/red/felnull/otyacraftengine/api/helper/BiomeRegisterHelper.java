package red.felnull.otyacraftengine.api.helper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import red.felnull.otyacraftengine.util.IKSGBiomeUtil;

import java.util.HashMap;
import java.util.Map;

public class BiomeRegisterHelper {
    public final Map<ResourceLocation, Biome> REGISTRATION_BIOMES = new HashMap<>();
    public final Map<ResourceLocation, OverworldContinentalEntry> REGISTRATION_OVERWORLDCONTINENTAL = new HashMap<>();

    public void registrationBiome(ResourceLocation location, Biome biome) {
        REGISTRATION_BIOMES.put(location, biome);
    }

    public Map<ResourceLocation, Biome> getRegistrationBiomes() {
        return REGISTRATION_BIOMES;
    }

    public void registrationAddOverworldContinentalBiome(ResourceLocation location, IKSGBiomeUtil.BiomeType type, double weight) {
        REGISTRATION_OVERWORLDCONTINENTAL.put(location, new OverworldContinentalEntry(type, weight));
    }

    public Map<ResourceLocation, OverworldContinentalEntry> getAddOverworldContinentalBiome() {
        return REGISTRATION_OVERWORLDCONTINENTAL;
    }

    public static class OverworldContinentalEntry {
        public final IKSGBiomeUtil.BiomeType type;
        public final double weight;

        private OverworldContinentalEntry(IKSGBiomeUtil.BiomeType type, double weight) {
            this.type = type;
            this.weight = weight;
        }
    }
}
