package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

@ApiStatus.Internal
public class WrappedFabricRecipeProvider extends FabricRecipeProvider {
    private final RecipeProviderWrapper recipeProviderWrapper;

    public WrappedFabricRecipeProvider(FabricDataGenerator dataGenerator, RecipeProviderWrapper recipeProviderWrapper) {
        super(dataGenerator);
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
        recipeProviderWrapper.generateRecipe(exporter, new RecipeProviderAccessImpl());
    }

    private static class RecipeProviderAccessImpl implements RecipeProviderWrapper.RecipeProviderAccess {
        @Override
        public InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints ints, ItemLike itemLike) {
            return RecipeProvider.has(ints, itemLike);
        }

        @Override
        public InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike) {
            return RecipeProvider.has(itemLike);
        }

        @Override
        public InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tagKey) {
            return RecipeProvider.has(tagKey);
        }

        @Override
        public String getHasName(ItemLike itemLike) {
            return RecipeProvider.getHasName(itemLike);
        }

        @Override
        public String getItemName(ItemLike itemLike) {
            return RecipeProvider.getItemName(itemLike);
        }
    }
}
