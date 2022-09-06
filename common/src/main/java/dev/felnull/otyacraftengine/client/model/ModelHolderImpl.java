package dev.felnull.otyacraftengine.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@ApiStatus.Internal
public class ModelHolderImpl implements ModelHolder {
    private final ResourceLocation modelLocation;
    private Supplier<BakedModel> modelSupplier;

    protected ModelHolderImpl(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public @NotNull BakedModel get() {
        if (modelSupplier == null)
            throw new RuntimeException("Unregistered model");

        return modelSupplier.get();
    }

    @Override
    public @NotNull ResourceLocation getLocation() {
        return modelLocation;
    }

    @Override
    public void setModelSupplier(Supplier<BakedModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
    }
}
