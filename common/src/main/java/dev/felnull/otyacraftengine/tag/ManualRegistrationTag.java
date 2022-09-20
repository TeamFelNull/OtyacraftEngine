package dev.felnull.otyacraftengine.tag;

import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.tags.TagKey;

import java.util.function.Consumer;

public record ManualRegistrationTag<T>(TagKey<T> tagKey,
                                       Consumer<TagProviderWrapper.TagAppenderWrapper<T>> tagRegister) {
}
