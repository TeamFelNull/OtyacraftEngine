package dev.felnull.otyacraftengine.forge.data;

import com.google.common.collect.ImmutableList;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import dev.felnull.otyacraftengine.data.provider.*;
import dev.felnull.otyacraftengine.forge.data.provider.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApiStatus.Internal
public class CrossDataGeneratorAccessImpl implements CrossDataGeneratorAccess {
    private final List<Path> resourceInputFolders = new ArrayList<>();
    private final GatherDataEvent gatherDataEvent;

    protected CrossDataGeneratorAccessImpl(GatherDataEvent gatherDataEvent) {
        this.gatherDataEvent = gatherDataEvent;
    }

    @Override
    public @NotNull DataGenerator getVanillaGenerator() {
        return gatherDataEvent.getGenerator();
    }

    @Override
    public void addProvider(@NotNull DataGeneratorType dataGeneratorType, @NotNull DataProvider dataProvider) {
        var gen = gatherDataEvent.getGenerator();
        gen.addProvider(isInclude(dataGeneratorType), dataProvider);
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

        return new WrappedItemTagsProvider(getVanillaGenerator(), blockTagsProvider, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), itemTagProviderWrapper);
    }

    @Override
    public TagsProvider<Block> createBlockTagProvider(BlockTagProviderWrapper blockTagProviderWrapper) {
        return new WrappedBlockTagsProvider(getVanillaGenerator(), gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), blockTagProviderWrapper);
    }

    @Override
    public TagsProvider<PoiType> createPoiTypeTagProvider(PoiTypeTagProviderWrapper poiTypeTagProviderWrapper) {
        return new WrappedPoiTypeTagsProvider(getVanillaGenerator(), gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), poiTypeTagProviderWrapper);
    }

    @Override
    public DataProvider createDevToolProvider(DevToolProviderWrapper devToolProviderWrapper) {
        return new WrappedDevToolProvider(devToolProviderWrapper);
    }

    @Override
    public DataProvider createBlockLootTableProvider(BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        return new WrappedBlockLootTableProvider(getVanillaGenerator(), blockLootTableProviderWrapper);
    }

    @Override
    public DataProvider createAdvancementProvider(AdvancementProviderWrapper advancementProviderWrapper) {
        return new WrappedAdvancementProvider(getVanillaGenerator(), gatherDataEvent.getExistingFileHelper(), advancementProviderWrapper);
    }

    @Override
    public DataProvider createItemModelProvider(ItemModelProviderWrapper itemModelProviderWrapper) {
        return new WrappedItemModelProvider(getVanillaGenerator(), gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), itemModelProviderWrapper);
    }

    @Override
    public boolean isInclude(DataGeneratorType type) {
        return switch (type) {
            case CLIENT -> gatherDataEvent.includeClient();
            case SERVER -> gatherDataEvent.includeServer();
            case DEV -> gatherDataEvent.includeDev();
            case REPORT -> gatherDataEvent.includeReports();
        };
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
