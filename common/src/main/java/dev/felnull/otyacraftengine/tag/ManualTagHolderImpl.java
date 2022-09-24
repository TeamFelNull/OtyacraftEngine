package dev.felnull.otyacraftengine.tag;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@ApiStatus.Internal
public record ManualTagHolderImpl<T>(TagKey<T> tagKey,
                                     Consumer<TagProviderWrapper.TagAppenderWrapper<T>> tagRegister) implements ManualTagHolder<T> {
    public ManualTagHolderImpl(TagKey<T> tagKey) {
        this(tagKey, null);
    }

    @Override
    public @NotNull TagKey<T> getKey() {
        return tagKey;
    }

    @Override
    public void registering(@NotNull TagProviderWrapper.TagProviderAccess<T> tagProviderAccess) {
        if (tagRegister != null)
            tagRegister.accept(tagProviderAccess.tag(getKey()));
    }
}
