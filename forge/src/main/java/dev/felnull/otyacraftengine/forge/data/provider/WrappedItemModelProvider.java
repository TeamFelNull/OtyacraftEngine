package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.model.MutableFileModelImpl;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class WrappedItemModelProvider extends ItemModelProvider {
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(output, modid, existingFileHelper);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    protected void registerModels() {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl());
    }

    private class ItemModelProviderAccessImpl implements ItemModelProviderWrapper.ItemModelProviderAccess {

        private MutableFileModel of(ItemModelBuilder itemModelBuilder) {
            return new MutableFileModelImpl(itemModelBuilder);
        }

        @Override
        public MutableFileModel basicFlatItem(Item item) {
            return of(basicItem(item));
        }

        @Override
        public MutableFileModel basicFlatItem(ResourceLocation itemLocation) {
            return of(basicItem(itemLocation));
        }

        @Override
        public MutableFileModel handheldFlatItem(Item item) {
            return handheldFlatItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
        }

        @Override
        public MutableFileModel handheldFlatItem(ResourceLocation itemLocation) {
            return of(getBuilder(itemLocation.toString())
                    .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                    .texture("layer0", new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath())));
        }

        @Override
        public MutableFileModel builtinEntity(Item item) {
            return builtinEntity(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
        }

        @Override
        public MutableFileModel builtinEntity(ResourceLocation itemLocation) {
            return of(getBuilder(itemLocation.toString())
                    .parent(new ModelFile.UncheckedModelFile("builtin/entity")));
        }
    }
}
