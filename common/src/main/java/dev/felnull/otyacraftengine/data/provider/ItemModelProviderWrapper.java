package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

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

    public static interface ItemModelProviderAccess {
        void basicFlatItem(Item item);

        void builtinEntity(Item item);
    }
}
