package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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
        MutableFileModel basicFlatItem(Item item);

        MutableFileModel basicFlatItem(ResourceLocation itemLocation);

        MutableFileModel handheldFlatItem(Item item);

        MutableFileModel handheldFlatItem(ResourceLocation itemLocation);

        MutableFileModel builtinEntity(Item item);

        MutableFileModel builtinEntity(ResourceLocation itemLocation);
    }
}
