package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class WrappedTagsProvider<T> extends TagsProvider<T> {
    private final TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T>> tagProviderWrapper;

    protected WrappedTagsProvider(DataGenerator arg, Registry<T> arg2, String modId, @Nullable ExistingFileHelper existingFileHelper, TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T>> tagProviderWrapper) {
        super(arg, arg2, modId, existingFileHelper);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags() {
        this.tagProviderWrapper.generateTag(new TagProviderAccessImpl());
    }

    private class TagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<T> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> tag(TagKey<T> tagKey) {
            return new TagAppenderWrapperImpl<>(this, WrappedTagsProvider.this.tag(tagKey));
        }
    }

    protected static class TagAppenderWrapperImpl<A> implements TagProviderWrapper.TagAppenderWrapper<A> {
        private final TagProviderWrapper.TagProviderAccess<A> provider;
        private final TagAppender<A> appender;

        protected TagAppenderWrapperImpl(TagProviderWrapper.TagProviderAccess<A> provider, TagAppender<A> appender) {
            this.provider = provider;
            this.appender = appender;
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> add(A object) {
            return new TagAppenderWrapperImpl<>(provider, appender.add(object));
        }

        @SafeVarargs
        @Override
        public final TagProviderWrapper.TagAppenderWrapper<A> add(ResourceKey<A>... resourceKeys) {
            return new TagAppenderWrapperImpl<>(provider, appender.add(resourceKeys));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addOptional(ResourceLocation resourceLocation) {
            return new TagAppenderWrapperImpl<>(provider, appender.addOptional(resourceLocation));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addTagHolder(TagKey<A> tagKey) {
            return new TagAppenderWrapperImpl<>(provider, appender.addTag(tagKey));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<A> addOptionalTag(ResourceLocation resourceLocation) {
            return new TagAppenderWrapperImpl<>(provider, appender.addOptionalTag(resourceLocation));
        }

        @SafeVarargs
        @Override
        public final TagProviderWrapper.TagAppenderWrapper<A> add(A... objects) {
            return new TagAppenderWrapperImpl<>(provider, appender.add(objects));
        }

        @Override
        public TagProviderWrapper.TagProviderAccess<A> getTagProvider() {
            return provider;
        }
    }
}
