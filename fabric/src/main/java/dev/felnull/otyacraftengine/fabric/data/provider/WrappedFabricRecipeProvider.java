package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
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
        recipeProviderWrapper.generateRecipes(exporter);
    }
}
