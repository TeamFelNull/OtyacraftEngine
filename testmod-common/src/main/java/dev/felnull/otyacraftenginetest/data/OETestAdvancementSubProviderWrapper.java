package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.AdvancementSubProviderWrapper;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class OETestAdvancementSubProviderWrapper extends AdvancementSubProviderWrapper {
    protected OETestAdvancementSubProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    @Override
    public void generate(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement()
                .display(Items.ACACIA_LOG, modTitle("root"), modDescription("root"), modLoc("textures/gui/advancements/backgrounds/imp.png"), FrameType.TASK, false, false, false)
                .addCriterion(OtyacraftEngineTest.MODID, InventoryChangeTrigger.TriggerInstance.hasItems(Items.APPLE))
                .save(consumer, modLoc(OtyacraftEngineTest.MODID + "/root").toString());
    }
}
