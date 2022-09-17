package dev.felnull.otyacraftengine.client.model.impl;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.model.ModelBundle;
import dev.felnull.otyacraftengine.client.model.ModelCollectiveRegister;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ModelCollectiveRegisterImpl implements ModelCollectiveRegister {
    private static final Set<ModelHolder> MODELS = new HashSet<>();

    @Override
    public @NotNull ModelHolder register(@NotNull ResourceLocation location) {
        var holder = ModelHolder.create(location);
        MODELS.add(holder);
        return holder;
    }

    @Override
    public <T extends ModelBundle> @NotNull T register(@NotNull T bundle) {
        MODELS.addAll(bundle.getAllHolders());
        return bundle;
    }

    @Override
    public void registering(@NotNull ModelRegister register) {
        for (ModelHolder model : MODELS) {
            model.registering(register);
        }
    }
}
