package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class WrappedFabricTagProvider<T> extends FabricTagProvider<T> {
    private final TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T>> tagProviderWrapper;

    public WrappedFabricTagProvider(FabricDataGenerator dataGenerator, Registry<T> registry, TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T>> tagProviderWrapper) {
        super(dataGenerator, registry);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void generateTags() {
        this.tagProviderWrapper.generateTag(new TagProviderAccessImpl());
    }

    private class TagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<T> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> tag(TagKey<T> tagKey) {
            return new TagAppenderWrapperImpl<>(WrappedFabricTagProvider.this.tag(tagKey));
        }
    }

    protected static class TagAppenderWrapperImpl<A> implements TagProviderWrapper.TagAppenderWrapper<A> {
        private final TagAppender<A> appender;

        protected TagAppenderWrapperImpl(TagAppender<A> appender) {
            this.appender = appender;
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> add(A object) {
            return new TagAppenderWrapperImpl<>(appender.add(object));
        }

        @SafeVarargs
        @Override
        public final TagProviderWrapper.TagAppenderWrapper<A> add(ResourceKey<A>... resourceKeys) {
            return new TagAppenderWrapperImpl<>(appender.add(resourceKeys));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addOptional(ResourceLocation resourceLocation) {
            return new TagAppenderWrapperImpl<>(appender.addOptional(resourceLocation));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addTag(TagKey<A> tagKey) {
            return new TagAppenderWrapperImpl<>(appender.addTag(tagKey));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addVanillaTag(TagKey<A> tagKey) {
            return addOptionalTag(tagKey.location());
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addOptionalTag(ResourceLocation resourceLocation) {
            return new TagAppenderWrapperImpl<>(appender.addOptionalTag(resourceLocation));
        }

        @SafeVarargs
        @Override
        public final TagProviderWrapper.TagAppenderWrapper<A> add(A... objects) {
            return new TagAppenderWrapperImpl<>(appender.add(objects));
        }
    }
}
