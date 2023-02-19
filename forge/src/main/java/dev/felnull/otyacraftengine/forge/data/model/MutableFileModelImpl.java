package dev.felnull.otyacraftengine.forge.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import net.minecraftforge.client.model.generators.ItemModelBuilder;

import java.util.List;

public class MutableFileModelImpl extends FileModelImpl implements MutableFileModel {
    private final ItemModelBuilder itemModelBuilder;

    public MutableFileModelImpl(ItemModelBuilder itemModelBuilder) {
        super(itemModelBuilder);
        this.itemModelBuilder = itemModelBuilder;
    }

    @Override
    public void addOverride(FileModel model, List<OverridePredicate> predicates) {
        var mf = FileModelImpl.getModelFile(model);
        var ovs = itemModelBuilder.override().model(mf);

        for (OverridePredicate predicate : predicates) {
            ovs.predicate(predicate.key(), predicate.value());
        }

        ovs.end();
    }
}
