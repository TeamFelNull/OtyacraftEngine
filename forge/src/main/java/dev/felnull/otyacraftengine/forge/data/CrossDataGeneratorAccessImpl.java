package dev.felnull.otyacraftengine.forge.data;

import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.provider.WrappedRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
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
}
