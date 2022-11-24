package dev.felnull.otyacraftengine.tag;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ManualTagHolder<T> {
    @NotNull
    static <T> ManualTagHolder<T> of(@NotNull TagKey<T> tagKey, @Nullable Consumer<TagProviderWrapper.TagAppenderWrapper<T>> tagRegister) {
        return new ManualTagHolderImpl<>(tagKey, tagRegister);
    }

    @Deprecated
    @NotNull
    static <T> ManualTagHolder<T> of(@NotNull TagKey<T> tagKey) {
        return new ManualTagHolderImpl<>(tagKey);
    }

    @NotNull
    static <T> ManualTagHolder<T> of(@NotNull Supplier<TagKey<T>> tagKey) {
        return new ManualTagHolder<>() {
            @Override
            public @NotNull TagKey<T> getKey() {
                return tagKey.get();
            }

            @Override
            public void registering(TagProviderWrapper.@NotNull TagProviderAccess<T> tagProviderAccess) {
            }
        };
    }

    @NotNull
    TagKey<T> getKey();

    void registering(@NotNull TagProviderWrapper.TagProviderAccess<T> tagProviderAccess);
}
