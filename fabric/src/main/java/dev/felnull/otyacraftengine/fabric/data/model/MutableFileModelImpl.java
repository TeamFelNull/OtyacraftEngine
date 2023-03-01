package dev.felnull.otyacraftengine.fabric.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MutableFileModelImpl extends FileModelImpl implements MutableFileModel {
    private final JsonModelInjector jsonModelInjector;

    public MutableFileModelImpl(ResourceLocation location, JsonModelInjector jsonModelInjector) {
        super(location);
        this.jsonModelInjector = jsonModelInjector;
    }

    @Override
    public void override(@NotNull FileModel model, @NotNull List<OverridePredicate> predicates) {
        this.jsonModelInjector.putOverride(model, predicates);
    }

    @Override
    public void texture(@NotNull String id, @NotNull ResourceLocation location) {
        this.jsonModelInjector.putTexture(id, location);
    }
}
