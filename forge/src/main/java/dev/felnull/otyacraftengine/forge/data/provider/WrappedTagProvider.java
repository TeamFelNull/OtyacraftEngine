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

public class WrappedTagProvider<T> extends TagsProvider<T> {
    private final TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T>> tagProviderWrapper;

    protected WrappedTagProvider(DataGenerator arg, Registry<T> arg2, String modId, @Nullable ExistingFileHelper existingFileHelper, TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T>> tagProviderWrapper) {
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
            return new TagAppenderWrapperImpl<>(WrappedTagProvider.this.tag(tagKey));
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
