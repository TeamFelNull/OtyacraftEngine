package dev.felnull.otyacraftengine.fabric.data;

import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.ItemTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import dev.felnull.otyacraftengine.fabric.data.provider.WrappedFabricBlockTagProvider;
import dev.felnull.otyacraftengine.fabric.data.provider.WrappedFabricItemTagProvider;
import dev.felnull.otyacraftengine.fabric.data.provider.WrappedFabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class CrossDataGeneratorAccessImpl implements CrossDataGeneratorAccess {
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
    public boolean isInclude(DataGeneratorType type) {
        return true;
    }
}
