package dev.felnull.otyacraftengine.data;

import dev.architectury.platform.Mod;
import dev.felnull.otyacraftengine.data.provider.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;

public interface CrossDataGeneratorAccess {
    @NotNull DataGenerator getVanillaGenerator();

    void addProvider(@NotNull DataGeneratorType dataGeneratorType, @NotNull DataProvider dataProvider);

    default void addProvider(@NotNull DataProviderWrapper<?> dataProviderWrapper) {
        addProvider(dataProviderWrapper.getGeneratorType(), dataProviderWrapper.getProvider());
    }

    Mod getMod();

    RecipeProvider createRecipeProvider(RecipeProviderWrapper recipeProviderWrapper);

    TagsProvider<Item> createItemTagProvider(ItemTagProviderWrapper itemTagProviderWrapper, @NotNull BlockTagProviderWrapper blockTagProviderWrapper);

    TagsProvider<Block> createBlockTagProvider(BlockTagProviderWrapper blockTagProviderWrapper);

    TagsProvider<PoiType> createPoiTypeTagProvider(PoiTypeTagProviderWrapper poiTypeTagProviderWrapper);

    DataProvider createDevToolProvider(DevToolProviderWrapper devToolProviderWrapper);

    DataProvider createBlockLootTableProvider(BlockLootTableProviderWrapper blockLootTableProviderWrapper);

    DataProvider createAdvancementProvider(AdvancementProviderWrapper advancementProviderWrapper);

    default DataProvider createItemModelProvider(ItemModelProviderWrapper itemModelProviderWrapper) {
        return null;
    }

    boolean isInclude(DataGeneratorType type);

    Collection<Path> getResourceInputFolders();

    void addResourceInputFolders(Path path);
}
