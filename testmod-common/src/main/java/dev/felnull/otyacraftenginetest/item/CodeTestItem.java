package dev.felnull.otyacraftenginetest.item;

import dev.felnull.otyacraftenginetest.UtilTest;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CodeTestItem extends Item {
    public CodeTestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        UtilTest.init(s -> player.displayClientMessage(Component.literal(s), false), player);

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
