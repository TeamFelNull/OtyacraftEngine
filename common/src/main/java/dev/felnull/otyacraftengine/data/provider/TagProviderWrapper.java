package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
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

        TagAppenderWrapper<T> addOptionalTag(ResourceLocation resourceLocation);

        TagAppenderWrapper<T> add(T... objects);
    }
}
