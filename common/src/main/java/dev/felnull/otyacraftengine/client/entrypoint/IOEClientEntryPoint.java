package dev.felnull.otyacraftengine.client.entrypoint;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public interface IOEClientEntryPoint {
    default void onModelRegistry(Consumer<ResourceLocation> addModel) {
    }
}
