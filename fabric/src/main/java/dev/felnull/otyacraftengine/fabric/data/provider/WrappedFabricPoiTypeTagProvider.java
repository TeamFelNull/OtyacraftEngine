package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.PoiTypeTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class WrappedFabricPoiTypeTagProvider extends FabricTagProvider<PoiType> {
    private final PoiTypeTagProviderWrapper tagProviderWrapper;

    public WrappedFabricPoiTypeTagProvider(FabricDataGenerator dataGenerator, PoiTypeTagProviderWrapper tagProviderWrapper) {
        super(dataGenerator, Registry.POINT_OF_INTEREST_TYPE);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void generateTags() {
        tagProviderWrapper.generateTag(new PoiTypeTagProviderAccessImpl());
    }

    private class PoiTypeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<PoiType> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<PoiType> tag(TagKey<PoiType> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricPoiTypeTagProvider.this.tag(tagKey));
        }
    }
}
