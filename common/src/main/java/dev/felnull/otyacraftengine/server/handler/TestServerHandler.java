package dev.felnull.otyacraftengine.server.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.otyacraftengine.server.event.LootTableEvent;
import dev.felnull.otyacraftengine.server.level.LootTableAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootTables;

public class TestServerHandler {

    public static void init() {
        TickEvent.SERVER_LEVEL_PRE.register(TestServerHandler::onServerLevelTick);
        LootTableEvent.LOOT_TABLE_LOAD.register(TestServerHandler::onLootTableLoading);
    }

    public static void onServerLevelTick(ServerLevel serverLevel) {
        //  if (!serverLevel.dimension().location().equals(DimensionType.OVERWORLD_LOCATION.location())) return;
    /*    long time = System.currentTimeMillis();
        if (time - lastTime > 10000) {
            lastTime = time;*/
        // serverLevel.players().forEach(n -> n.getMainHandItem().setHoverName(new TextComponent(UUID.randomUUID().toString())));
        //  }
    }

    public static void onLootTableLoading(ResourceLocation name, LootTables manager, LootTableAccess access) {
        ///setblock ~ ~1 ~ minecraft:chest[]{LootTable:"minecraft:chests/simple_dungeon"} destroy
       /* if (name.toString().startsWith("minecraft:chests/")) {
            var pool = LootPool.lootPool().setRolls(UniformGenerator.between(1, 20))
                    .when(LootItemRandomChanceCondition.randomChance((1f)))
                    .add(LootItem.lootTableItem(() -> Items.DIAMOND_BLOCK).setWeight(20));
            access.addLootPool(new ResourceLocation(OtyacraftEngine.MODID, "test"), pool);
        }*/
    }
}
