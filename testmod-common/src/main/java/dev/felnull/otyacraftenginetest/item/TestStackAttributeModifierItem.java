package dev.felnull.otyacraftenginetest.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.felnull.otyacraftengine.item.StackAttributeModifierItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestStackAttributeModifierItem extends Item implements StackAttributeModifierItem {
    public TestStackAttributeModifierItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getStackAttributeModifiers(@NotNull EquipmentSlot slot, ItemStack stack) {
        return ImmutableMultimap.of(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 10, AttributeModifier.Operation.ADDITION));
    }
}
