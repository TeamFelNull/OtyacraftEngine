package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class OETestRecipeProvider extends RecipeProviderWrapper {
    public OETestRecipeProvider(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    @Override
    public void generate(Consumer<FinishedRecipe> exporter) {
        ShapelessRecipeBuilder.shapeless(Items.APPLE, 1)
                .requires(Items.EGG)
                // .unlockedBy("has_item", has.apply(Items.EGG))
                .save(exporter, new ResourceLocation(OtyacraftEngineTest.MODID, "test"));
    }
}
