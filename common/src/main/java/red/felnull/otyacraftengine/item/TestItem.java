package red.felnull.otyacraftengine.item;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
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
import red.felnull.otyacraftengine.util.IKSGRegistryUtil;

import java.util.UUID;

;

public class TestItem extends Item {

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (level.isClientSide()) {
         //   player.displayClientMessage(new TextComponent(Minecraft.getInstance().player.connection.getPlayerInfo(UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03")).getProfile().getName()), false);
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
