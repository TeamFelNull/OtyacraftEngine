package dev.felnull.otyacraftengine.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.util.OEClientUtil;
import dev.felnull.otyacraftengine.util.OEVoxelShapeUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.UUID;

public class TestItem extends Item implements IEquipmentItem {
    public TestItem(Properties properties) {
        super(properties);
    }

    private static final VoxelShape shape = OEVoxelShapeUtil.getShapeFromResource(new ResourceLocation(OtyacraftEngine.MODID, "sample"));

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);

        if (level.isClientSide()) {
            var ui = player.getGameProfile().getName();
            for (int i = 0; i < 30; i++) {
                var tx = OEClientUtil.getPlayerUUIDByName(UUID.randomUUID().toString());
                System.out.println(tx);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    public static Item TEST_ITEM;

    public static void init() {
        DeferredRegister<Item> ITEM_REG = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        TEST_ITEM = new TestItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        ITEM_REG.register("test_item", () -> TEST_ITEM);
        ITEM_REG.register();
    }

    @Override
    public EquipmentSlot getEquipmentSlotType(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
