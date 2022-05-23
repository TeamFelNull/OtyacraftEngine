package dev.felnull.otyacraftengine.fabric.client;

import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPointManager;
import dev.felnull.otyacraftengine.client.entrypoint.ParticleRegister;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

public class ClientParticleInit {
    public static void init() {
        OEClientEntryPointManager.getInstance().call().onParticleRegistry(new ParticleRegister() {
            @Override
            public <T extends ParticleOptions> void registerParticle(ParticleType<T> type, ParticleProvider<T> provider) {
                ParticleFactoryRegistry.getInstance().register(type, provider);
            }

            @Override
            public <T extends ParticleOptions> void registerParticle(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> provider) {
                ParticleFactoryRegistry.getInstance().register(type, provider::apply);
            }
        });
    }
}
