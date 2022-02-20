package dev.felnull.otyacraftengine.server.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.felnull.otyacraftengine.server.level.LootTableAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTables;

public interface LootTableEvent {
    Event<LootTableLoad> LOOT_TABLE_LOAD = EventFactory.createLoop();

    public interface LootTableLoad {
        void lootTableLoading(ResourceLocation name, LootTables manager, LootTableAccess access);
    }
}
