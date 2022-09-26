package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataProvider;

import java.util.function.Consumer;

public abstract class AdvancementProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider advancementProvider;

    public AdvancementProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.advancementProvider = crossDataGeneratorAccess.createAdvancementProvider(this);
    }

    @Override
    public DataProvider getProvider() {
        return advancementProvider;
    }

    @Override
    public DataGeneratorType getGeneratorType() {
        return DataGeneratorType.SERVER;
    }

    public abstract void generateAdvancement(Consumer<Advancement> consumer);
}
