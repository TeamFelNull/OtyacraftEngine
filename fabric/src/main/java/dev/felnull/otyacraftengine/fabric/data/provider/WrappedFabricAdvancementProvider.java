package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.AdvancementProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;

import java.util.function.Consumer;

public class WrappedFabricAdvancementProvider extends FabricAdvancementProvider {
    private final AdvancementProviderWrapper advancementProviderWrapper;

    public WrappedFabricAdvancementProvider(FabricDataGenerator dataGenerator, AdvancementProviderWrapper advancementProviderWrapper) {
        super(dataGenerator);
        this.advancementProviderWrapper = advancementProviderWrapper;
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        this.advancementProviderWrapper.generateAdvancement(consumer);
    }
}
