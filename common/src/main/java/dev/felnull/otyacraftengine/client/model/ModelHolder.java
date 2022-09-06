package dev.felnull.otyacraftengine.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface ModelHolder {
    static ModelHolder create(@NotNull ResourceLocation location) {
        return new ModelHolderImpl(location);
    }

    @NotNull
    BakedModel get();

    @NotNull
    ResourceLocation getLocation();

    void setModelSupplier(Supplier<BakedModel> modelSupplier);
}
