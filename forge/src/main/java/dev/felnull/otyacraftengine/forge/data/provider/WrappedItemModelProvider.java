package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.model.ItemModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.model.ItemMutableFileModelImpl;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

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

    private class ItemModelProviderAccessImpl implements ItemModelProviderAccess {

        private MutableFileModel of(ItemModelBuilder itemModelBuilder) {
            return new ItemMutableFileModelImpl(itemModelBuilder);
        }

        @Override
        public @NotNull MutableFileModel basicFlatItem(@NotNull Item item) {
            return of(basicItem(item));
        }

        @Override
        public @NotNull MutableFileModel basicFlatItem(@NotNull ResourceLocation itemLocation) {
            return of(basicItem(itemLocation));
        }

        @Override
        public @NotNull MutableFileModel handheldFlatItem(@NotNull Item item) {
            return handheldFlatItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
        }

        @Override
        public @NotNull MutableFileModel handheldFlatItem(@NotNull ResourceLocation itemLocation) {
            return of(getBuilder(itemLocation.toString())
                    .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                    .texture("layer0", new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath())));
        }

        @Override
        public @NotNull MutableFileModel builtinEntityItem(@NotNull Item item) {
            return builtinEntityItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
        }

        @Override
        public @NotNull MutableFileModel builtinEntityItem(@NotNull ResourceLocation itemLocation) {
            return of(getBuilder(itemLocation.toString())
                    .parent(new ModelFile.UncheckedModelFile("builtin/entity")));
        }

        @Override
        public @NotNull MutableFileModel parentedItem(@NotNull Item item, @NotNull ResourceLocation parentLocation) {
            return parentedItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), parentLocation);
        }

        @Override
        public @NotNull MutableFileModel parentedItem(@NotNull ResourceLocation itemLocation, @NotNull ResourceLocation parentLocation) {
            return of(getBuilder(itemLocation.toString())
                    .parent(new ModelFile.UncheckedModelFile(parentLocation)));
        }
    }
}
