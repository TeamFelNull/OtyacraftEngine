package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class WrappedItemModelProvider extends ItemModelProvider {
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(generator, modid, existingFileHelper);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    protected void registerModels() {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl());
    }

    private class ItemModelProviderAccessImpl implements ItemModelProviderWrapper.ItemModelProviderAccess {
        @Override
        public void basicFlatItem(Item item) {
            basicItem(item);
        }
    }
}
