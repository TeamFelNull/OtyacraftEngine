package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public abstract class PoiTypeTagProviderWrapper extends TagProviderWrapper<PoiType, TagProviderWrapper.TagProviderAccess<PoiType>> {
    private final TagsProvider<PoiType> poiTypeTagsProvider;

    public PoiTypeTagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.poiTypeTagsProvider = crossDataGeneratorAccess.createPoiTypeTagProvider(this);
    }

    @Override
    public TagsProvider<PoiType> getProvider() {
        return poiTypeTagsProvider;
    }
}
