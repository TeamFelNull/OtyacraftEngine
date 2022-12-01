package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftengine.fabric.mixin.data.ItemModelGeneratorsAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class WrappedFabricItemModelProvider extends FabricModelProvider {
    public static final ModelTemplate BUILTIN_ENTITY = new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "builtin/entity")), Optional.empty());
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

        @Override
        public void builtinEntity(Item item) {
            var access = (ItemModelGeneratorsAccessor) itemModelGenerators;
            BUILTIN_ENTITY.create(ModelLocationUtils.getModelLocation(item), new TextureMapping(), access.getOutput());
        }
    }
}
