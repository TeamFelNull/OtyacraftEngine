package dev.felnull.otyacraftengine.client.entrypoint;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

/**
 * ArchitecturyApiのParticleProviderRegistryがForgeで動かないための代替措置
 */
public interface ParticleRegister {
    <T extends ParticleOptions> void registerParticle(ParticleType<T> type, ParticleProvider<T> provider);

    <T extends ParticleOptions> void registerParticle(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> provider);
}
