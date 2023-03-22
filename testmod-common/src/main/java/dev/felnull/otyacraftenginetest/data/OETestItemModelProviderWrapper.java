package dev.felnull.otyacraftenginetest.data;

import com.google.common.collect.ImmutableList;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.model.ItemModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftenginetest.item.TestItems;
import net.minecraft.data.PackOutput;

public class OETestItemModelProviderWrapper extends ItemModelProviderWrapper {
    public OETestItemModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
    }

    @Override
    public void generateItemModels(ItemModelProviderAccess providerAccess) {
        var model = providerAccess.basicFlatItem(TestItems.TEST_ITEM.get());
        model.override(model, ImmutableList.of(new OverridePredicate(modLoc("test"), 0.3f)));

        providerAccess.handheldFlatItem(TestItems.TEST_SWORD.get());
        providerAccess.basicFlatItem(TestItems.TEST_HELMET.get());
        providerAccess.basicFlatItem(TestItems.TEST_CHESTPLATE.get());
        providerAccess.basicFlatItem(TestItems.TEST_LEGGINGS.get());
        providerAccess.basicFlatItem(TestItems.TEST_BOOTS.get());
    }
}
