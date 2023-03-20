package dev.felnull.otyacraftenginetest.server.level;

import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.block.TestBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

import java.util.List;
import java.util.OptionalLong;

public class OETestDimensions {
    public static final ResourceKey<DimensionType> IKISUGI_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(OtyacraftEngineTest.MODID, "ikisugi_dimension"));
    public static final ResourceKey<LevelStem> IKISUGI_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(OtyacraftEngineTest.MODID, "ikisugi_dimension"));
    public static final ResourceKey<NoiseGeneratorSettings> IKISUGI_NOISE_GENERATOR_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(OtyacraftEngineTest.MODID, "ikisugi_level_stem_noise_generator_settings"));

    protected static final NoiseSettings IKISUGI_NOISE_SETTINGS = NoiseSettings.create(0, 128, 1, 2);

    //https://minecraftjapan.miraheze.org/wiki/%E3%83%87%E3%83%BC%E3%82%BF%E3%83%91%E3%83%83%E3%82%AF/%E3%83%AF%E3%83%BC%E3%83%AB%E3%83%89%E3%81%AE%E3%82%AB%E3%82%B9%E3%82%BF%E3%83%9E%E3%82%A4%E3%82%BA
    public static DimensionType createIkisugiDimType() {

        return new DimensionType(OptionalLong.empty(),
                true,
                false,
                false,
                true,
                3f,
                false,
                false,
                -64,
                320,
                320,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0F,
                new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0));
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(IKISUGI_DIM_TYPE, createIkisugiDimType());
    }

    private static LevelStem createIkisugiDimLevStem(BootstapContext<LevelStem> context) {
        //Holder<DimensionType> holder = dimTypes.getOrThrow(IKISUGI_DIM_TYPE);

        /*Holder<NoiseGeneratorSettings> holder1 = noiseSettings.getOrThrow(IKISUGI_NOISE_GENERATOR_SETTINGS);
        return new LevelStem(dimTypes.getOrThrow(IKISUGI_DIM_TYPE),
                new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(biomes), holder1));*/


        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        var biomes = context.lookup(Registries.BIOME);
        var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        Holder<NoiseGeneratorSettings> holder1 = noiseSettings.getOrThrow(IKISUGI_NOISE_GENERATOR_SETTINGS);

        var biomeList = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST).get(MultiNoiseBiomeSourceParameterLists.OVERWORLD).get();

        return new LevelStem(dimTypes.getOrThrow(IKISUGI_DIM_TYPE),
                new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromPreset(biomeList), holder1));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        context.register(IKISUGI_LEVEL_STEM, createIkisugiDimLevStem(context));
    }

    private static NoiseGeneratorSettings createIkisugiNoiseGeneratorSettings(BootstapContext<NoiseGeneratorSettings> context) {
        return new NoiseGeneratorSettings(
                IKISUGI_NOISE_SETTINGS,
                TestBlocks.TEST_BLOCK.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                OETestNoiseRouter.createIkisugiNoiseRouter(context),
                createIkisugiSurfaceRule(),
                List.of(),
                19,
                false,
                false,
                true,
                false);
    }


    private static SurfaceRules.RuleSource createIkisugiSurfaceRule() {
        var bedrockRule = SurfaceRules.state((Blocks.BEDROCK).defaultBlockState());//常に適用されるルール
        var condition = SurfaceRules.verticalGradient("bedrock_floor"/*何のid?DensityFunction?*/, VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5));//条件
        var ifRule = SurfaceRules.ifTrue(condition, bedrockRule);//条件付きルール
        var ifRules = SurfaceRules.sequence(ifRule, ifRule, ifRule);//条件付きルールの連続
        return SurfaceRuleData.nether();
    }

    public static void bootstrapNoiseGeneratorSettings(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(IKISUGI_NOISE_GENERATOR_SETTINGS, createIkisugiNoiseGeneratorSettings(context));
    }
}
