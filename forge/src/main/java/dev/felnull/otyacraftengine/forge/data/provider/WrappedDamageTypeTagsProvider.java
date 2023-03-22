package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.DamageTypeTagsProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class WrappedDamageTypeTagsProvider extends DamageTypeTagsProvider {
    private final DamageTypeTagsProviderWrapper tagProviderWrapper;

    public WrappedDamageTypeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, DamageTypeTagsProviderWrapper tagProviderWrapper) {
        super(arg, completableFuture);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tagProviderWrapper.generateTag(new DamageTypeTagsProviderAccessImpl());
    }

    private class DamageTypeTagsProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<DamageType, TagProviderWrapper.TagAppenderWrapper<DamageType>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<DamageType> tag(TagKey<DamageType> tagKey) {
            return new WrappedTagsProvider.TagAppenderWrapperImpl<>(WrappedDamageTypeTagsProvider.this.tag(tagKey));
        }
    }
}
