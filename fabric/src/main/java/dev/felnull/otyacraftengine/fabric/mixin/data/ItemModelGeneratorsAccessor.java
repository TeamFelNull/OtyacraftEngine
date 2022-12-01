package dev.felnull.otyacraftengine.fabric.mixin.data;

import com.google.gson.JsonElement;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Mixin(value = ItemModelGenerators.class, remap = false)
public interface ItemModelGeneratorsAccessor {
    @Accessor
    BiConsumer<ResourceLocation, Supplier<JsonElement>> getOutput();
}
