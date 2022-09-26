package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.AdvancementProviderWrapper;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class WrappedAdvancementProvider extends AdvancementProvider {
    private final AdvancementProviderWrapper advancementProviderWrapper;

    public WrappedAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn, AdvancementProviderWrapper advancementProviderWrapper) {
        super(generatorIn, fileHelperIn);
        this.advancementProviderWrapper = advancementProviderWrapper;
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        this.advancementProviderWrapper.generateAdvancement(consumer);
    }
}
