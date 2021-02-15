package red.felnull.otyacraftengine.item;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

public class TestItem extends Item {

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        player.displayClientMessage(new TextComponent("" + OtyacraftEngineAPI.getInstance().getIntegrations()), false);

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    public static Item TEST_ITEM;

    public static void init() {
        DeferredRegister<Item> MOD_ITEMS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        TEST_ITEM = new TestItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        MOD_ITEMS_REGISTER.register("test_item", () -> TEST_ITEM);
        MOD_ITEMS_REGISTER.register();
    }
}
