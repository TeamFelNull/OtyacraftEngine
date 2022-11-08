package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class WrappedFabricItemModelProvider extends FabricModelProvider {
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedFabricItemModelProvider(FabricDataGenerator dataGenerator, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(dataGenerator);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl(itemModelGenerator));
    }

    private static class ItemModelProviderAccessImpl implements ItemModelProviderWrapper.ItemModelProviderAccess {
        private final ItemModelGenerators itemModelGenerators;

        private ItemModelProviderAccessImpl(ItemModelGenerators itemModelGenerator) {
            this.itemModelGenerators = itemModelGenerator;
        }

        @Override
        public void basicFlatItem(Item item) {
            this.itemModelGenerators.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }
    }
}
