package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.model.ItemModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftengine.fabric.data.model.JsonModelInjector;
import dev.felnull.otyacraftengine.fabric.data.model.MutableFileModelImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public class WrappedFabricItemModelProvider extends FabricModelProvider {
    public static final ModelTemplate BUILTIN_ENTITY = new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "builtin/entity")), Optional.empty());
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedFabricItemModelProvider(FabricDataOutput output, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(output);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl(itemModelGenerator));
    }

    @Override
    public String getName() {
        return "Model Definitions (Item)";
    }

    private static class ItemModelProviderAccessImpl implements ItemModelProviderAccess {
        private final ItemModelGenerators itemModelGenerators;

        private ItemModelProviderAccessImpl(ItemModelGenerators itemModelGenerator) {
            this.itemModelGenerators = itemModelGenerator;
        }

        @Override
        public @NotNull MutableFileModel basicFlatItem(@NotNull Item item) {
            return createLayer0Model(ModelTemplates.FLAT_ITEM, item);
        }

        @Override
        public @NotNull MutableFileModel basicFlatItem(@NotNull ResourceLocation itemLocation) {
            return createLayer0Model(ModelTemplates.FLAT_ITEM, itemLocation);
        }

        @Override
        public @NotNull MutableFileModel handheldFlatItem(@NotNull Item item) {
            return createLayer0Model(ModelTemplates.FLAT_HANDHELD_ITEM, item);
        }

        @Override
        public @NotNull MutableFileModel handheldFlatItem(@NotNull ResourceLocation itemLocation) {
            return createLayer0Model(ModelTemplates.FLAT_HANDHELD_ITEM, itemLocation);
        }

        @Override
        public @NotNull MutableFileModel builtinEntityItem(@NotNull Item item) {
            return createModel(BUILTIN_ENTITY, new TextureMapping(), item);
        }

        @Override
        public @NotNull MutableFileModel builtinEntityItem(@NotNull ResourceLocation itemLocation) {
            return createModel(BUILTIN_ENTITY, new TextureMapping(), itemLocation);
        }

        @Override
        public @NotNull MutableFileModel parentedItem(@NotNull Item item, @NotNull ResourceLocation parentLocation) {
            var ji = new JsonModelInjector(this.itemModelGenerators.output);
            var loc = ModelLocationUtils.getModelLocation(item);
            ji.injectedModelOutput().accept(loc, new DelegatedModel(parentLocation));
            return new MutableFileModelImpl(loc, ji);
        }

        @Override
        public @NotNull MutableFileModel parentedItem(@NotNull ResourceLocation itemLocation, @NotNull ResourceLocation parentLocation) {
            var ji = new JsonModelInjector(this.itemModelGenerators.output);
            var loc = decorateItemModelLocation(itemLocation);
            ji.injectedModelOutput().accept(loc, new DelegatedModel(parentLocation));
            return new MutableFileModelImpl(loc, ji);
        }

        private MutableFileModelImpl createLayer0Model(ModelTemplate modelTemplate, ResourceLocation itemLocation) {
            var ji = new JsonModelInjector(this.itemModelGenerators.output);
            var loc = decorateItemModelLocation(itemLocation);
            modelTemplate.create(loc, TextureMapping.layer0(loc), ji.injectedModelOutput());
            return new MutableFileModelImpl(loc, ji);
        }

        private MutableFileModelImpl createLayer0Model(ModelTemplate modelTemplate, Item item) {
            return createModel(modelTemplate, TextureMapping.layer0(item), item);
        }

        private MutableFileModelImpl createModel(ModelTemplate modelTemplate, TextureMapping textureMapping, ResourceLocation itemLocation) {
            var ji = new JsonModelInjector(this.itemModelGenerators.output);
            var loc = decorateItemModelLocation(itemLocation);
            modelTemplate.create(loc, textureMapping, ji.injectedModelOutput());
            return new MutableFileModelImpl(loc, ji);
        }

        private MutableFileModelImpl createModel(ModelTemplate modelTemplate, TextureMapping textureMapping, Item item) {
            var ji = new JsonModelInjector(this.itemModelGenerators.output);
            var loc = ModelLocationUtils.getModelLocation(item);
            modelTemplate.create(loc, textureMapping, ji.injectedModelOutput());
            return new MutableFileModelImpl(loc, ji);
        }
    }

    private static ResourceLocation decorateItemModelLocation(ResourceLocation location) {
        return new ResourceLocation(location.getNamespace(), "item/" + location.getPath());
    }
}

