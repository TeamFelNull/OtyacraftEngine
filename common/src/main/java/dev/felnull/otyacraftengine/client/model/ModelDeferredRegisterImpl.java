package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class ModelDeferredRegisterImpl implements ModelDeferredRegister {
    private static final List<ModelHolder> MODELS = new ArrayList<>();

    protected ModelDeferredRegisterImpl() {

    }

    @Override
    public @NotNull ModelHolder register(@NotNull ResourceLocation location) {
        var holder = ModelHolder.create(location);
        MODELS.add(holder);
        return holder;
    }

    @Override
    public void registering(@NotNull ModelRegister register) {
        for (ModelHolder model : MODELS) {
            model.setModelSupplier(register.addAndGetModel(model.getLocation()));
        }
    }
}
