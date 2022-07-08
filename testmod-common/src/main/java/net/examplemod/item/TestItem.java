package net.examplemod.item;

import net.examplemod.server.saveddata.TestSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestItem extends Item {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            var data = TestSavedData.get(level.getServer());
            if (!player.isCrouching()) {
                player.displayClientMessage(Component.literal(data.getName()), false);
            } else {
                data.setName(itemStack.getHoverName().getString());
                player.displayClientMessage(Component.literal("nna"), false);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
