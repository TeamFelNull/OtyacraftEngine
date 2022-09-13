package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface ModelDeferredRegister {
    @NotNull
    static ModelDeferredRegister create() {
        return new ModelDeferredRegisterImpl();
    }

    void registering(@NotNull ModelRegister register);

    @NotNull
    ModelHolder register(@NotNull ResourceLocation location);

    @NotNull <T extends ModelBundle> T register(@NotNull T bundle);
}
