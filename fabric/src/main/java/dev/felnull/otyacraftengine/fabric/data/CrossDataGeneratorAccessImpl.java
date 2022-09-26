package dev.felnull.otyacraftengine.fabric.data;

import com.google.common.collect.ImmutableList;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import dev.felnull.otyacraftengine.data.provider.*;
import dev.felnull.otyacraftengine.fabric.data.provider.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApiStatus.Internal
public class CrossDataGeneratorAccessImpl implements CrossDataGeneratorAccess {
    private final List<Path> resourceInputFolders = new ArrayList<>();
    private final FabricDataGenerator fabricDataGenerator;

    protected CrossDataGeneratorAccessImpl(FabricDataGenerator fabricDataGenerator) {
        this.fabricDataGenerator = fabricDataGenerator;
    }

    @Override
    public @NotNull DataGenerator getVanillaGenerator() {
        return fabricDataGenerator;
    }

    @Override
    public void addProvider(@NotNull DataGeneratorType dataGeneratorType, @NotNull DataProvider dataProvider) {
        fabricDataGenerator.addProvider(dataProvider);
    }

    @Override
    public Mod getMod() {
        return Platform.getMod(fabricDataGenerator.getModId());
    }

    @Override
    public RecipeProvider createRecipeProvider(RecipeProviderWrapper recipeProviderWrapper) {
        return new WrappedFabricRecipeProvider(fabricDataGenerator, recipeProviderWrapper);
    }

    @Override
    public TagsProvider<Item> createItemTagProvider(ItemTagProviderWrapper itemTagProviderWrapper, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        var blockTagsProvider = blockTagProviderWrapper.getProvider();
        if (!(blockTagsProvider instanceof FabricTagProvider.BlockTagProvider blockTagProvider))
            throw new IllegalArgumentException("Not FabricTagProvider.BlockTagProvider");

        return new WrappedFabricItemTagProvider(fabricDataGenerator, blockTagProvider, itemTagProviderWrapper);
    }

    @Override
    public TagsProvider<Block> createBlockTagProvider(BlockTagProviderWrapper blockTagProviderWrapper) {
        return new WrappedFabricBlockTagProvider(fabricDataGenerator, blockTagProviderWrapper);
    }

    @Override
    public TagsProvider<PoiType> createPoiTypeTagProvider(PoiTypeTagProviderWrapper poiTypeTagProviderWrapper) {
        return new WrappedFabricPoiTypeTagProvider(fabricDataGenerator, poiTypeTagProviderWrapper);
    }

    @Override
    public DataProvider createDevToolProvider(DevToolProviderWrapper devToolProviderWrapper) {
        return new WrappedFabricDevToolProvider(devToolProviderWrapper);
    }

    @Override
    public DataProvider createBlockLootTableProvider(BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        return new WrappedFabricBlockLootTableProvider(fabricDataGenerator, blockLootTableProviderWrapper);
    }

    @Override
    public DataProvider createAdvancementProvider(AdvancementProviderWrapper advancementProviderWrapper) {
        return new WrappedFabricAdvancementProvider(fabricDataGenerator, advancementProviderWrapper);
    }

    @Override
    public boolean isInclude(DataGeneratorType type) {
        return true;
    }

    @Override
    public Collection<Path> getResourceInputFolders() {
        return ImmutableList.copyOf(resourceInputFolders);
    }

    @Override
    public void addResourceInputFolders(Path path) {
        resourceInputFolders.add(path);
    }
}
