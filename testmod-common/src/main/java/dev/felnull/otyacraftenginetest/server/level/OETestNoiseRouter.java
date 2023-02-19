package dev.felnull.otyacraftenginetest.server.level;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class OETestNoiseRouter {
    protected static NoiseRouter createIkisugiNoiseRouter(BootstapContext<NoiseGeneratorSettings> context) {
        var densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        var noiseParameters = context.lookup(Registries.NOISE);
        DensityFunction densityfunction = getFunction(densityFunctions, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(densityFunctions, SHIFT_Z);
        DensityFunction densityfunction2 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, noiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityfunction3 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, noiseParameters.getOrThrow(Noises.VEGETATION));
        //DensityFunction densityfunction4 = postProcess(slideNetherLike(p_256256_, 0, 128));
        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                densityfunction2,
                densityfunction3,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                getFunction(densityFunctions, SHIFT_X),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero());


    }

    private static final ResourceKey<DensityFunction> SHIFT_X = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x"));
    private static final ResourceKey<DensityFunction> SHIFT_Z = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z"));

    private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> p_255724_, HolderGetter<NormalNoise.NoiseParameters> p_255986_, DensityFunction p_256378_) {
        DensityFunction densityfunction = getFunction(p_255724_, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(p_255724_, SHIFT_Z);
        DensityFunction densityfunction2 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, p_255986_.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityfunction3 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, p_255986_.getOrThrow(Noises.VEGETATION));
        DensityFunction densityfunction4 = postProcess(p_256378_);
        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), densityfunction2, densityfunction3, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), densityfunction4, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }

    private static DensityFunction postProcess(DensityFunction p_224493_) {
        DensityFunction densityfunction = DensityFunctions.blendDensity(p_224493_);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityfunction), DensityFunctions.constant(0.64D)).squeeze();
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> p_256312_, ResourceKey<DensityFunction> p_256077_) {
        return new DensityFunctions.HolderHolder(p_256312_.getOrThrow(p_256077_));
    }
}
