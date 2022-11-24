package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import dev.felnull.otyacraftengine.tag.ManualTagHolder;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public abstract class TagProviderWrapper<T, A extends TagProviderWrapper.TagProviderAccess<T>> extends DataProviderWrapper<TagsProvider<T>> {
    public TagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    public abstract void generateTag(A providerAccess);

    public static interface TagProviderAccess<T> {
        TagAppenderWrapper<T> tag(TagKey<T> tagKey);
    }

    @Override
    public DataGeneratorType getGeneratorType() {
        return DataGeneratorType.SERVER;
    }

    public static interface TagAppenderWrapper<T> {
        TagAppenderWrapper<T> add(T object);

        TagAppenderWrapper<T> add(ResourceKey<T>... resourceKeys);

        TagAppenderWrapper<T> addOptional(ResourceLocation resourceLocation);

        TagAppenderWrapper<T> addTag(TagKey<T> tagKey);

        default TagAppenderWrapper<T> addTag(ManualTagHolder<T> tagHolder) {
            var tp = getTagProvider();
            if (tp != null)
                tagHolder.registering(tp);
            return addTag(tagHolder.getKey());
        }

        default TagAppenderWrapper<T> addVanillaTag(TagKey<T> tagKey) {
            return addTag(tagKey);
        }

        TagAppenderWrapper<T> addOptionalTag(ResourceLocation resourceLocation);

        TagAppenderWrapper<T> add(T... objects);

        default TagAppenderWrapper<T> addTag(TagKey<T>... tagKeys) {
            TagAppenderWrapper<T> appenderWrapper = this;

            for (TagKey<T> tagKey : tagKeys)
                appenderWrapper = appenderWrapper.addTag(tagKey);

            return appenderWrapper;
        }

        default TagAppenderWrapper<T> addTag(ManualTagHolder<T>... tagHolders) {
            TagAppenderWrapper<T> appenderWrapper = this;

            for (ManualTagHolder<T> tagHolder : tagHolders)
                appenderWrapper = appenderWrapper.addTag(tagHolder);

            return appenderWrapper;
        }

        default TagProviderAccess<T> getTagProvider() {
            return null;
        }
    }
}
