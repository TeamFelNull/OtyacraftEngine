package dev.felnull.otyacraftengine.server.level;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public interface LootTableAccess {
    public void set(LootTable table);

    public void addLootPool(ResourceLocation name, LootPool.Builder poolBuilder);
}
