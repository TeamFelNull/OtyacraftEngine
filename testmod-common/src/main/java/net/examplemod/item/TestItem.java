package net.examplemod.item;

import dev.felnull.otyacraftengine.util.OEPlayerUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class TestItem extends Item {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (level.isClientSide) {
            var uuid = UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03");
            int[] num = new int[1];
            for (int i = 0; i < 300; i++) {
                OEPlayerUtils.getNameByUUIDAsync(uuid).thenAccept(n -> {
                    player.displayClientMessage(Component.literal(n.orElse("NONE")), false);
                    num[0]++;
                });
            }
            System.out.println(num[0]);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
