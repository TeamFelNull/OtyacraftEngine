package dev.felnull.otyacraftengine.data;

import dev.architectury.platform.Mod;
import dev.felnull.otyacraftengine.data.provider.DataProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

public interface CrossDataGeneratorAccess {
    @NotNull DataGenerator getVanillaGenerator();

    void addProvider(@NotNull DataProvider dataProvider);

    default void addProvider(@NotNull DataProviderWrapper<?> dataProviderWrapper) {
        addProvider(dataProviderWrapper.getProvider());
    }

    Mod getMod();

    RecipeProvider createRecipeProvider(RecipeProviderWrapper recipeProviderWrapper);
}
