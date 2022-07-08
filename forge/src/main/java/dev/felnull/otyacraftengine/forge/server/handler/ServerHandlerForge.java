package dev.felnull.otyacraftengine.forge.server.handler;

import dev.felnull.otyacraftengine.server.event.OEServerEventHooks;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerHandlerForge {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent e) {
        OEServerEventHooks.onLootTableModify(e.getLootTableManager(), e.getName(), (name, poolBuilder) -> e.getTable().addPool(poolBuilder.name(name.toString()).build()));

        var rep = OEServerEventHooks.onLootTableReplace(e.getLootTableManager(), e.getName(), e.getTable());
        if (rep != null)
            e.setTable(rep);
    }
}
