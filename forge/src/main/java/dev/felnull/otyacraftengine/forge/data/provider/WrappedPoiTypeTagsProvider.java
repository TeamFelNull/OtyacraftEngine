package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.PoiTypeTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class WrappedPoiTypeTagsProvider extends PoiTypeTagsProvider {
    private final PoiTypeTagProviderWrapper tagProviderWrapper;

    public WrappedPoiTypeTagsProvider(DataGenerator arg, String modId, @Nullable ExistingFileHelper existingFileHelper, PoiTypeTagProviderWrapper tagProviderWrapper) {
        super(arg, modId, existingFileHelper);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags() {
        tagProviderWrapper.generateTag(new PoiTypeTagProviderAccessImpl());
    }

    private class PoiTypeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<PoiType> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<PoiType> tag(TagKey<PoiType> tagKey) {
            return new WrappedTagsProvider.TagAppenderWrapperImpl<>(WrappedPoiTypeTagsProvider.this.tag(tagKey));
        }
    }
}
