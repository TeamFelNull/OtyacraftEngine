package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.recipes.RecipeProvider;

public abstract class RecipeProviderWrapper extends DataProviderWrapper<RecipeProvider> {
    private final RecipeProvider recipeProvider;

    public RecipeProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.recipeProvider = crossDataGeneratorAccess.createRecipeProvider(this);
    }

    @Override
    public RecipeProvider getProvider() {
        return this.recipeProvider;
    }
}
