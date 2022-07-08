package dev.felnull.otyacraftengine.server.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import org.jetbrains.annotations.NotNull;

public class OEServerEventHooks {
    public static void onLootTableModify(LootTables lootManager, ResourceLocation id, LootTableEvent.LootTableModify modifyAccess) {
        LootTableEvent.LOOT_TABLE_MODIFY.invoker().lootTableModify(lootManager, id, modifyAccess);
    }

    public static LootTable onLootTableReplace(LootTables lootManager, ResourceLocation id, LootTable original) {
        var ret = LootTableEvent.LOOT_TABLE_REPLACE.invoker().lootTableReplace(lootManager, id, original);
        return ret.object();
    }

    public static void onServerSaving(@NotNull MinecraftServer server) {
        ServerEvent.SERVER_SAVING.invoker().stateChanged(server);
    }
}
