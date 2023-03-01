package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.model.ItemModelProviderAccess;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

public abstract class ItemModelProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider itemModelProvider;

    public ItemModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.itemModelProvider = crossDataGeneratorAccess.createItemModelProvider(packOutput, this);
    }

    @Override
    public DataProvider getProvider() {
        return itemModelProvider;
    }

    public abstract void generateItemModels(ItemModelProviderAccess providerAccess);
}
