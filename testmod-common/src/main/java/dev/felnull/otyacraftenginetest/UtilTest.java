package dev.felnull.otyacraftenginetest;

import dev.felnull.otyacraftengine.util.*;
import dev.felnull.otyacraftenginetest.item.TestItems;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;

import java.util.*;
import java.util.function.Consumer;

public class UtilTest {
    public static void init(Consumer<String> print, Player player) {
        print.accept("-------------------");
        entityUtils(print, player);
        itemUtils(print, player);
        nbtUtils(print, player);
        paths(print, player);
        playerUtils(print, player);
        print.accept("-------------------");
    }

    private static void entityUtils(Consumer<String> print, Player player) {
        print.accept("Skeleton entity tags:" + OEEntityUtils.getTags(EntityType.SKELETON).map(TagKey::location).toList());
        print.accept("Player right hand:" + OEEntityUtils.getHandByArm(player, HumanoidArm.RIGHT).name());
        print.accept("Player main hand:" + OEEntityUtils.getArmByHand(player, InteractionHand.MAIN_HAND).name());
        asserted(OEEntityUtils.getOppositeHand(InteractionHand.MAIN_HAND) == InteractionHand.OFF_HAND);
    }

    private static void itemUtils(Consumer<String> print, Player player) {
        for (Item item : Registry.ITEM) {
            if (item instanceof MobBucketItem mobBucketItem)
                print.accept("MobBucketItem entity:" + OEItemUtils.getMobBucketEntity(mobBucketItem));
        }

        if (player instanceof ServerPlayer serverPlayer) {
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createMoriMoriHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createKamesutaHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createNinHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createMGHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createMiyabiHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createYuuHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createToranpfanHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createHarumakiHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createBuunnHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createMUHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createAlfortHead());
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createPlayerHead(player));
            OEPlayerUtils.giveItem(serverPlayer, OEItemUtils.createPlayerHead("ikisugi"));

            var itemEntity = OEItemUtils.createItemEntity(new ItemStack(Items.APPLE), serverPlayer.level, serverPlayer.position());
            serverPlayer.level.addFreshEntity(itemEntity);
        }

        print.accept("Apple mod name:" + OEItemUtils.getCreatorModId(new ItemStack(Items.APPLE)));
        print.accept("Test mod name:" + OEItemUtils.getCreatorModId(new ItemStack(TestItems.TEST_ITEM.get())));
    }

    private static void nbtUtils(Consumer<String> print, Player player) {
        List<UUID> testList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            testList.add(UUID.randomUUID());
        }
        var nbt = new CompoundTag();
        OENbtUtils.writeUUIDList(nbt, "test_uuid_list", testList);

        List<UUID> testOutList = new ArrayList<>();
        OENbtUtils.readUUIDList(nbt, "test_uuid_list", testOutList);
        asserted(testList.equals(testOutList));

        Map<UUID, UUID> testMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            testMap.put(UUID.randomUUID(), UUID.randomUUID());
        }
        OENbtUtils.writeUUIDMap(nbt, "test_uuid_map", testMap);

        Map<UUID, UUID> testOutMap = new HashMap<>();
        OENbtUtils.readUUIDMap(nbt, "test_uuid_map", testOutMap);
        asserted(testMap.equals(testOutMap));

        Map<UUID, TestTagSerializable> testTagSerializableMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            testTagSerializableMap.put(UUID.randomUUID(), new TestTagSerializable());
        }
        OENbtUtils.writeUUIDSerializableMap(nbt, "test_uuid_serial", testTagSerializableMap);

        Map<UUID, TestTagSerializable> testOutTagSerializableMap = new HashMap<>();
        OENbtUtils.readUUIDSerializableMap(nbt, "test_uuid_serial", testOutTagSerializableMap, TestTagSerializable::new);
        asserted(testTagSerializableMap.equals(testOutTagSerializableMap));
    }

    private static void paths(Consumer<String> print, Player player) {
        print.accept("Client otyacraft engine folder path" + OEPaths.getClientOEFolderPath());
        if (!player.level.isClientSide())
            print.accept("World save data path" + OEPaths.getWorldSaveDataPath(player.level.getServer()));
    }

    private static void playerUtils(Consumer<String> print, Player player) {
        if (player instanceof ServerPlayer) {
            OEPlayerUtils.doPlayers(player.level, player.blockPosition(), p -> {
                OEPlayerUtils.giveItem(p, new ItemStack(Items.DIAMOND));
            });
        }

        print.accept("MoriMori uuid by name:" + OEPlayerUtils.getUUIDByName("MoriMori_0317_jp"));
        print.accept("MoriMori name by uuid:" + OEPlayerUtils.getNameByUUID(UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03")));
    }

    private static void asserted(boolean test) {
        if (!test)
            throw new AssertionError("Test failure");
    }
}
