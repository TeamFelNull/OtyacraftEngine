package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.model.impl.ModelHolderImpl;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface ModelHolder {
    @NotNull
    static ModelHolder create(@NotNull ResourceLocation location) {
        return new ModelHolderImpl(location);
    }

    @NotNull
    BakedModel get();

    void registering(@NotNull ModelRegister register);
}
