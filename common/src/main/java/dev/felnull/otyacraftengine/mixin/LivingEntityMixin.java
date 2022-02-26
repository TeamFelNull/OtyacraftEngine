package dev.felnull.otyacraftengine.mixin;

import dev.felnull.otyacraftengine.item.IEquipmentItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "getEquipmentSlotForItem", at = @At("HEAD"), cancellable = true)
    private static void getEquipmentSlotForItem(ItemStack itemStack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (itemStack.getItem() instanceof IEquipmentItem equipmentItem) {
            var slot = equipmentItem.getEquipmentSlotType(itemStack);
            if (slot != null)
                cir.setReturnValue(slot);
        }
    }
}