package dev.felnull.otyacraftenginetest.server.handler;

public class ServerHandler {

    public static void init() {
        // LootTableEvent.LOOT_TABLE_MODIFY.register(ServerHandler::onLootTableModify);
        // LootTableEvent.LOOT_TABLE_REPLACE.register(ServerHandler::onLootTableReplace);
    }
/*
    private static void onLootTableModify(LootTables lootManager, ResourceLocation id, LootTableEvent.LootTableModify modifyAccess) {
        *//*var antennaPoolB = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(1f))
                .add(LootItem.lootTableItem(Items.APPLE).setWeight(1))
                .add(LootItem.lootTableItem(Items.APPLE).setWeight(4));
        modifyAccess.addLootPool(new ResourceLocation(OtyacraftEngine.MODID, "test"), antennaPoolB);*//*
    }

    private static CompoundEventResult<LootTable> onLootTableReplace(LootTables lootManager, ResourceLocation id, LootTable original) {
       *//* var antennaPoolB = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(1f))
                .add(LootItem.lootTableItem(Items.APPLE).setWeight(1))
                .add(LootItem.lootTableItem(Items.APPLE).setWeight(4));
        return CompoundEventResult.interruptTrue(LootTable.lootTable().withPool(antennaPoolB).build());*//*
        return CompoundEventResult.pass();
    }*/
}
