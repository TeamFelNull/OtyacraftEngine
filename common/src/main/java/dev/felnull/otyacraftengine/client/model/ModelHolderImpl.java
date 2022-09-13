package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.util.OEModelUtils;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ApiStatus.Internal
public class ModelHolderImpl implements ModelHolder {
    private final ResourceLocation modelLocation;
    private boolean registerFinish;

    protected ModelHolderImpl(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public @NotNull BakedModel get() {
        return OEModelUtils.getModel(modelLocation);
    }

    @Override
    public void registering(@NotNull ModelRegister register) {
        if (registerFinish) {
            OtyacraftEngine.LOGGER.warn("Duplicate registration of Model: " + modelLocation);
            return;
        }

        registerFinish = true;
        register.addModelLoad(modelLocation);
    }

    @Override
    public String toString() {
        return "ModelHolder{" + "location=" + modelLocation + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelHolderImpl that = (ModelHolderImpl) o;
        return registerFinish == that.registerFinish && Objects.equals(modelLocation, that.modelLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelLocation, registerFinish);
    }
}
