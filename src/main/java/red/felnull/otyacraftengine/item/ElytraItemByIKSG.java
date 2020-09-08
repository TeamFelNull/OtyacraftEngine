package red.felnull.otyacraftengine.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ElytraItemByIKSG extends ElytraItem implements IElytraLayerItem {

    public ElytraItemByIKSG(Properties builder) {
        super(builder);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return !this.isDamageable() || super.canElytraFly(stack, entity);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {

        return !this.isDamageable() || super.elytraFlightTick(stack, entity, flightTicks);
    }

    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.CHEST;
    }

    @Override
    public ResourceLocation getElytraLayerTextuer() {
        return new ResourceLocation(this.getRegistryName().getNamespace(), "textures/models/elytra/" + this.getRegistryName().getPath() + ".png");
    }
}
