package dev.felnull.otyacraftengine.forge.data;

import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.ItemTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.provider.WrappedBlockTagProvider;
import dev.felnull.otyacraftengine.forge.data.provider.WrappedItemTagProvider;
import dev.felnull.otyacraftengine.forge.data.provider.WrappedRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class CrossDataGeneratorAccessImpl implements CrossDataGeneratorAccess {
    private final GatherDataEvent gatherDataEvent;

    protected CrossDataGeneratorAccessImpl(GatherDataEvent gatherDataEvent) {
        this.gatherDataEvent = gatherDataEvent;
    }

    @Override
    public @NotNull DataGenerator getVanillaGenerator() {
        return gatherDataEvent.getGenerator();
    }

    @Override
    public void addProvider(@NotNull DataProvider dataProvider) {
        var gen = gatherDataEvent.getGenerator();
        gen.addProvider(gatherDataEvent.includeServer(), dataProvider);
    }

    @Override
    public Mod getMod() {
        return Platform.getMod(gatherDataEvent.getModContainer().getModId());
    }

    @Override
    public RecipeProvider createRecipeProvider(RecipeProviderWrapper recipeProviderWrapper) {
        return new WrappedRecipeProvider(gatherDataEvent.getGenerator(), recipeProviderWrapper);
    }

    @Override
    public TagsProvider<Item> createItemTagProvider(ItemTagProviderWrapper itemTagProviderWrapper, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        if (!(blockTagProviderWrapper.getProvider() instanceof BlockTagsProvider blockTagsProvider))
            throw new IllegalArgumentException("Not BlockTagsProvider");

        return new WrappedItemTagProvider(getVanillaGenerator(), blockTagsProvider, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), itemTagProviderWrapper);
    }

    @Override
    public TagsProvider<Block> createBlockTagProvider(BlockTagProviderWrapper blockTagProviderWrapper) {
        return new WrappedBlockTagProvider(getVanillaGenerator(), gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), blockTagProviderWrapper);
    }
}