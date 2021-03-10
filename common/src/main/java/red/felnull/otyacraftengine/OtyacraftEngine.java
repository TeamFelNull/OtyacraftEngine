package red.felnull.otyacraftengine;

import net.minecraft.world.level.biome.Biome;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.block.TestBlock;
import red.felnull.otyacraftengine.blockentity.TestBlockEntity;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;
import red.felnull.otyacraftengine.item.TestItem;
import red.felnull.otyacraftengine.packet.OEPackets;

public class OtyacraftEngine {
    public static final String MODID = "otyacraftengine";
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

    public static Biome testBiome;

    public static void init() {
        OERegistries.init(api);
        OEPackets.init();

        if (api.isTestMode()) {
            test();
        }
    }

    public static void apiInit() {
        boolean testmode = true;
        OtyacraftEngineAPI api = new OtyacraftEngineAPI(OEExpectPlatform.getIntegrations(), testmode);
        api.setDebugMode(false);
    }

    /**
     * OEのテスト用アイテムやブロックの追加等
     * リリースする際は必ず呼ばないようにすること
     *
     * @author MORIMORI0317
     * @since 2.0
     */
    private static void test() {
        TestBlock.init();
        TestItem.init();
        TestBlockEntity.init();
/*
        ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> OBSIDIAN_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
                .configured(new SurfaceBuilderBaseConfiguration(
                        Blocks.OBSIDIAN.defaultBlockState(),
                        Blocks.DIRT.defaultBlockState(),
                        Blocks.GRAVEL.defaultBlockState()));

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnSettings);
        BiomeDefaultFeatures.monsters(spawnSettings, 95, 5, 100);

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.surfaceBuilder(OBSIDIAN_SURFACE_BUILDER);
        BiomeDefaultFeatures.addDefaultOverworldLandStructures(generationSettings);
        BiomeDefaultFeatures.addDefaultCarvers(generationSettings);
        BiomeDefaultFeatures.addDefaultLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);

        testBiome = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.4F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0x3f76e4).waterFogColor(0x050533).fogColor(0xc0d8ff).skyColor(0x77adff).build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();

        IKSGBiomeUtil.registerConfiguredSurfaceBuilder(new ResourceLocation(OtyacraftEngine.MODID, "test"), OBSIDIAN_SURFACE_BUILDER);

        // ResourceKey<Biome> OBSILAND_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(OtyacraftEngine.MODID, "test"));

        Registry.register(BuiltinRegistries.BIOME, new ResourceLocation(OtyacraftEngine.MODID, "test"), testBiome);

        IKSGBiomeUtil.addOverworldContinentalBiome(new ResourceLocation(OtyacraftEngine.MODID, "test"), IKSGBiomeUtil.BiomeType.TEMPERATE, 20);
   */
    }
}
