package dev.felnull.otyacraftengine.forge.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemMutableFileModelImpl extends MutableFileModelImpl {
    private final ItemModelBuilder itemModelBuilder;

    public ItemMutableFileModelImpl(ItemModelBuilder itemModelBuilder) {
        super(itemModelBuilder);
        this.itemModelBuilder = itemModelBuilder;
    }

    @Override
    public void override(@NotNull FileModel model, @NotNull List<OverridePredicate> predicates) {
        var mf = FileModelImpl.getModelFile(model);
        var ovs = itemModelBuilder.override().model(mf);

        for (OverridePredicate predicate : predicates) {
            ovs.predicate(predicate.key(), predicate.value());
        }

        ovs.end();
    }
}
