package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

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

        @Override
        public void builtinEntity(Item item) {
            var name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
            getBuilder(name.toString()).parent(new ModelFile.UncheckedModelFile("builtin/entity"));
        }
    }
}
