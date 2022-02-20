package dev.felnull.otyacraftengine.forge.server.handler;

import dev.felnull.otyacraftengine.server.event.OEServerEventHooks;
import dev.felnull.otyacraftengine.server.level.LootTableAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerHandler {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent e) {
        OEServerEventHooks.onLootTableLoading(e.getName(), e.getLootTableManager(), new LootTableAccess() {
            @Override
            public void set(LootTable table) {
                e.setTable(table);
            }

            @Override
            public void addLootPool(ResourceLocation name, LootPool.Builder poolBuilder) {
                e.getTable().addPool(poolBuilder.name(name.toString()).build());
            }
        });
    }
}
