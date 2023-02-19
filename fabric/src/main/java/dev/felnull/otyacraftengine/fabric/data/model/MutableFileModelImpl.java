package dev.felnull.otyacraftengine.fabric.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class MutableFileModelImpl extends FileModelImpl implements MutableFileModel {
    private final JsonModelInjector jsonModelInjector;

    public MutableFileModelImpl(ResourceLocation location, JsonModelInjector jsonModelInjector) {
        super(location);
        this.jsonModelInjector = jsonModelInjector;
    }

    @Override
    public void addOverride(FileModel model, List<OverridePredicate> predicates) {
        this.jsonModelInjector.addOverride(model, predicates);
    }
}
