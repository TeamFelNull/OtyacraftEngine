package dev.felnull.otyacraftengine.tag;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.tags.TagKey;

import java.util.function.Consumer;

public interface ManualTagHolder<T> {
    static <T> ManualTagHolder<T> of(TagKey<T> tagKey, Consumer<TagProviderWrapper.TagAppenderWrapper<T>> tagRegister) {
        return new ManualTagHolderImpl<>(tagKey, tagRegister);
    }

    static <T> ManualTagHolder<T> of(TagKey<T> tagKey) {
        return new ManualTagHolderImpl<>(tagKey);
    }

    TagKey<T> getKey();

    void registering(TagProviderWrapper.TagProviderAccess<T> tagProviderAccess);
}
