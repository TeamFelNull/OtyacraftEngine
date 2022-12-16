package dev.felnull.otyacraftengine.fabric.server.handler;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;

public class ServerHandlerFabric {
    public static void init() {
        LootTableEvents.MODIFY.register(ServerHandlerFabric::onLootTableModify);
        LootTableEvents.REPLACE.register(ServerHandlerFabric::onReplaceLootTable);
    }

    private static void onLootTableModify(ResourceManager resourceManager, LootTables lootManager, ResourceLocation id, LootTable.Builder tableBuilder, LootTableSource source) {
        //  OEServerEventHooks.onLootTableModify(lootManager, id, (name, poolBuilder) -> tableBuilder.withPool(poolBuilder));
    }

    private static LootTable onReplaceLootTable(ResourceManager resourceManager, LootTables lootManager, ResourceLocation id, LootTable original, LootTableSource source) {
//        return OEServerEventHooks.onLootTableReplace(lootManager, id, original);
        return null;
    }
}
