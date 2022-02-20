package dev.felnull.otyacraftengine.fabric.server.handler;

import dev.felnull.otyacraftengine.server.event.OEServerEventHooks;
import dev.felnull.otyacraftengine.server.level.LootTableAccess;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;

public class ServerHandler {
    public static void init() {
        LootTableLoadingCallback.EVENT.register(ServerHandler::onLootTableLoading);
    }

    public static void onLootTableLoading(ResourceManager resourceManager, LootTables manager, ResourceLocation id, FabricLootSupplierBuilder supplier, LootTableLoadingCallback.LootTableSetter setter) {
        OEServerEventHooks.onLootTableLoading(id, manager, new LootTableAccess() {
            @Override
            public void set(LootTable table) {
                setter.set(table);
            }

            @Override
            public void addLootPool(ResourceLocation name, LootPool.Builder poolBuilder) {
                supplier.withPool(poolBuilder.build());
            }
        });
    }
}
