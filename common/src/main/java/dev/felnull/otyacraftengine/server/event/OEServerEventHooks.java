package dev.felnull.otyacraftengine.server.event;

import dev.felnull.otyacraftengine.server.level.LootTableAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTables;

public class OEServerEventHooks {
    public static void onLootTableLoading(ResourceLocation name, LootTables manager, LootTableAccess access) {
        LootTableEvent.LOOT_TABLE_LOAD.invoker().lootTableLoading(name, manager, access);
    }
}
