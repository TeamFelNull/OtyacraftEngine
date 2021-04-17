package red.felnull.otyacraftengine.item;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.util.IKSGNbtUtil;
import red.felnull.otyacraftengine.util.IKSGRegistryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestItem extends Item {

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!level.isClientSide()) {
            //   player.displayClientMessage(new TextComponent(IKSGPathUtil.getWorldSaveDataPath().toString()), false);
        } else {

         /*   TestSaveData data = WorldDataManager.getInstance().getSaveData(TestSaveData.class);

            if (!player.isCrouching()) {
                player.displayClientMessage(new TextComponent(data.getTest()), false);
            } else {
                player.displayClientMessage(new TextComponent("OverWrited"), false);

                String itmName = itemStack.getDisplayName().getString();

                if (itemStack.getDisplayName() instanceof TextComponent) {
                    itmName = ((TextComponent) itemStack.getDisplayName()).getText();
                }

                data.setTest(itmName);

            }*/

            List<UUID> uuids = new ArrayList<>();
            uuids.add(UUID.randomUUID());
            uuids.add(UUID.randomUUID());
            uuids.add(UUID.randomUUID());

            CompoundTag tag = new CompoundTag();
            IKSGNbtUtil.writeUUIDList(tag, "uuids", uuids);

            uuids.clear();

            ListTag ls = tag.getList("uuids", 11);

            List<UUID> auuids = new ArrayList<>();

            ls.forEach(n -> {
                //        auuids.add(NbtUtils.loadUUID(n));
            });

            IKSGNbtUtil.readUUIDList(tag, "uuids", auuids);

            auuids.forEach(n -> {
                player.displayClientMessage(new TextComponent(n.toString()), false);
            });
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    public static Item TEST_ITEM;
    public static Item TEST_TANK_ITEM;

    public static void init() {
        DeferredRegister<Item> MOD_ITEMS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        TEST_ITEM = new TestItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        TEST_TANK_ITEM = new TestTankItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        MOD_ITEMS_REGISTER.register("test_item", () -> TEST_ITEM);
        MOD_ITEMS_REGISTER.register("test_tank_item", () -> TEST_TANK_ITEM);
        MOD_ITEMS_REGISTER.register();

        IKSGRegistryUtil.replaceFood(Items.DIAMOND, Foods.APPLE);
        IKSGRegistryUtil.registerCompostable(0.5f, Items.DIAMOND);
    }
}
