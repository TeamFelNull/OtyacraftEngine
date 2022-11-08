package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.Item;

public abstract class ItemModelProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider itemModelProvider;

    public ItemModelProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.itemModelProvider = crossDataGeneratorAccess.createItemModelProvider(this);
    }

    @Override
    public DataProvider getProvider() {
        return itemModelProvider;
    }

    @Override
    public DataGeneratorType getGeneratorType() {
        return DataGeneratorType.CLIENT;
    }

    public abstract void generateItemModels(ItemModelProviderAccess providerAccess);

    public static interface ItemModelProviderAccess {
        void basicFlatItem(Item item);
    }
}
