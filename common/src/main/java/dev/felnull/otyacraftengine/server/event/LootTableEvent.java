package dev.felnull.otyacraftengine.server.event;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;

/**
 * Use Architectury LootEvent
 */
@Deprecated(forRemoval = true, since = "mc1.19.3")
public interface LootTableEvent {
    Event<Modify> LOOT_TABLE_MODIFY = EventFactory.createLoop();
    Event<Replace> LOOT_TABLE_REPLACE = EventFactory.createCompoundEventResult();

    interface Modify {
        void lootTableModify(LootTables lootManager, ResourceLocation id, LootTableModify modifyAccess);
    }

    interface Replace {
        CompoundEventResult<LootTable> lootTableReplace(LootTables lootManager, ResourceLocation id, LootTable original);
    }

    interface LootTableModify {
        void addLootPool(ResourceLocation name, LootPool.Builder poolBuilder);
    }
}