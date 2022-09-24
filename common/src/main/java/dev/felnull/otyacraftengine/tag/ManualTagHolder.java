package dev.felnull.otyacraftengine.tag;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ManualTagHolder<T> {
    static <T> ManualTagHolder<T> of(@NotNull TagKey<T> tagKey, @Nullable Consumer<TagProviderWrapper.TagAppenderWrapper<T>> tagRegister) {
        return new ManualTagHolderImpl<>(tagKey, tagRegister);
    }

    static <T> ManualTagHolder<T> of(@NotNull TagKey<T> tagKey) {
        return new ManualTagHolderImpl<>(tagKey);
    }

    @NotNull
    TagKey<T> getKey();

    void registering(@NotNull TagProviderWrapper.TagProviderAccess<T> tagProviderAccess);
}
