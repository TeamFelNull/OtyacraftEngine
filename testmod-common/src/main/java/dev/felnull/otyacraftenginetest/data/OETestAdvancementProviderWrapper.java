package dev.felnull.otyacraftenginetest.data;

import com.google.common.collect.ImmutableList;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.AdvancementProviderWrapper;
import net.minecraft.data.PackOutput;

public class OETestAdvancementProviderWrapper extends AdvancementProviderWrapper {
    public OETestAdvancementProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess, ImmutableList.of(new OETestAdvancementSubProviderWrapper(crossDataGeneratorAccess)));
    }
}
