package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

@ApiStatus.Internal
public class WrappedRecipeProvider extends RecipeProvider {
    private final RecipeProviderWrapper recipeProviderWrapper;

    public WrappedRecipeProvider(DataGenerator arg, RecipeProviderWrapper recipeProviderWrapper) {
        super(arg);
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        recipeProviderWrapper.generateRecipes(consumer);
    }
}
