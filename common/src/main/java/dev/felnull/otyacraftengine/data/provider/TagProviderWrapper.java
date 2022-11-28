package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import dev.felnull.otyacraftengine.tag.ManualTagHolder;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.List;

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

        TagAppenderWrapper<T> addTagHolder(TagKey<T> tagKey);

        default TagAppenderWrapper<T> addTagHolder(ManualTagHolder<T> tagHolder) {
            var tp = getTagProvider();
            if (tp != null)
                tagHolder.registering(tp);
            return addTagHolder(tagHolder.getKey());
        }

        default TagAppenderWrapper<T> addVanillaTag(TagKey<T> tagKey) {
            return addTagHolder(tagKey);
        }

        TagAppenderWrapper<T> addOptionalTag(ResourceLocation resourceLocation);

        TagAppenderWrapper<T> add(T... objects);

        default TagAppenderWrapper<T> addTags(List<TagKey<T>> tagKeys) {
            TagAppenderWrapper<T> appenderWrapper = this;

            for (TagKey<T> tagKey : tagKeys)
                appenderWrapper = appenderWrapper.addTagHolder(tagKey);

            return appenderWrapper;
        }

        default TagAppenderWrapper<T> addTagHolders(List<ManualTagHolder<T>> tagHolders) {
            TagAppenderWrapper<T> appenderWrapper = this;

            for (ManualTagHolder<T> tagHolder : tagHolders)
                appenderWrapper = appenderWrapper.addTagHolder(tagHolder);

            return appenderWrapper;
        }

        default TagAppenderWrapper<T> addOptionalTag(ResourceLocation... resourceLocations) {
            TagAppenderWrapper<T> appenderWrapper = this;

            for (ResourceLocation resourceLocation : resourceLocations)
                appenderWrapper = appenderWrapper.addOptionalTag(resourceLocation);

            return appenderWrapper;
        }

        default TagProviderAccess<T> getTagProvider() {
            return null;
        }
    }
}
